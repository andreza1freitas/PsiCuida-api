package br.com.psicuida.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.psicuida.entity.Pergunta;

public interface PerguntaRepository extends JpaRepository<Pergunta, Long> {

}
