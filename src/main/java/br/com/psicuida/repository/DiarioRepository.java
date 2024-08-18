package br.com.psicuida.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.psicuida.entity.Diario;

public interface DiarioRepository extends JpaRepository<Diario, Long> {
	 // Método para buscar um diário por data
    Optional<Diario> findByData(LocalDate data);
    
    Optional<Diario> findByDataAndPacienteId(LocalDate data, Long pacienteId);
}