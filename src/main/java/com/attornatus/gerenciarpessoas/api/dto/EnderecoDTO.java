package com.attornatus.gerenciarpessoas.api.dto;

import com.attornatus.gerenciarpessoas.model.entity.Pessoa;
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
public class EnderecoDTO {

    private Long id;

    @NotBlank(message = "O campo Logradouro é obrigátorio.")
    @Length(min = 2, max = 150)
    private String logradouro;

    @NotBlank(message = "O campo CEP é obrigátorio.")
    @Length(min = 8, max = 8)
    private String cep;

    @NotBlank(message = "O campo Numero é obrigátorio.")
    private Long numero;

    @NotBlank(message = "O campo Cidade é obrigátorio.")
    @Length(min = 2, max = 50)
    private String cidade;

    @NotBlank(message = "O campo id_pessoa é obrigátorio.")
    private Pessoa pessoa;

    @NotBlank(message = "O campo isPrincipal é obrigátorio.")
    @Length(min = 3, max = 3)
    private String isPrincipal;
}
