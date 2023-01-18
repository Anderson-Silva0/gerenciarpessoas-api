package com.attornatus.gerenciarpessoas.service.impl;

import com.attornatus.gerenciarpessoas.model.entity.Pessoa;
import com.attornatus.gerenciarpessoas.model.repository.PessoaRepository;
import com.attornatus.gerenciarpessoas.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PessoaServiceImpl implements PessoaService {

    @Autowired
    private PessoaRepository repository;

    @Override
    public Pessoa salvarPessoa(Pessoa pessoa) {
        return repository.save(pessoa);
    }

    @Override
    public Pessoa atualizarPessoa(Pessoa pessoa) {
        return null;
    }

    @Override
    public Pessoa ConsultarPessoa(Pessoa pessoa) {
        return null;
    }

    @Override
    public List<Pessoa> ListarPessoas() {
        return null;
    }
}
