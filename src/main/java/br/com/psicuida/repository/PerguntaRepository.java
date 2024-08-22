package br.com.psicuida.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.psicuida.entity.Pergunta;

public interface PerguntaRepository extends JpaRepository<Pergunta, Long> {
	
	@Query("""
			select pergunta from Pergunta pergunta
			join fetch pergunta.paciente paciente 
			left join fetch pergunta.respostas respostas
			where paciente.id = :pacienteId
			"""
			)
	List<Pergunta> findByPacienteId(Long pacienteId);

}
