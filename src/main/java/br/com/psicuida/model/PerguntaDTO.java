package br.com.psicuida.model;

import br.com.psicuida.entity.Pergunta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PerguntaDTO {

    private Long id;
    private String titulo;
    private String descricao;
    
    public PerguntaDTO(Pergunta pergunta) {
        this.id = pergunta.getId();
        this.titulo = pergunta.getTitulo();
        this.descricao = pergunta.getDescricao();
    }
}