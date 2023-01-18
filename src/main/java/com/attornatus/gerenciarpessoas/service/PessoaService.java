package com.attornatus.gerenciarpessoas.service;

import com.attornatus.gerenciarpessoas.model.entity.Pessoa;

import java.util.List;

public interface PessoaService {
    Pessoa salvarPessoa(Pessoa pessoa);

    Pessoa atualizarPessoa(Pessoa pessoa);

    Pessoa ConsultarPessoa(Pessoa pessoa);

    List<Pessoa> ListarPessoas();
}
