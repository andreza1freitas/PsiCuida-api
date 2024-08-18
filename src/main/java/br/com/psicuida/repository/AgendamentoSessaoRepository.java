package br.com.psicuida.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.psicuida.entity.AgendamentoSessao;

public interface AgendamentoSessaoRepository extends JpaRepository<AgendamentoSessao, Long> {
	 boolean existsByDataHoraAndProfissionalId(LocalDateTime dataHora, Long profissionalId);
	 
	  @Query("""
	  		SELECT agendamento FROM AgendamentoSessao agendamento 
	  		WHERE agendamento.paciente.id = :pacienteId
	  		AND agendamento.status <> 'REALIZADO'
	  		""")
	 List<AgendamentoSessao> findByPacienteId(Long pacienteId);
}	