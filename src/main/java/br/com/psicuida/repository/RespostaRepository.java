package br.com.psicuida.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.psicuida.entity.Resposta;

public interface RespostaRepository extends JpaRepository<Resposta, Long> {

	List<Resposta> findByPerguntaId(Long perguntaId);
}