package softwave.api_gemini_ia.services;


import softwave.api_gemini_ia.entity.AnaliseProcesso;
import softwave.api_gemini_ia.exception.EntidadeNaoEncontradaException;
import softwave.api_gemini_ia.exception.NoContentException;
import softwave.api_gemini_ia.repository.AnaliseProcessoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnaliseProcessoService {

    @Autowired
    private AnaliseProcessoRepository analiseRepository;


    public List<AnaliseProcesso> listarTodas() {
        List<AnaliseProcesso> analiseProcesso = analiseRepository.findAll();

        if(analiseProcesso.isEmpty()){
            throw new NoContentException("Nenhuma analise de processo encontrado!");
        }

        return analiseProcesso;
    }


    public AnaliseProcesso buscarPorId(Integer id) {

        return analiseRepository.findById(id).orElseThrow(
                () -> new EntidadeNaoEncontradaException("Análise IA não foi encontrada!")
        );
    }

    public AnaliseProcesso buscarPorIdMovimentacao(Integer movimentacaoId) {

        return analiseRepository.findByMovimentacoesId(movimentacaoId).orElseThrow(
                () -> new EntidadeNaoEncontradaException(
                        "Nenhuma análise encontrada para esta movimentação!")
        );
    }
}
