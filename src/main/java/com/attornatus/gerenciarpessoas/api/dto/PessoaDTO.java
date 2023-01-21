package com.attornatus.gerenciarpessoas.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PessoaDTO {

    private Long id;

    @NotBlank(message = "O campo Nome é obrigatório.")
    @Length (min = 3, max = 50)
    private String nome;

    @NotBlank(message = "O campo Data de Nascimento é obrigatório.")
    @Length(min = 10, max = 10)
    private String dataNascimento;

    @JsonIgnore
    private Long enderecoPrincipal;
}
