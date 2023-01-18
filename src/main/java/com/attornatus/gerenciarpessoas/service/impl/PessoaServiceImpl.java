package com.attornatus.gerenciarpessoas.service.impl;

import com.attornatus.gerenciarpessoas.api.dto.PessoaDTO;
import com.attornatus.gerenciarpessoas.model.entity.Pessoa;
import com.attornatus.gerenciarpessoas.model.repository.PessoaRepository;
import com.attornatus.gerenciarpessoas.service.PessoaService;
import com.attornatus.gerenciarpessoas.service.exception.DataIntegratyViolationException;
import com.attornatus.gerenciarpessoas.service.exception.ObjectNotFoundException;
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
            if(pessoaDTO.getId() != null && pessoaDTO.getId().equals("")) {
                throw new DataIntegratyViolationException("Para salvar um usuário nao precisa informar id.");
            }
            return repository.save( mapper.map(pessoaDTO, Pessoa.class) );
    }

    @Override
    public Pessoa editarPessoa(PessoaDTO pessoaDTO) {
        if(!repository.existsById(pessoaDTO.getId())){
            throw new ObjectNotFoundException("Pessoa não encontrada para o Id informado.");
        }
        return repository.save( mapper.map(pessoaDTO, Pessoa.class) );
    }

    @Override
    public Optional<Pessoa> consultarPessoa(Long id ) {
        if(repository.existsById(id))
            return repository.findById(id);
        else
            throw new ObjectNotFoundException("Impossível consultar Pessoa. Informe um Id válido.");
    }

    @Override
    public List<Pessoa> listarPessoas() {
        return repository.findAll();
    }
}
