package br.com.psicuida.model;

import java.time.LocalDateTime;

import br.com.psicuida.entity.AgendamentoSessao;
import br.com.psicuida.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgendamentoSessaoDTO {

    private Long id;
    private LocalDateTime dataHora;
    private Status status;
    private String observacao;
    private Long pacienteId;
    private Long profissionalId;
    private String profissionalNome;
    
    public AgendamentoSessaoDTO(AgendamentoSessao agendamento) {
        this.id = agendamento.getId();
        this.dataHora = agendamento.getDataHora();
        this.status = agendamento.getStatus();
        this.observacao = agendamento.getObservacao();
        this.pacienteId = agendamento.getPaciente().getId();
        this.profissionalId = agendamento.getProfissional().getId();
        this.profissionalNome = agendamento.getProfissional().getNome();
    }
}
