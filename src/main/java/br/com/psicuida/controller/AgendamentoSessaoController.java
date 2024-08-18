package br.com.psicuida.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.psicuida.entity.AgendamentoSessao;
import br.com.psicuida.entity.Paciente;
import br.com.psicuida.entity.ProfissionalSaude;
import br.com.psicuida.enums.Status;
import br.com.psicuida.model.AgendamentoSessaoDTO;
import br.com.psicuida.repository.AgendamentoSessaoRepository;
import br.com.psicuida.repository.PacienteRepository;
import br.com.psicuida.repository.ProfissionalSaudeRepository;

@RestController
@RequestMapping("/agendamentos")
public class AgendamentoSessaoController {

	@Autowired
	private AgendamentoSessaoRepository agendamentoSessaoRepository;

	@Autowired
	private PacienteRepository pacienteRepository;

	@Autowired
	private ProfissionalSaudeRepository profissionalSaudeRepository;

	@GetMapping
	public List<AgendamentoSessaoDTO> listarTodos() {
		return agendamentoSessaoRepository.findAll().stream().map(AgendamentoSessaoDTO::new)
				.collect(Collectors.toList());
	}

	@GetMapping("/{id}")
	public ResponseEntity<AgendamentoSessaoDTO> buscarPorId(@PathVariable Long id) {
		Optional<AgendamentoSessao> agendamento = agendamentoSessaoRepository.findById(id);
		return agendamento.map(value -> ResponseEntity.ok(new AgendamentoSessaoDTO(value)))
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@GetMapping("/por-paciente")
	public List<AgendamentoSessaoDTO> listarPorPaciente(@RequestParam Long pacienteId) {
	    return agendamentoSessaoRepository.findByPacienteId(pacienteId).stream()
	            .map(AgendamentoSessaoDTO::new)
	            .collect(Collectors.toList());
	}
	
	@PostMapping
	public ResponseEntity<AgendamentoSessaoDTO> criar(@RequestBody AgendamentoSessaoDTO agendamentoDTO) {
	    Optional<Paciente> paciente = pacienteRepository.findById(agendamentoDTO.getPacienteId());
	    Optional<ProfissionalSaude> profissional = profissionalSaudeRepository.findById(agendamentoDTO.getProfissionalId());

	    if (paciente.isPresent() && profissional.isPresent()) {
	        // Verificar se já existe um agendamento no mesmo dia e horário para o mesmo profissional
	        boolean exists = agendamentoSessaoRepository.existsByDataHoraAndProfissionalId(agendamentoDTO.getDataHora(), agendamentoDTO.getProfissionalId());
	        if (exists) {
	            // Retorna status 204 (No Content) sem tentar criar outro agendamento
	            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	        }

	        AgendamentoSessao agendamento = new AgendamentoSessao();
	        agendamento.setDataHora(agendamentoDTO.getDataHora());
	        agendamento.setStatus(agendamentoDTO.getStatus());
	        agendamento.setPaciente(paciente.get());
	        agendamento.setProfissional(profissional.get());
	        agendamento.setObservacao(agendamentoDTO.getObservacao());
	        AgendamentoSessao salvo = agendamentoSessaoRepository.save(agendamento);
	        return ResponseEntity.ok(new AgendamentoSessaoDTO(salvo));
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}



	@PutMapping("/{id}")
	public ResponseEntity<AgendamentoSessaoDTO> atualizar(@PathVariable Long id,
			@RequestBody AgendamentoSessaoDTO agendamentoDTO) {
		return agendamentoSessaoRepository.findById(id).<ResponseEntity<AgendamentoSessaoDTO>>map(agendamento -> {
			if (agendamento.getStatus() == Status.PENDENTE) {
				agendamento.setDataHora(agendamentoDTO.getDataHora());
				agendamento.setStatus(agendamentoDTO.getStatus());
				agendamento.setProfissional(
						profissionalSaudeRepository.findById(agendamentoDTO.getProfissionalId()).orElseThrow());
				agendamento.setObservacao(agendamentoDTO.getObservacao());

				AgendamentoSessao atualizado = agendamentoSessaoRepository.save(agendamento);
				return ResponseEntity.ok(new AgendamentoSessaoDTO(atualizado));
			} else {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
			}
		}).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Long id) {
	    return agendamentoSessaoRepository.findById(id).map(agendamento -> {
	        agendamentoSessaoRepository.delete(agendamento);
	        return ResponseEntity.ok().<Void>build();
	    }).orElseGet(() -> ResponseEntity.notFound().build());
	}
}
