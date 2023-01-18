package com.attornatus.gerenciarpessoas.service.impl;

import com.attornatus.gerenciarpessoas.model.entity.Endereco;
import com.attornatus.gerenciarpessoas.model.entity.Pessoa;
import com.attornatus.gerenciarpessoas.service.EnderecoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnderecoServiceImpl implements EnderecoService {

    @Override
    public Endereco criarEnderecoPessoa(Pessoa pessoa, Endereco endereco) {
        return null;
    }

    @Override
    public List<Endereco> listarEnderecoPessoa(Pessoa pessoa) {
        return null;
    }

    @Override
    public void setEnderecoPrincipal() {

    }
}
