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

import br.com.psicuida.entity.ProfissionalSaude;
import br.com.psicuida.model.ProfissionalSaudeDTO;
import br.com.psicuida.repository.ProfissionalSaudeRepository;

@RestController
@RequestMapping("/profissionais")
public class ProfissionalSaudeController {

    @Autowired
    private ProfissionalSaudeRepository profissionalRepository;

    @GetMapping
    public List<ProfissionalSaudeDTO> listarTodos() {
        return profissionalRepository.findAll().stream()
                .map(ProfissionalSaudeDTO::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfissionalSaudeDTO> buscarPorId(@PathVariable Long id) {
        Optional<ProfissionalSaude> profissional = profissionalRepository.findById(id);
        return profissional.map(value -> ResponseEntity.ok(new ProfissionalSaudeDTO(value)))
                           .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ProfissionalSaudeDTO> criar(@RequestBody ProfissionalSaudeDTO profissionalDTO) {
        ProfissionalSaude profissional = new ProfissionalSaude();
        profissional.setNome(profissionalDTO.getNome());
        profissional.setProfissao(profissionalDTO.getProfissao());
        profissional.setEmail(profissionalDTO.getEmail());
        profissional.setTelefone(profissionalDTO.getTelefone());
        
        ProfissionalSaude salvo = profissionalRepository.save(profissional);
        return ResponseEntity.ok(new ProfissionalSaudeDTO(salvo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfissionalSaudeDTO> atualizar(@PathVariable Long id, @RequestBody ProfissionalSaudeDTO profissionalDTO) {
        return profissionalRepository.findById(id)
                .map(profissional -> {
                    profissional.setNome(profissionalDTO.getNome());
                    profissional.setProfissao(profissionalDTO.getProfissao());
                    profissional.setEmail(profissionalDTO.getEmail());
                    profissional.setTelefone(profissionalDTO.getTelefone());
                    ProfissionalSaude atualizado = profissionalRepository.save(profissional);
                    return ResponseEntity.ok(new ProfissionalSaudeDTO(atualizado));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        return profissionalRepository.findById(id)
                .map(profissional -> {
                    profissionalRepository.delete(profissional);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
