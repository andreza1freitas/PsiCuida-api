package br.com.psicuida.model;

import java.time.LocalDate;

import br.com.psicuida.entity.Paciente;
import br.com.psicuida.enums.EstadoCivil;
import br.com.psicuida.enums.Genero;
import br.com.psicuida.enums.Situacao;
import br.com.psicuida.enums.Tratamento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PacienteDTO {

	private Long id;
    private String nome;
    private String email;
    private String telefone;
    private LocalDate dataNascimento;
    private Genero genero;
    private EstadoCivil estadoCivil;
    private Tratamento tratamento;
    private String senha;
    private Situacao situacao;

    public PacienteDTO(Paciente paciente) {
        this.id = paciente.getId();
        this.nome = paciente.getNome();
        this.email = paciente.getEmail();
        this.telefone = paciente.getTelefone();
        this.dataNascimento = paciente.getDataNascimento();
        this.genero = paciente.getGenero();
        this.estadoCivil = paciente.getEstadoCivil();
        this.tratamento = paciente.getTratamento();
        this.senha = paciente.getSenha();
        this.situacao = paciente.getSituacao();
    }
}
