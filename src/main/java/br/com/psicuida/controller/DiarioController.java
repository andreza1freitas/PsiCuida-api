package br.com.psicuida.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.psicuida.entity.Diario;
import br.com.psicuida.entity.Paciente;
import br.com.psicuida.model.DiarioDTO;
import br.com.psicuida.repository.DiarioRepository;
import br.com.psicuida.repository.PacienteRepository;

/**
 * @author teste2
 */
@RestController
@RequestMapping("/diarios")
public class DiarioController {

	@Autowired
	private DiarioRepository diarioRepository;

	@Autowired
	private PacienteRepository pacienteRepository;

	@GetMapping
	public List<DiarioDTO> listarTodos() {
		return diarioRepository.findAll().stream().map(DiarioDTO::new).collect(Collectors.toList());
	}

	@GetMapping("/{id}")
	public ResponseEntity<DiarioDTO> buscarPorId(@PathVariable Long id) {
		Optional<Diario> diario = diarioRepository.findById(id);
		return diario.map(value -> ResponseEntity.ok(new DiarioDTO(value)))
				.orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	@GetMapping("/data/{data}/paciente/{pacienteId}")
	public ResponseEntity<DiarioDTO> buscarPorDataEPaciente(@PathVariable LocalDate data, @PathVariable Long pacienteId) {
	    Optional<Diario> diario = diarioRepository.findByDataAndPacienteId(data, pacienteId);
	    return diario.map(value -> ResponseEntity.ok(new DiarioDTO(value)))
	                 .orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<DiarioDTO> criar(@RequestBody DiarioDTO diarioDTO) {
		Diario diario = new Diario();
		diario.setData(diarioDTO.getData());
		diario.setSentimento(diarioDTO.getSentimento());
		diario.setFezBem(diarioDTO.getFezBem());
		diario.setFezMal(diarioDTO.getFezMal());
		diario.setLicoesAprendidas(diarioDTO.getLicoesAprendidas());

		// Associa o diário ao paciente
		Paciente paciente = pacienteRepository.findById(diarioDTO.getPacienteId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente não encontrado"));
		diario.setPaciente(paciente);

		Diario salvo = diarioRepository.save(diario);
		return ResponseEntity.ok(new DiarioDTO(salvo));
	}

	@PutMapping("/{id}")
	public ResponseEntity<DiarioDTO> atualizar(@PathVariable Long id, @RequestBody DiarioDTO diarioDTO) {
		return diarioRepository.findById(id).map(diario -> {
			diario.setData(diarioDTO.getData());
			diario.setSentimento(diarioDTO.getSentimento());
			diario.setFezBem(diarioDTO.getFezBem());
			diario.setFezMal(diarioDTO.getFezMal());
			diario.setLicoesAprendidas(diarioDTO.getLicoesAprendidas());

			Paciente paciente = pacienteRepository.findById(diarioDTO.getPacienteId())
					.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente não encontrado"));
			diario.setPaciente(paciente);

			Diario atualizado = diarioRepository.save(diario);
			return ResponseEntity.ok(new DiarioDTO(atualizado));
		}).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Long id) {
		return diarioRepository.findById(id).map(diario -> {
			diarioRepository.delete(diario);
			return ResponseEntity.ok().<Void>build();
		}).orElseGet(() -> ResponseEntity.notFound().build());
	}

	// Método para buscar diário pelo dia atual
	@GetMapping("/hoje")
	public ResponseEntity<DiarioDTO> buscarDiarioHoje() {
		LocalDate hoje = LocalDate.now();
		Optional<Diario> diario = diarioRepository.findByData(hoje);
		return diario.map(value -> ResponseEntity.ok(new DiarioDTO(value)))
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	// Método para buscar diário por data
	@GetMapping("/data/{data}")
	public ResponseEntity<DiarioDTO> buscarPorData(
			@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
		Optional<Diario> diario = diarioRepository.findByData(data);
		return diario.map(value -> ResponseEntity.ok(new DiarioDTO(value)))
				.orElseGet(() -> ResponseEntity.notFound().build());
	}
}