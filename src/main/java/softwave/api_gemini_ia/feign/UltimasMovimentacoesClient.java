package softwave.api_gemini_ia.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import softwave.api_gemini_ia.dto.UltimasMovimentacoesDTO;

import java.util.List;

@FeignClient(name = "ultimasMovimentacoesClient", url = "http://backend-softwave:8080/ultimas-movimentacoes")
public interface UltimasMovimentacoesClient {

    @GetMapping("/{id}")
    UltimasMovimentacoesDTO buscarPorId(@PathVariable("id") Integer id);

    @GetMapping("/ordenadas")
    List<UltimasMovimentacoesDTO> listarMovimentacoesOrdenadas();

    @GetMapping("/processo/{processoId}")
    List<UltimasMovimentacoesDTO> buscarPorProcesso(@PathVariable("processoId") Integer processoId);

    @GetMapping("/processo/{processoId}/ordenadas")
    List<UltimasMovimentacoesDTO> buscarPorProcessoOrdenadas(@PathVariable("processoId") Integer processoId);
}
