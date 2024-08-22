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

import br.com.psicuida.entity.Paciente;
import br.com.psicuida.entity.Pergunta;
import br.com.psicuida.model.PerguntaDTO;
import br.com.psicuida.repository.PerguntaRepository;

@RestController
@RequestMapping("/perguntas")
public class PerguntaController {

    @Autowired
    private PerguntaRepository perguntaRepository;
    
    @GetMapping
    public List<PerguntaDTO> listarTodos(@RequestParam Long pacienteId) {
    	var listaPerguntas = perguntaRepository.findByPacienteId(pacienteId).stream()
        .map(PerguntaDTO::new)
        .collect(Collectors.toList());
        return listaPerguntas;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PerguntaDTO> buscarPorId(@PathVariable Long id) {
        Optional<Pergunta> pergunta = perguntaRepository.findById(id);
        return pergunta.map(value -> ResponseEntity.ok(new PerguntaDTO(value)))
                       .orElseGet(() -> ResponseEntity.notFound().build());
    }
    

    @PostMapping
    public ResponseEntity<PerguntaDTO> adicionarPergunta(@RequestBody PerguntaDTO perguntaDTO) {
        Pergunta novaPergunta = new Pergunta();
        novaPergunta.setTitulo(perguntaDTO.getTitulo());
        novaPergunta.setDescricao(perguntaDTO.getDescricao());

        // Assumindo que o pacienteId é enviado como parte do DTO
        Paciente paciente = new Paciente();
        paciente.setId(perguntaDTO.getPacienteId());
        novaPergunta.setPaciente(paciente);

        Pergunta perguntaSalva = perguntaRepository.save(novaPergunta);
        return ResponseEntity.status(HttpStatus.CREATED).body(new PerguntaDTO(perguntaSalva));
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

