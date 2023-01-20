package com.attornatus.gerenciarpessoas.service.impl;

import com.attornatus.gerenciarpessoas.api.dto.PessoaDTO;
import com.attornatus.gerenciarpessoas.model.entity.Pessoa;
import com.attornatus.gerenciarpessoas.model.repository.PessoaRepository;
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

import java.util.List;
import java.util.Optional;

@SpringBootTest
class PessoaServiceImplTest {

    @InjectMocks
    private PessoaServiceImpl pessoaService;

    @Mock
    private PessoaRepository pessoaRepository;

    @Mock
    private ModelMapper mapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        criarPessoa();
        criarPessoaDTO();
    }

    @Test
    void deveCriarERetornarPessoa() {
        PessoaDTO pessoaDTO = criarPessoaDTO();
        pessoaDTO.setId(null);
        Pessoa pessoa = criarPessoa();
        Mockito.when(mapper.map(Mockito.any(), Mockito.any())).thenReturn(pessoa);
        Mockito.when(pessoaRepository.save(pessoa)).thenReturn(pessoa);

        Pessoa response = pessoaService.criarPessoa(pessoaDTO);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(Pessoa.class, response.getClass());
        Assertions.assertEquals(pessoa.getId(), response.getId());
        Assertions.assertEquals(pessoa.getNome(), response.getNome());
        Assertions.assertEquals(pessoa.getDataNascimento(), response.getDataNascimento());
        Assertions.assertEquals(pessoa.getEnderecoPrincipal(), response.getEnderecoPrincipal());
    }
    @Test
    void deveRetornarErroAoTentarCriarPessoa() {
        PessoaDTO pessoaDTO = criarPessoaDTO();
        Pessoa pessoa = criarPessoa();
        Mockito.when(mapper.map(Mockito.any(), Mockito.any())).thenReturn(pessoa);
        Mockito.when(pessoaRepository.save(pessoa)).thenThrow(
                new DataIntegratyViolationException("Para salvar um usuário nao precisa informar id."));
        try {
            Pessoa response = pessoaService.criarPessoa(pessoaDTO);
        }catch (Exception e) {
            Assertions.assertEquals(DataIntegratyViolationException.class, e.getClass());
            Assertions.assertEquals("Para salvar um usuário nao precisa informar id.", e.getMessage());
        }
    }

    @Test
    void deveEditarERetornarPessoa() {
        PessoaDTO pessoaDTO = criarPessoaDTO();
        Pessoa pessoa = criarPessoa();
        
        Mockito.when(pessoaRepository.existsById(Mockito.any())).thenReturn(true);
        Mockito.when(mapper.map(Mockito.any(), Mockito.any())).thenReturn(pessoa);
        Mockito.when(pessoaRepository.save(pessoa)).thenReturn(pessoa);

        Pessoa response = pessoaService.editarPessoa(pessoaDTO);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(Pessoa.class, response.getClass());
        Assertions.assertEquals(pessoa.getId(), response.getId());
        Assertions.assertEquals(pessoa.getNome(), response.getNome());
        Assertions.assertEquals(pessoa.getDataNascimento(), response.getDataNascimento());
        Assertions.assertEquals(pessoa.getEnderecoPrincipal(), response.getEnderecoPrincipal());
    }

    @Test
    void deveLancarErroAoTentarEditarPessoa() {
        PessoaDTO pessoaDTO = criarPessoaDTO();
        Pessoa pessoa = criarPessoa();

        Mockito.when(pessoaRepository.existsById(Mockito.any())).thenReturn(false);
        Mockito.when(mapper.map(Mockito.any(), Mockito.any())).thenReturn(pessoa);
        Mockito.when(pessoaRepository.save(pessoa)).thenReturn(pessoa);
        try {
            Pessoa response = pessoaService.editarPessoa(pessoaDTO);
        }catch (Exception e) {
            Assertions.assertEquals(ObjectNotFoundException.class, e.getClass());
            Assertions.assertEquals("Pessoa não encontrada para o Id informado.", e.getMessage());
        }
    }

    @Test
    void deveConsultarUmaPessoa() {
        Pessoa pessoa = criarPessoa();
        Mockito.when(pessoaRepository.existsById(Mockito.anyLong())).thenReturn(true);
        Mockito.when(pessoaRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(pessoa));

        Optional<Pessoa> response = pessoaService.consultarPessoa(1l);

        Assertions.assertNotNull(response.get());
        Assertions.assertEquals(Optional.class, response.getClass());
        Assertions.assertEquals(pessoa.getId(), response.get().getId());
        Assertions.assertEquals(pessoa.getNome(), response.get().getNome());
        Assertions.assertEquals(pessoa.getDataNascimento(), response.get().getDataNascimento());
        Assertions.assertEquals(pessoa.getEnderecoPrincipal(), response.get().getEnderecoPrincipal());
    }
    @Test
    void deveRetornarErroAoTentarConsultarUmaPessoa() {
        Pessoa pessoa = criarPessoa();
        Mockito.when(pessoaRepository.existsById(Mockito.anyLong())).thenReturn(false);
        Mockito.when(pessoaRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(pessoa));

        try {
            Optional<Pessoa> response = pessoaService.consultarPessoa(1l);
        }catch (Exception e) {
            Assertions.assertEquals(ObjectNotFoundException.class, e.getClass());
            Assertions.assertEquals("Impossível consultar Pessoa. Informe um Id válido.", e.getMessage());
        }
    }

    @Test
    void deveListarPessoas() {
        Pessoa pessoa = criarPessoa();
        Mockito.when(pessoaRepository.findAll()).thenReturn(List.of(pessoa));

        List<Pessoa> response = pessoaService.listarPessoas();

        Assertions.assertNotNull(response);
        Assertions.assertEquals(Pessoa.class, response.get(0).getClass());
        Assertions.assertEquals(pessoa, response.get(0));
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