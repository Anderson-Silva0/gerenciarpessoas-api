package com.attornatus.gerenciarpessoas.service;

import com.attornatus.gerenciarpessoas.api.dto.PessoaDTO;
import com.attornatus.gerenciarpessoas.model.entity.Pessoa;

import java.util.List;
import java.util.Optional;

public interface PessoaService {
    Pessoa criarPessoa(PessoaDTO pessoaDTO);

    Pessoa editarPessoa(PessoaDTO pessoaDTO);

    Optional<Pessoa> consultarPessoa(PessoaDTO pessoaDTO);

    List<Pessoa> listarPessoas();
}
