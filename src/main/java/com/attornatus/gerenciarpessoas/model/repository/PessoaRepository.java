package com.attornatus.gerenciarpessoas.model.repository;

import com.attornatus.gerenciarpessoas.model.entity.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

}
