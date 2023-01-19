package com.attornatus.gerenciarpessoas.service;

import com.attornatus.gerenciarpessoas.api.dto.EnderecoDTO;
import com.attornatus.gerenciarpessoas.model.entity.Endereco;

import java.util.List;

public interface EnderecoService {

    Endereco criarEnderecoPessoa(EnderecoDTO enderecoDTO);

    List<Endereco> listarEnderecosDaPessoa(Long id);

    Endereco buscarEnderecoPrincipal(Long idPessoa);

    void deletarEnderecoPrincipal(Long id);

    void setEnderecoPrincipalPessoa(Endereco endereco);
}
