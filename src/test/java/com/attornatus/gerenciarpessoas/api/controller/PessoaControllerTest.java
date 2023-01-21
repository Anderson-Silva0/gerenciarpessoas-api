package com.attornatus.gerenciarpessoas.api.controller;

import com.attornatus.gerenciarpessoas.api.dto.PessoaDTO;
import com.attornatus.gerenciarpessoas.model.entity.Pessoa;
import com.attornatus.gerenciarpessoas.service.PessoaService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class PessoaControllerTest {

    @InjectMocks
    private PessoaController pessoaController;

    @Mock
    private PessoaService pessoaService;

    @Mock
    private ModelMapper mapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        criarPessoa();
        criarPessoaDTO();
    }

    @Test
    void quandoCriarUmaPessoaRetornarOStatusOk() {
        Mockito.when(pessoaService.criarPessoa(criarPessoaDTO())).thenReturn(criarPessoa());
        Mockito.when(mapper.map(Mockito.any(), Mockito.any())).thenReturn(criarPessoaDTO());

        ResponseEntity<PessoaDTO> response = pessoaController.criarPessoa(criarPessoaDTO());

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(ResponseEntity.class, response.getClass());
        Assertions.assertEquals(PessoaDTO.class, response.getBody().getClass());
        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
    }

    @Test
    void quantoEditarUmaPessoaRetornarOStatusOk() {
        PessoaDTO pessoaDTO = criarPessoaDTO();
        Pessoa pessoa = criarPessoa();
        Mockito.when(pessoaService.editarPessoa(pessoaDTO)).thenReturn(pessoa);
        Mockito.when(mapper.map(Mockito.any(), Mockito.any())).thenReturn(pessoaDTO);

        ResponseEntity<PessoaDTO> response = pessoaController.editarPessoa(5l, pessoaDTO);

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(5l, response.getBody().getId());
        Assertions.assertEquals(ResponseEntity.class, response.getClass());
        Assertions.assertEquals(PessoaDTO.class, response.getBody().getClass());
        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
    }

    @Test
    void deveConsultarUmaPessoaPorIdComSucesso() {
        Mockito.when(pessoaService.consultarPessoa(Mockito.anyLong())).thenReturn(Optional.of(criarPessoa()));
        Mockito.when(mapper.map(Mockito.any(), Mockito.any())).thenReturn(criarPessoaDTO());

        ResponseEntity<PessoaDTO> response = pessoaController.consultarPessoa(1l);

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(ResponseEntity.class, response.getClass());
        Assertions.assertEquals(PessoaDTO.class, response.getBody().getClass());
        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
    }

    @Test
    void deveRetornarUmaListaDePessoaDTOComStatusOk() {
        Mockito.when(pessoaService.listarPessoas()).thenReturn(Arrays.asList(criarPessoa()));
        Mockito.when(mapper.map(Mockito.any(), Mockito.any())).thenReturn(criarPessoaDTO());

        ResponseEntity<List<PessoaDTO>> listResponse = pessoaController.listarPessoas();

        Assertions.assertNotNull(listResponse);
        Assertions.assertNotNull(listResponse.getBody());
        Assertions.assertEquals(ResponseEntity.class, listResponse.getClass());
        Assertions.assertEquals(ArrayList.class, listResponse.getBody().getClass());
        Assertions.assertEquals(PessoaDTO.class, listResponse.getBody().get(0).getClass());
        Assertions.assertEquals(HttpStatus.OK.value(), listResponse.getStatusCode().value());
        Assertions.assertEquals(HttpStatus.OK, listResponse.getStatusCode());
    }

    private Pessoa criarPessoa() {
        return Pessoa.builder()
                .id(1l)
                .nome("Anderson Silva")
                .dataNascimento("01/02/1999")
                .enderecoPrincipal(1l)
                .build();
    }

    private PessoaDTO criarPessoaDTO() {
        return PessoaDTO.builder()
                .id(1l)
                .nome("Anderson Silva")
                .dataNascimento("01/02/1999")
                .enderecoPrincipal(1l)
                .build();
    }
}