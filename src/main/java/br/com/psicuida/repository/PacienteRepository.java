package br.com.psicuida.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.psicuida.entity.Paciente;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {

	@Query("""
			SELECT paciente FROM Paciente paciente
			WHERE paciente.email = :email
			AND paciente.situacao = 'ATIVO'
			""")
	Optional<Paciente> findByEmail(String email);

}
