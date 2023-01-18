package com.attornatus.gerenciarpessoas.service;

import com.attornatus.gerenciarpessoas.model.entity.Pessoa;

import java.util.List;
import java.util.Optional;

public interface PessoaService {
    Pessoa criarPessoa(Pessoa pessoa);

    Pessoa editarPessoa(Pessoa pessoa);

    Optional<Pessoa> consultarPessoa(Pessoa pessoa);

    List<Pessoa> listarPessoas();
}
