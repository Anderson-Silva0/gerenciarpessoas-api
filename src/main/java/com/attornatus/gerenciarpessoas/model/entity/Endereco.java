package com.attornatus.gerenciarpessoas.model.entity;

import com.attornatus.gerenciarpessoas.model.enums.EnderecoPrincipal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "endereco", schema = "gerencia")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_endereco")
    private Long id;

    @Column(name = "logradouro")
    @NotBlank(message = "O campo Logradouro é obrigátorio.")
    @Length(min = 2, max = 150)
    private String logradouro;

    @Column(name = "cep")
    @NotBlank(message = "O campo CEP é obrigátorio.")
    @Length(min = 8, max = 8)
    private String cep;

    @Column(name = "numero")
    private Long numero;

    @Column(name = "cidade")
    @NotBlank(message = "O campo Cidade é obrigátorio.")
    @Length(min = 2, max = 50)
    private String cidade;

    @ManyToOne
    @JoinColumn(name = "pessoa")
    private Pessoa pessoa;

    @Column(name = "is_Principal")
    @Enumerated(EnumType.STRING)
    private EnderecoPrincipal isPrincipal;
}
