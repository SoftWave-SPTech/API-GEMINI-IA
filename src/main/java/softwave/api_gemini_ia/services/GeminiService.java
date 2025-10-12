package softwave.api_gemini_ia.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import softwave.api_gemini_ia.dto.UltimasMovimentacoesDTO;
import softwave.api_gemini_ia.entity.AnaliseProcesso;
import softwave.api_gemini_ia.exception.EntidadeConflitoException;
import softwave.api_gemini_ia.exception.EntidadeNaoEncontradaException;
import softwave.api_gemini_ia.exception.ServiceUnavailableException;
import softwave.api_gemini_ia.feign.UltimasMovimentacoesClient;
import softwave.api_gemini_ia.repository.AnaliseProcessoRepository;



import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class GeminiService {

    private final AnaliseProcessoRepository analiseRepository;
    private final UltimasMovimentacoesClient movimentacoesClient;

    private final String apiKey =  "AIzaSyCyF9md2vSFdPQcRf7nAop2EXn4FNsJt6M";

    public GeminiService(
            AnaliseProcessoRepository analiseRepository,
            UltimasMovimentacoesClient movimentacoesClient) {
        this.analiseRepository = analiseRepository;
        this.movimentacoesClient = movimentacoesClient;
    }

    public void gerarAnalisePorId(Integer movimentacaoId) {
        UltimasMovimentacoesDTO movimentacao = movimentacoesClient.buscarPorId(movimentacaoId);

        if (movimentacao == null) {
            throw new EntidadeNaoEncontradaException("Movimentação não encontrada!");
        }

        boolean jaTemAnalise = analiseRepository.findByMovimentacoesId(movimentacao.getId()).isPresent();
        if (jaTemAnalise) {
            throw new EntidadeConflitoException("Análise já existente para esta movimentação!");
        }

        String prompt = "Explique de forma simples a seguinte movimentação processual:\n\n"
                + movimentacao.getMovimento() + "\n\n"
                + "Use uma linguagem clara para leigos e destaque os eventos mais importantes.";

        try {
            String resposta = chamarGemini(prompt);
            resposta = resposta.replace("*", "").trim();

            AnaliseProcesso analise = new AnaliseProcesso();
            analise.setMovimentacaoId(movimentacao.getId());
            analise.setResumoIA(resposta);

            analiseRepository.save(analise);

        } catch (Exception e) {
            throw new ServiceUnavailableException("Erro ao acessar o serviço Gemini: " + e.getMessage());
        }
    }

    private String chamarGemini(String prompt) throws Exception {
        String endpoint = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent";

        String requestBody = String.format("""
        {
          "contents": [{
            "parts": [{
              "text": "%s"
            }]
          }],
          "generationConfig": {
            "temperature": 0.7,
            "maxOutputTokens": 1000
          }
        }
        """, prompt.replace("\"", "\\\""));

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpoint + "?key=" + apiKey))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.body());

        if (root.has("error")) {
            String mensagemErro = root.path("error").path("message").asText();
            throw new ServiceUnavailableException(
                    "O serviço não está disponível!" +
                            "Por favor, contate o nosso suporte para que possamos ajudá-lo!"
            );
        }

        return root.path("candidates")
                .get(0)
                .path("content")
                .path("parts")
                .get(0)
                .path("text")
                .asText()
                .trim();
    }
}