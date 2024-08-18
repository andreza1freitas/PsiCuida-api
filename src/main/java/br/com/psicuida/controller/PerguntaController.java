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

import br.com.psicuida.entity.Pergunta;
import br.com.psicuida.model.PerguntaDTO;
import br.com.psicuida.repository.PerguntaRepository;

@RestController
@RequestMapping("/perguntas")
public class PerguntaController {

    @Autowired
    private PerguntaRepository perguntaRepository;

    @GetMapping
    public List<PerguntaDTO> listarTodos() {
        return perguntaRepository.findAll().stream()
                .map(PerguntaDTO::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PerguntaDTO> buscarPorId(@PathVariable Long id) {
        Optional<Pergunta> pergunta = perguntaRepository.findById(id);
        return pergunta.map(value -> ResponseEntity.ok(new PerguntaDTO(value)))
                       .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PerguntaDTO> criar(@RequestBody PerguntaDTO perguntaDTO) {
        Pergunta pergunta = new Pergunta();
        pergunta.setTitulo(perguntaDTO.getTitulo());
        pergunta.setDescricao(perguntaDTO.getDescricao());
        
        Pergunta salvo = perguntaRepository.save(pergunta);
        return ResponseEntity.ok(new PerguntaDTO(salvo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PerguntaDTO> atualizar(@PathVariable Long id, @RequestBody PerguntaDTO perguntaDTO) {
        return perguntaRepository.findById(id)
                .map(pergunta -> {
                    pergunta.setTitulo(perguntaDTO.getTitulo());
                    pergunta.setDescricao(perguntaDTO.getDescricao());
                    Pergunta atualizado = perguntaRepository.save(pergunta);
                    return ResponseEntity.ok(new PerguntaDTO(atualizado));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        return perguntaRepository.findById(id)
                .map(pergunta -> {
                    perguntaRepository.delete(pergunta);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}

