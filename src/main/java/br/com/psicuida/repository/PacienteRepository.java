package br.com.psicuida.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.psicuida.entity.Paciente;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {

	Optional<Paciente> findByEmail(String email);
	
	
}
