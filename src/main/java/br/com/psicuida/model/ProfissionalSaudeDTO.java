package br.com.psicuida.model;

import br.com.psicuida.entity.ProfissionalSaude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfissionalSaudeDTO {

    private Long id;
    private String nome;
    private String profissao;
    private String email;
    private String telefone;

    public ProfissionalSaudeDTO(ProfissionalSaude profissional) {
        this.id = profissional.getId();
        this.nome = profissional.getNome();
        this.profissao = profissional.getProfissao();
        this.email = profissional.getEmail();
        this.telefone = profissional.getTelefone();
    }
}
