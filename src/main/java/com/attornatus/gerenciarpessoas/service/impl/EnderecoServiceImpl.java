package com.attornatus.gerenciarpessoas.service.impl;

import com.attornatus.gerenciarpessoas.api.dto.EnderecoDTO;
import com.attornatus.gerenciarpessoas.api.dto.PessoaDTO;
import com.attornatus.gerenciarpessoas.model.entity.Endereco;
import com.attornatus.gerenciarpessoas.model.entity.Pessoa;
import com.attornatus.gerenciarpessoas.model.enums.EnderecoPrincipal;
import com.attornatus.gerenciarpessoas.model.repository.EnderecoRepository;
import com.attornatus.gerenciarpessoas.model.repository.PessoaRepository;
import com.attornatus.gerenciarpessoas.service.EnderecoService;
import com.attornatus.gerenciarpessoas.service.PessoaService;
import com.attornatus.gerenciarpessoas.service.exception.DataIntegratyViolationException;
import com.attornatus.gerenciarpessoas.service.exception.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnderecoServiceImpl implements EnderecoService {
    @Autowired
    private EnderecoRepository repository;

    @Autowired
    private PessoaService pessoaService;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private ModelMapper mapper;

    private boolean temEnderecoPrincipal = false;
    private Endereco enderecoPrincipal = new Endereco();

    @Override
    public Endereco criarEnderecoPessoa(EnderecoDTO enderecoDTO) {
        Endereco endereco = mapper.map(enderecoDTO, Endereco.class);
        endereco.setIsPrincipal(EnderecoPrincipal.valueOf( enderecoDTO.getIsPrincipal() ));
        if(endereco.getPessoa().getEnderecoPrincipal() == null
                || endereco.getIsPrincipal() == EnderecoPrincipal.NAO) {
            return repository.save(endereco);
        } else {
            throw new DataIntegratyViolationException("Essa pessoa já possui um endereço principal. " +
                    "Para cadastrar um novo endereço principal delete o antigo.");
        }
    }

    public Endereco buscarEnderecoPrincipal(Long idPessoa) {
        enderecoPrincipal = null;
        List<Endereco> enderecos = listarEnderecosDaPessoa(idPessoa);
        enderecos.stream().forEach(e -> {
            if (e.getIsPrincipal() == EnderecoPrincipal.SIM) {
                enderecoPrincipal = mapper.map(e, Endereco.class);
            }
        });
        if (enderecoPrincipal == null) {
            throw new ObjectNotFoundException("A Pessoa não possui um Endereço Principal.");
        }
        return enderecoPrincipal;
    }

    @Override
    public List<Endereco> listarEnderecosDaPessoa(Long idPessoa) {
        if (!pessoaRepository.existsById(idPessoa))
            throw new ObjectNotFoundException("Pessoa não encontrada para o Id informado.");
        return repository.buscarTodosEnderecosPorIdPessoa(idPessoa);
    }


    public void deletarEnderecoPrincipal(Long idPessoa) {
        temEnderecoPrincipal = false;
        List<Endereco> enderecos = listarEnderecosDaPessoa(idPessoa);
        if(enderecos.isEmpty())
            throw new ObjectNotFoundException("Erro ao tentar Deletar. " +
                    "Essa Pessoa não possui nenhum endereço.");
        enderecos.stream().forEach(endereco -> {
            if (endereco.getIsPrincipal() == EnderecoPrincipal.SIM) {
                repository.deleteById(endereco.getId());
                Pessoa pessoa = endereco.getPessoa();
                pessoa.setEnderecoPrincipal(null);
                pessoaRepository.save(pessoa);
                temEnderecoPrincipal = true;
            }
        });
        if(!temEnderecoPrincipal)
            throw new ObjectNotFoundException("Erro ao tentar Deletar. " +
                    "Essa Pessoa não possui Endereço Principal.");
    }

    @Override
    public void setEnderecoPrincipalPessoa(Endereco endereco) {
        if (endereco.getIsPrincipal() == EnderecoPrincipal.SIM) {
            Pessoa pessoa = endereco.getPessoa();
            PessoaDTO pessoaDTO = mapper.map(pessoa, PessoaDTO.class);
            pessoaDTO.setEnderecoPrincipal(endereco.getId());
            pessoaService.editarPessoa(pessoaDTO);
        }
    }
}

