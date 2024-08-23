package br.com.psicuida.model;

import java.time.LocalDate;

import br.com.psicuida.entity.Diario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiarioDTO {

	private Long id;
	private LocalDate data;
    private String sentimento;
    private String fezBem;
    private String fezMal;
    private String licoesAprendidas;
    private Long pacienteId; 

	public DiarioDTO(Diario diario) {
		this.id = diario.getId();
		this.data = diario.getData();
		this.sentimento = diario.getSentimento();
		this.fezBem = diario.getFezBem();
		this.fezMal = diario.getFezMal();
		this.licoesAprendidas = diario.getLicoesAprendidas();
		this.pacienteId = diario.getPaciente().getId(); 
	}
}
