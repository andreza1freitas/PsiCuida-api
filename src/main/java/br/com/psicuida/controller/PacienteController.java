package br.com.psicuida.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.psicuida.entity.Paciente;
import br.com.psicuida.enums.Situacao;
import br.com.psicuida.model.PacienteDTO;
import br.com.psicuida.repository.PacienteRepository;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteRepository pacienteRepository;

    @GetMapping
    public List<PacienteDTO> listarTodos() {
        return pacienteRepository.findAll().stream()
                .map(PacienteDTO::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PacienteDTO> buscarPorId(@PathVariable Long id) {
        Optional<Paciente> paciente = pacienteRepository.findById(id);
        return paciente.map(value -> ResponseEntity.ok(new PacienteDTO(value)))
                       .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PacienteDTO> criar(@RequestBody PacienteDTO pacienteDTO) {
        Paciente paciente = new Paciente();
        paciente.setNome(pacienteDTO.getNome());
        paciente.setEmail(pacienteDTO.getEmail());
        paciente.setTelefone(pacienteDTO.getTelefone());
        paciente.setDataNascimento(pacienteDTO.getDataNascimento());
        paciente.setGenero(pacienteDTO.getGenero());
        paciente.setEstadoCivil(pacienteDTO.getEstadoCivil());
        paciente.setTratamento(pacienteDTO.getTratamento());
        paciente.setSenha(pacienteDTO.getSenha());
        paciente.setSituacao(Situacao.ATIVO);
        
        Paciente salvo = pacienteRepository.save(paciente);
        return ResponseEntity.ok(new PacienteDTO(salvo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PacienteDTO> atualizar(@PathVariable Long id, @RequestBody PacienteDTO pacienteDTO) {
        return pacienteRepository.findById(id)
                .map(paciente -> {
                    paciente.setNome(pacienteDTO.getNome());
                    paciente.setEmail(pacienteDTO.getEmail());
                    paciente.setTelefone(pacienteDTO.getTelefone());
                    paciente.setDataNascimento(pacienteDTO.getDataNascimento());
                    paciente.setGenero(pacienteDTO.getGenero());
                    paciente.setEstadoCivil(pacienteDTO.getEstadoCivil());
                    paciente.setTratamento(pacienteDTO.getTratamento());
                    paciente.setSenha(pacienteDTO.getSenha());
                    
                    Paciente atualizado = pacienteRepository.save(paciente);
                    return ResponseEntity.ok(new PacienteDTO(atualizado));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        return pacienteRepository.findById(id)
                .map(paciente -> {
                    pacienteRepository.delete(paciente);
                    return ResponseEntity.ok().<Void>build(); 
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @PutMapping("/desativar/{id}")
    public ResponseEntity<Void> desativar(@PathVariable Long id) {
        return pacienteRepository.findById(id)
                .map(paciente -> {
                    paciente.setSituacao(Situacao.INATIVO); 
                    pacienteRepository.save(paciente);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
