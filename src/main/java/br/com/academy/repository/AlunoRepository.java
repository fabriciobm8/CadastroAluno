package br.com.academy.repository;

import br.com.academy.model.Aluno;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AlunoRepository extends JpaRepository<Aluno,Integer> {

  @Query("select a from Aluno a where a.status = 'ATIVO' ")
  public List<Aluno> findByStatusAtivo();

  @Query("select a from Aluno a where a.status = 'INATIVO' ")
  public List<Aluno> findByStatusInativo();

  @Query("select a from Aluno a where a.status = 'CANCELADO' ")
  public List<Aluno> findByStatusCancelado();

  @Query("select a from Aluno a where a.status = 'TRANCADO' ")
  public List<Aluno> findByStatusTrancado();

  public List<Aluno> findByNomeContainingIgnoreCase(String nome);


}
