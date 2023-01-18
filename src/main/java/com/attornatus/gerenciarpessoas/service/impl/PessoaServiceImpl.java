package com.attornatus.gerenciarpessoas.service.impl;

import com.attornatus.gerenciarpessoas.api.dto.PessoaDTO;
import com.attornatus.gerenciarpessoas.model.entity.Pessoa;
import com.attornatus.gerenciarpessoas.model.repository.PessoaRepository;
import com.attornatus.gerenciarpessoas.service.PessoaService;
import com.attornatus.gerenciarpessoas.service.exception.DataIntegratyViolationException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PessoaServiceImpl implements PessoaService {

    @Autowired
    private PessoaRepository repository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public Pessoa criarPessoa(PessoaDTO pessoaDTO) {
        return repository.save( mapper.map(pessoaDTO, Pessoa.class) );
    }

    @Override
    public Pessoa editarPessoa(PessoaDTO pessoaDTO) {
        if (pessoaDTO.getId() != null)
            return repository.save( mapper.map(pessoaDTO, Pessoa.class) );
        else
            throw new DataIntegratyViolationException("Erro ao tentar atualizar pessoa, informe o Id.");
    }

    @Override
    public Optional<Pessoa> consultarPessoa(PessoaDTO pessoaDTO) {
        return repository.findById(pessoaDTO.getId());
    }

    @Override
    public List<Pessoa> listarPessoas() {
        return repository.findAll();
    }
}
