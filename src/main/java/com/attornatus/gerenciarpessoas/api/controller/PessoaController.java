package com.attornatus.gerenciarpessoas.api.controller;

import com.attornatus.gerenciarpessoas.api.dto.PessoaDTO;
import com.attornatus.gerenciarpessoas.service.PessoaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pessoa")
public class PessoaController {
    @Autowired
    private PessoaService service;

    @Autowired
    private ModelMapper mapper;

    @PostMapping
    public ResponseEntity<PessoaDTO> criarPessoa(@Valid @RequestBody PessoaDTO pessoaDTO) {
        return ResponseEntity.ok()
                .body( mapper.map(service.criarPessoa(pessoaDTO), PessoaDTO.class) );
    }

    @PostMapping(value = "/{id}")
    public ResponseEntity<PessoaDTO> editarPessoa(@Valid @PathVariable("id") Long id, @RequestBody PessoaDTO pessoaDTO) {
        pessoaDTO.setId(id);
        return ResponseEntity.ok()
                .body( mapper.map(service.editarPessoa(pessoaDTO), PessoaDTO.class) );
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PessoaDTO> consultarPessoa(@PathVariable("id") Long id) {
        return ResponseEntity.ok()
                .body(mapper.map(service.consultarPessoa(id).get(), PessoaDTO.class));
    }
    @GetMapping(value = "/listar")
    public ResponseEntity<List<PessoaDTO>> listarPessoas() {
        List<PessoaDTO> pessoaDTOS = service.listarPessoas()
                .stream()
                .map( x -> mapper.map(x, PessoaDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(pessoaDTOS);
    }
}
