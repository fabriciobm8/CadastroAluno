package br.com.academy.repository;

import br.com.academy.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlunoRepository extends JpaRepository<Aluno,Integer> {



}
