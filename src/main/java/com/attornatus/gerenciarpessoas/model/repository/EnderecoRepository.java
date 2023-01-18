package com.attornatus.gerenciarpessoas.model.repository;

import com.attornatus.gerenciarpessoas.model.entity.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
    //List<Endereco> findAllByIdPessoa();
}
