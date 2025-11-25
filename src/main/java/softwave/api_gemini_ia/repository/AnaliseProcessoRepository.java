package softwave.api_gemini_ia.repository;

import softwave.api_gemini_ia.entity.AnaliseProcesso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softwave.api_gemini_ia.entity.UltimasMovimentacoes;

import java.util.Optional;

@Repository
public interface AnaliseProcessoRepository extends JpaRepository<AnaliseProcesso, Integer> {

    Optional<AnaliseProcesso> findByMovimentacoesId(Integer id);


    Optional<AnaliseProcesso> findByMovimentacoes(UltimasMovimentacoes movimentacoes);

}
