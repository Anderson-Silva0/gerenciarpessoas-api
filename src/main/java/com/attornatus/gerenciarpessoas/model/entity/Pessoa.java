package com.attornatus.gerenciarpessoas.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "pessoa", schema = "gerencia")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pessoa")
    private Long id;

    @Column(name = "nome")
    @NotBlank(message = "O campo Nome é obrigatório.")
    @Length(min = 3, max = 50)
    private String nome;

    @Column(name = "data_nascimento")
    @NotBlank(message = "O campo Data de Nascimento é obrigatório.")
    @Length(min = 10, max = 10)
    private String dataNascimento;

    @Column(name = "endereco_principal")
    private Long enderecoPrincipal;
}
