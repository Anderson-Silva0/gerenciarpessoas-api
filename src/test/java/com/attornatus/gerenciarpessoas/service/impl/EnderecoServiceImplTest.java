package com.attornatus.gerenciarpessoas.service.impl;

import com.attornatus.gerenciarpessoas.api.dto.EnderecoDTO;
import com.attornatus.gerenciarpessoas.api.dto.PessoaDTO;
import com.attornatus.gerenciarpessoas.model.entity.Endereco;
import com.attornatus.gerenciarpessoas.model.entity.Pessoa;
import com.attornatus.gerenciarpessoas.model.enums.EnderecoPrincipal;
import com.attornatus.gerenciarpessoas.model.repository.EnderecoRepository;
import com.attornatus.gerenciarpessoas.model.repository.PessoaRepository;
import com.attornatus.gerenciarpessoas.service.PessoaService;
import com.attornatus.gerenciarpessoas.service.exception.DataIntegratyViolationException;
import com.attornatus.gerenciarpessoas.service.exception.ObjectNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
class EnderecoServiceImplTest {
    @InjectMocks
    private EnderecoServiceImpl enderecoService;
    @Mock
    private EnderecoRepository enderecoRepository;
    @Mock
    private PessoaRepository pessoaRepository;
    @Mock
    private PessoaService pessoaService;
    @Mock
    private ModelMapper mapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        criarEndereco();
        criarEnderecoDTO();
        criarPessoa();
        criarPessoaDTO();
    }

    @Test
    void quandoCriarEnderecoRetornaUmaInstanciaDeEndereco() {
        Endereco endereco = criarEndereco();
        endereco.setId(null);
        endereco.getPessoa().setEnderecoPrincipal(null);

        EnderecoDTO enderecoDTO = criarEnderecoDTO();
        enderecoDTO.setId(null);

        Endereco enderecoSalvo = criarEndereco();

        Mockito.when(mapper.map(Mockito.any(), Mockito.any())).thenReturn(endereco);
        Mockito.when(enderecoRepository.save(endereco)).thenReturn(enderecoSalvo);

        Endereco response = enderecoService.criarEnderecoPessoa(enderecoDTO);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(Endereco.class, response.getClass());
        Assertions.assertEquals(enderecoSalvo.getId(), response.getId());
        Assertions.assertEquals(enderecoSalvo.getCep(), response.getCep());
        Assertions.assertEquals(enderecoSalvo.getCidade(), response.getCidade());
        Assertions.assertEquals(enderecoSalvo.getLogradouro(), response.getLogradouro());
        Assertions.assertEquals(enderecoSalvo.getNumero(), response.getNumero());
        Assertions.assertEquals(enderecoSalvo.getPessoa(), response.getPessoa());
        Assertions.assertEquals(enderecoSalvo.getIsPrincipal(), response.getIsPrincipal());
    }

    @Test
    void deveRetornarErroAoTentarCriarUmEndereco() {
        Endereco endereco = criarEndereco();
        endereco.setId(null);

        EnderecoDTO enderecoDTO = criarEnderecoDTO();
        enderecoDTO.setId(null);

        Mockito.when(mapper.map(Mockito.any(), Mockito.any())).thenReturn(endereco);
        Mockito.when(enderecoRepository.save(endereco)).thenReturn(criarEndereco());

        try {
            Endereco response = enderecoService.criarEnderecoPessoa(enderecoDTO);
        }catch (Exception e) {
            Assertions.assertEquals(DataIntegratyViolationException.class, e.getClass());
            Assertions.assertEquals("Essa pessoa j?? possui um endere??o principal. " +
                    "Para cadastrar um novo endere??o principal delete o antigo.", e.getMessage());
        }
    }

    @Test
    void deveBuscarEnderecoPrincipalPorIdDaPessoaERetornarUmEndereco() {
        Endereco endereco1 = criarEndereco();
        endereco1.setIsPrincipal(EnderecoPrincipal.NAO);
        Endereco endereco2 = criarEndereco();
        endereco2.setIsPrincipal(EnderecoPrincipal.SIM);

        Mockito.when(mapper.map(Mockito.any(), Mockito.any())).thenReturn(endereco2);
        Mockito.when(pessoaRepository.existsById(Mockito.anyLong())).thenReturn(true);
        Mockito.when(enderecoRepository.buscarTodosEnderecosPorIdPessoa(Mockito.anyLong()))
                .thenReturn(Arrays.asList(endereco1, endereco2));

        Endereco response = enderecoService.buscarEnderecoPrincipal(1l);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(Endereco.class, response.getClass());
        Assertions.assertEquals(EnderecoPrincipal.SIM, response.getIsPrincipal());
        Assertions.assertEquals(endereco2.getPessoa(), response.getPessoa());
        Assertions.assertNotEquals(endereco1, response);
    }

    @Test
    void deveLancarErroAoTentarBuscarEnderecoPrincipalPorIdDaPessoa() {
        Endereco endereco1 = criarEndereco();
        endereco1.setIsPrincipal(EnderecoPrincipal.NAO);
        Endereco endereco2 = criarEndereco();
        endereco2.setIsPrincipal(EnderecoPrincipal.SIM);

        Mockito.when(mapper.map(Mockito.any(), Mockito.any())).thenReturn(null);
        Mockito.when(pessoaRepository.existsById(Mockito.anyLong())).thenReturn(true);
        Mockito.when(enderecoRepository.buscarTodosEnderecosPorIdPessoa(Mockito.anyLong()))
                .thenReturn(Arrays.asList(endereco1, endereco2));

        try {
            Endereco response = enderecoService.buscarEnderecoPrincipal(1l);
        }catch (Exception e) {
            Assertions.assertEquals(ObjectNotFoundException.class, e.getClass());
            Assertions.assertEquals("A Pessoa n??o possui um Endere??o Principal.", e.getMessage());
        }
    }

    @Test
    void deveListarTodosEnderecosDaPessoaPorId() {
        Pessoa pessoa = criarPessoa();
        Endereco endereco1 = criarEndereco();
        endereco1.setIsPrincipal(EnderecoPrincipal.NAO);
        Endereco endereco2 = criarEndereco();
        endereco2.setIsPrincipal(EnderecoPrincipal.SIM);

        Mockito.when(enderecoRepository.buscarTodosEnderecosPorIdPessoa(Mockito.anyLong()))
                .thenReturn(Arrays.asList(endereco1, endereco2));

        Mockito.when(pessoaRepository.existsById(Mockito.anyLong())).thenReturn(true);

        List<Endereco> responses = enderecoService.listarEnderecosDaPessoa(pessoa.getId());

        Assertions.assertNotNull(responses);
        Assertions.assertNotNull(responses.get(0));
        Assertions.assertNotNull(responses.get(1));
        Assertions.assertEquals(Endereco.class, responses.get(0).getClass());
        Assertions.assertEquals(Endereco.class, responses.get(1).getClass());
        Assertions.assertEquals(EnderecoPrincipal.NAO, responses.get(0).getIsPrincipal());
        Assertions.assertEquals(EnderecoPrincipal.SIM, responses.get(1).getIsPrincipal());
    }

    @Test
    void deveLancarErroAoTentarListarTodosEnderecosDaPessoaPorId() {
        Pessoa pessoa = criarPessoa();
        Endereco endereco1 = criarEndereco();
        endereco1.setIsPrincipal(EnderecoPrincipal.NAO);
        Endereco endereco2 = criarEndereco();
        endereco2.setIsPrincipal(EnderecoPrincipal.SIM);

        Mockito.when(enderecoRepository.buscarTodosEnderecosPorIdPessoa(Mockito.anyLong()))
                .thenReturn(Arrays.asList(endereco1, endereco2));

        Mockito.when(pessoaRepository.existsById(Mockito.anyLong())).thenReturn(false);

        try {
            List<Endereco> responses = enderecoService.listarEnderecosDaPessoa(pessoa.getId());
        }catch (Exception e) {
            Assertions.assertEquals(ObjectNotFoundException.class, e.getClass());
            Assertions.assertEquals("Pessoa n??o encontrada para o Id informado.", e.getMessage());
        }
    }

    @Test
    void deveDeletarOEnderecoPrincipal() {
        Mockito.when(pessoaRepository.existsById(Mockito.anyLong())).thenReturn(true);
        Mockito.when(enderecoRepository.buscarTodosEnderecosPorIdPessoa(Mockito.anyLong()))
                .thenReturn(Arrays.asList(criarEndereco()));
        Mockito.doNothing().when(enderecoRepository).deleteById(Mockito.anyLong());

        enderecoService.deletarEnderecoPrincipal(1l);

        Mockito.verify(enderecoRepository ,Mockito.times(1))
                .deleteById(Mockito.anyLong());
        Mockito.verify(pessoaRepository ,Mockito.times(1))
                .save(Mockito.any());
    }

    @Test
    void deveLancarErroAoTentarDeletarUmaPessoaQueNaoPossuiNenhumEnderecoCadastrado() {
        Mockito.when(pessoaRepository.existsById(Mockito.anyLong())).thenReturn(true);
        Mockito.when(enderecoRepository.buscarTodosEnderecosPorIdPessoa(Mockito.anyLong()))
                .thenReturn(new ArrayList<>());
        Mockito.doNothing().when(enderecoRepository).deleteById(Mockito.anyLong());

        try {
            enderecoService.deletarEnderecoPrincipal(1l);
        }catch (Exception e) {
            Assertions.assertEquals(ObjectNotFoundException.class, e.getClass());
            Assertions.assertEquals("Erro ao tentar Deletar. " +
                    "Essa Pessoa n??o possui nenhum endere??o.", e.getMessage());
        }
    }

    @Test
    void deveLancarErroAoTentarDeletarUmaPessoaQueNaoPossuiEnderecoPrincipalCadastrado() {
        Endereco endereco = criarEndereco();
        endereco.setIsPrincipal(EnderecoPrincipal.NAO);
        Mockito.when(pessoaRepository.existsById(Mockito.anyLong())).thenReturn(true);
        Mockito.when(enderecoRepository.buscarTodosEnderecosPorIdPessoa(Mockito.anyLong()))
                .thenReturn(Arrays.asList(endereco));
        Mockito.doNothing().when(enderecoRepository).deleteById(Mockito.anyLong());

        try {
            enderecoService.deletarEnderecoPrincipal(1l);
        }catch (Exception e) {
            Assertions.assertEquals(ObjectNotFoundException.class, e.getClass());
            Assertions.assertEquals("Erro ao tentar Deletar. " +
                    "Essa Pessoa n??o possui Endere??o Principal.", e.getMessage());
        }
    }

    @Test
    void deveSalvarUmIdDeUmEnderecoPrincipalNaEntidadePessoa() {
        Mockito.when(mapper.map(Mockito.any(), Mockito.any())).thenReturn(criarPessoaDTO());

        enderecoService.setEnderecoPrincipalPessoa(criarEndereco());

        Mockito.verify(mapper, Mockito.times(1)).map(Mockito.any(), Mockito.any());
        Mockito.verify(pessoaService ,Mockito.times(1))
                .editarPessoa(criarPessoaDTO());
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