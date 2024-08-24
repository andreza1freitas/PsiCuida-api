package br.com.psicuida.model;

import java.time.LocalDate;

import br.com.psicuida.entity.Resposta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespostaDTO {

    private Long id;
    private String conteudo;
    private Long perguntaId;
    private Long pacienteId;
    private LocalDate data;

    // Construtor que aceita uma entidade Resposta como argumento
    public RespostaDTO(Resposta resposta) {
        this.id = resposta.getId();
        this.conteudo = resposta.getConteudo();
        this.perguntaId = resposta.getPergunta().getId();
        this.pacienteId = resposta.getPaciente().getId();
        this.data = resposta.getData();
        
    }
}
