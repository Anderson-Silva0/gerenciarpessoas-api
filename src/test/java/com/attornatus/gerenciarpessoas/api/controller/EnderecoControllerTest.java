package com.attornatus.gerenciarpessoas.api.controller;

import com.attornatus.gerenciarpessoas.api.dto.EnderecoDTO;
import com.attornatus.gerenciarpessoas.api.dto.PessoaDTO;
import com.attornatus.gerenciarpessoas.model.entity.Endereco;
import com.attornatus.gerenciarpessoas.model.entity.Pessoa;
import com.attornatus.gerenciarpessoas.model.enums.EnderecoPrincipal;
import com.attornatus.gerenciarpessoas.service.EnderecoService;
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
class EnderecoControllerTest {
    @InjectMocks
    private EnderecoController enderecoController;
    @Mock
    private EnderecoService enderecoService;
    @Mock
    private PessoaService pessoaService;
    @Mock
    private ModelMapper mapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        criarPessoa();
        criarPessoaDTO();
        criarEndereco();
        criarEnderecoDTO();
    }

    @Test
    void deveRetornarStatusOkQuandoCriarUmUsuario() {
        Mockito.when(pessoaService.consultarPessoa(Mockito.anyLong())).thenReturn(Optional.of(criarPessoa()));
        Mockito.when(enderecoService.criarEnderecoPessoa(criarEnderecoDTO())).thenReturn(criarEndereco());
        Mockito.doNothing().when(enderecoService).setEnderecoPrincipalPessoa(criarEndereco());
        Mockito.when(mapper.map(Mockito.any(), Mockito.any())).thenReturn(criarEnderecoDTO());

        ResponseEntity<EnderecoDTO> response = enderecoController.criarEndereco(1l, criarEnderecoDTO());

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(ResponseEntity.class, response.getClass());
        Assertions.assertEquals(EnderecoDTO.class, response.getBody().getClass());
        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
    }

    @Test
    void deveRetornarUmaListaDeEnderecosPorIdDaPessoa() {
        Mockito.when(enderecoService.listarEnderecosDaPessoa(Mockito.anyLong()))
                .thenReturn(Arrays.asList(criarEndereco()));
        Mockito.when(mapper.map(Mockito.any(), Mockito.any())).thenReturn(criarEnderecoDTO());

        ResponseEntity<List<EnderecoDTO>> listResponse = enderecoController.listarEnderecosPessoa(1l);

        Assertions.assertNotNull(listResponse);
        Assertions.assertNotNull(listResponse.getBody());
        Assertions.assertEquals(ResponseEntity.class, listResponse.getClass());
        Assertions.assertEquals(ArrayList.class, listResponse.getBody().getClass());
        Assertions.assertEquals(EnderecoDTO.class, listResponse.getBody().get(0).getClass());
        Assertions.assertEquals(HttpStatus.OK.value(), listResponse.getStatusCode().value());
        Assertions.assertEquals(HttpStatus.OK, listResponse.getStatusCode());
    }

    @Test
    void deveDeletarOEnderecoPrincipalPorOIdDaPessoaERetornarNoContent() {
        Mockito.doNothing().when(enderecoService).deletarEnderecoPrincipal(1l);

        ResponseEntity response = enderecoController.deletarEnderecoPrincipal(1l);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(ResponseEntity.class, response.getClass());
        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        Mockito.verify(enderecoService, Mockito.times(1)).deletarEnderecoPrincipal(1l);
    }

    @Test
    void deveRetornarOEnderecoPrincipalDaPessoaPorOIdDela() {
        Mockito.when(enderecoService.buscarEnderecoPrincipal(1l)).thenReturn(criarEndereco());
        Mockito.when(mapper.map(Mockito.any(), Mockito.any())).thenReturn(criarEnderecoDTO());

        ResponseEntity<EnderecoDTO> response = enderecoController.buscarEnderecoPrincipal(1l);

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(ResponseEntity.class, response.getClass());
        Assertions.assertEquals(EnderecoDTO.class, response.getBody().getClass());
        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
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

    private Endereco criarEndereco() {
        return Endereco.builder()
                .id(1l)
                .cep("51250545")
                .cidade("Recife")
                .logradouro("Rua flores do oriente")
                .numero(100l)
                .pessoa(criarPessoa())
                .isPrincipal(EnderecoPrincipal.SIM)
                .build();
    }

    private EnderecoDTO criarEnderecoDTO() {
        return EnderecoDTO.builder()
                .id(1l)
                .cep("51250545")
                .cidade("Recife")
                .logradouro("Rua flores do oriente")
                .numero(100l)
                .pessoa(criarPessoa())
                .isPrincipal("SIM")
                .build();
    }
}