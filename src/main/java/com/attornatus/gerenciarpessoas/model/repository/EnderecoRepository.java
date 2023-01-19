package com.attornatus.gerenciarpessoas.model.repository;

import com.attornatus.gerenciarpessoas.model.entity.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

    @Query(value = "select e from Endereco e " +
            "JOIN  Pessoa p on e.pessoa.id = p.id  and p.id = :id")
    List<Endereco> buscarTodosEnderecosPorIdPessoa(@Param("id") Long id);
}
