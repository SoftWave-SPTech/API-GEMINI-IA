package softwave.api_gemini_ia.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import softwave.api_gemini_ia.dto.UltimasMovimentacoesDTO;
import softwave.api_gemini_ia.entity.UltimasMovimentacoes;
import softwave.api_gemini_ia.services.AnaliseProcessoService;
import softwave.api_gemini_ia.services.GeminiService;
import softwave.api_gemini_ia.entity.AnaliseProcesso;
import softwave.api_gemini_ia.dto.AnaliseIAMovimentacaoDTO;
import softwave.api_gemini_ia.dto.AnaliseProcessoDTO;


@RestController
@RequestMapping("/analise-processo")
public class AnaliseProcessoController {

        @Autowired
        private AnaliseProcessoService analiseService;

        @Autowired
        private GeminiService geminiService;

        @Operation(summary = "Geração das análise com IA de uma movimentação do processo por ID da Movimentação", method = "POST")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "201", description = "Análise gerada com sucesso"),
                @ApiResponse(responseCode = "500", description = "Erro interno ao gerar a análise"),
                @ApiResponse(responseCode = "404", description = "Movimentação não encontrada"),
        })
        @PostMapping("/{id}")
        @SecurityRequirement(name = "Bearer")
        public ResponseEntity<String> analisarProcessos(@Valid @PathVariable Integer id) {
            geminiService.gerarAnalisePorId(id);
            return ResponseEntity.status(201).body("Análise gerada com sucesso!");
        }

        @Operation(summary = "Lista todas as análises de movimentações dos processos", method = "GET")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "Lista de análises retornada com sucesso"),
                @ApiResponse(responseCode = "204", description = "Não há análises geradas")
        })
        @GetMapping
        @SecurityRequirement(name = "Bearer")
        public ResponseEntity<List<AnaliseProcessoDTO>> listarAnalises() {
            List<AnaliseProcesso> analises = analiseService.listarTodas();
            if (analises.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            List<AnaliseProcessoDTO> analisesDTO = analises.stream()
                    .map(AnaliseProcessoDTO::toDTO)
                    .toList();
            return ResponseEntity.ok(analisesDTO);
        }

        @Operation(summary = "Busca uma análise por ID", method = "GET")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "Análise encontrada com sucesso"),
                @ApiResponse(responseCode = "404", description = "Análise não encontrada")
        })
        @GetMapping("/{id}")
        @SecurityRequirement(name = "Bearer")
        public ResponseEntity<AnaliseProcessoDTO> buscarPorId(@PathVariable Integer id) {
            AnaliseProcesso analiseProcesso = analiseService.buscarPorId(id);
            return ResponseEntity.ok(AnaliseProcessoDTO.toDTO(analiseProcesso));
        }

        @Operation(summary = "Busca uma análise de processo por ID da movimentação", method = "GET")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "Análise encontrada com sucesso"),
                @ApiResponse(responseCode = "404", description = "Análise não encontrada para o ID da movimentação fornecido")
        })
        @GetMapping("/por-movimentacao/{movimentacaoId}")
        @SecurityRequirement(name = "Bearer")
        public ResponseEntity<AnaliseIAMovimentacaoDTO> buscarPorMovimentacao(@PathVariable Integer movimentacaoId) {
            AnaliseProcesso analiseProcesso = analiseService.buscarPorIdMovimentacao(movimentacaoId);
            UltimasMovimentacoes ultimasMovimentacoes = analiseProcesso.getMovimentacoes();
            UltimasMovimentacoesDTO movimentacoesDTO = new UltimasMovimentacoesDTO(
                    ultimasMovimentacoes.getId(),
                    ultimasMovimentacoes.getData(),
                    ultimasMovimentacoes.getMovimento(),
                    ultimasMovimentacoes.getProcessoId()
            );
            AnaliseIAMovimentacaoDTO analiseDto = new AnaliseIAMovimentacaoDTO(
                    analiseProcesso.getId(),
                    analiseProcesso.getResumoIA(),
                    movimentacoesDTO
            );
            return ResponseEntity.ok(analiseDto);
        }
    }


