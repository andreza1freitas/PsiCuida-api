package br.com.psicuida.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    private Long pacienteId;
	private List<RespostaDTO> respostas;
    
    public PerguntaDTO(Pergunta pergunta) {
        this.id = pergunta.getId();
        this.titulo = pergunta.getTitulo();
        this.descricao = pergunta.getDescricao();
        this.pacienteId = pergunta.getPaciente().getId();
        this.respostas = pergunta.getRespostas() != null ?
                pergunta.getRespostas().stream().map(RespostaDTO::new).collect(Collectors.toList()) :
                new ArrayList<>();
    }
}