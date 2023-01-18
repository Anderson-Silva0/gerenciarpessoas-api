package com.attornatus.gerenciarpessoas.service;

import com.attornatus.gerenciarpessoas.model.entity.Endereco;
import com.attornatus.gerenciarpessoas.model.entity.Pessoa;

import java.util.List;

public interface EnderecoService {

    Endereco criarEnderecoPessoa(Pessoa pessoa, Endereco endereco);

    List<Endereco> listarEnderecoPessoa(Pessoa pessoa);

    void setEnderecoPrincipal();
}
