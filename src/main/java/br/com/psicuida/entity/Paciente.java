package br.com.psicuida.entity;

import java.time.LocalDate;
import java.util.List;

import br.com.psicuida.enums.EstadoCivil;
import br.com.psicuida.enums.Genero;
import br.com.psicuida.enums.Tratamento;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 150) 
    private String nome;

    @Column(length = 150) 
    private String email;

    @Column(length = 20) 
    private String telefone;

    private LocalDate dataNascimento; 

    @Enumerated(EnumType.STRING)
    @Column(length = 50) 
    private Genero genero;

    @Enumerated(EnumType.STRING)
    @Column(length = 20) 
    private EstadoCivil estadoCivil;

    @Enumerated(EnumType.STRING)
    @Column(length = 50) 
    private Tratamento tratamento;

    @Column(length = 30) 
    private String senha;

    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Diario> diarios;

    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AgendamentoSessao> agendamentos;
}
