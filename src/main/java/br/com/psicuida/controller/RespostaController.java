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
import br.com.psicuida.entity.Resposta;
import br.com.psicuida.model.RespostaDTO;
import br.com.psicuida.repository.PerguntaRepository;
import br.com.psicuida.repository.RespostaRepository;

@RestController
@RequestMapping("/respostas")
public class RespostaController {

    @Autowired
    private RespostaRepository respostaRepository;
    
    @Autowired
    private PerguntaRepository perguntaRepository;

    @GetMapping
    public List<RespostaDTO> listarTodos() {
        return respostaRepository.findAll().stream()
                .map(RespostaDTO::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RespostaDTO> buscarPorId(@PathVariable Long id) {
        Optional<Resposta> resposta = respostaRepository.findById(id);
        return resposta.map(value -> ResponseEntity.ok(new RespostaDTO(value)))
                       .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<RespostaDTO> criar(@RequestBody RespostaDTO respostaDTO) {
        Optional<Pergunta> pergunta = perguntaRepository.findById(respostaDTO.getPerguntaId());
        
        if (pergunta.isPresent()) {
            Resposta resposta = new Resposta();
            resposta.setConteudo(respostaDTO.getConteudo());
            resposta.setPergunta(pergunta.get());
            
            Resposta salvo = respostaRepository.save(resposta);
            return ResponseEntity.ok(new RespostaDTO(salvo));
        } else {
            return ResponseEntity.notFound().build();  // Se a pergunta não for encontrada
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<RespostaDTO> atualizar(@PathVariable Long id, @RequestBody RespostaDTO respostaDTO) {
        return respostaRepository.findById(id)
                .map(resposta -> {
                    resposta.setConteudo(respostaDTO.getConteudo());
                    Resposta atualizado = respostaRepository.save(resposta);
                    return ResponseEntity.ok(new RespostaDTO(atualizado));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        return respostaRepository.findById(id)
                .map(resposta -> {
                    respostaRepository.delete(resposta);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
