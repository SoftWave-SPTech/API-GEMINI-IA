package softwave.api_gemini_ia.entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ultimas_movimentacoes")
public class UltimasMovimentacoes {

    @Id
    private Integer id;

    private LocalDateTime data;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String movimento;

    private Integer processoId;


    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public LocalDateTime getData() { return data; }
    public void setData(LocalDateTime data) { this.data = data; }

    public String getMovimento() { return movimento; }
    public void setMovimento(String movimento) { this.movimento = movimento; }

    public Integer getProcessoId() { return processoId; }
    public void setProcessoId(Integer processoId) { this.processoId = processoId; }
}

