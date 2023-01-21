package com.attornatus.gerenciarpessoas.api.controller;

import com.attornatus.gerenciarpessoas.api.dto.EnderecoDTO;
import com.attornatus.gerenciarpessoas.model.entity.Endereco;
import com.attornatus.gerenciarpessoas.model.entity.Pessoa;
import com.attornatus.gerenciarpessoas.service.EnderecoService;
import com.attornatus.gerenciarpessoas.service.PessoaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/endereco")
public class EnderecoController {

    @Autowired
    private EnderecoService service;

    @Autowired
    private PessoaService pessoaService;

    @Autowired
    private ModelMapper mapper;

    @PostMapping("/{id}")
    public ResponseEntity<EnderecoDTO> criarEndereco(@Valid @PathVariable("id") Long id, @RequestBody EnderecoDTO enderecoDTO) {
        Optional<Pessoa> pessoa = pessoaService.consultarPessoa(id);
        enderecoDTO.setPessoa(pessoa.get());
        Endereco endereco = service.criarEnderecoPessoa(enderecoDTO);
        service.setEnderecoPrincipalPessoa(endereco);
        return ResponseEntity.ok().body(mapper.map(endereco, EnderecoDTO.class));
    }

    @GetMapping(value = "/enderecos-pessoa/{id}")
    public ResponseEntity<List<EnderecoDTO>> listarEnderecosPessoa(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(service.listarEnderecosDaPessoa(id)
                .stream()
                .map(x -> mapper.map(x, EnderecoDTO.class))
                .collect(Collectors.toList()));
    }

    @DeleteMapping(value = "deletar-principal/{id}")
    public ResponseEntity deletarEnderecoPrincipal(@PathVariable("id") Long id) {
            service.deletarEnderecoPrincipal(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/buscar-principal/{id}")
    public ResponseEntity<EnderecoDTO> buscarEnderecoPrincipal(@PathVariable("id") Long id) {
        return ResponseEntity.ok()
                .body( mapper.map(service.buscarEnderecoPrincipal(id), EnderecoDTO.class));
    }
}
