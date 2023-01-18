package com.attornatus.gerenciarpessoas.model.entity;

import com.attornatus.gerenciarpessoas.model.enums.EnderecoPrincipal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

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
    @NotEmpty(message = "O campo Logradouro é obrigátorio.")
    @Length(min = 2, max = 150)
    private String logradouro;

    @Column(name = "cep")
    @NotEmpty(message = "O campo CEP é obrigátorio.")
    @Length(min = 8, max = 8)
    private String cep;

    @Column(name = "numero")
    @NotEmpty(message = "O campo Numero é obrigátorio.")
    private Long numero;

    @Column(name = "cidade")
    @NotEmpty(message = "O campo Cidade é obrigátorio.")
    @Length(min = 2, max = 50)
    private String cidade;

    @ManyToOne
    @JoinColumn(name = "id_pessoa")
    @NotEmpty(message = "O campo id_pessoa é obrigátorio.")
    private Pessoa id_pessoa;

    @Column(name = "endereco_principal")
    @Enumerated(EnumType.STRING)
    @NotEmpty(message = "O campo Principal é obrigátorio.")
    @Length(min = 3, max = 3)
    private EnderecoPrincipal principal;
}
