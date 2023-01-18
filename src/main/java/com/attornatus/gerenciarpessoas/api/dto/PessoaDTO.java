package com.attornatus.gerenciarpessoas.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PessoaDTO {

    private Long id;

    @NotEmpty (message = "O campo Nome é obrigatório.")
    @Length (max = 10)
    private String nome;

    @NotEmpty(message = "O campo Data de Nascimento é obrigatório.")
    @Length(min = 8, max = 10)
    private String dataNascimento;

    private Long enderecoPrincipal;
}
