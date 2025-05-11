package br.com.academy.repository;

import br.com.academy.model.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Long> {

  @Query("select u from Usuario u where u.email = :email")
  public Usuario findByEmail(String email);

  @Query("select u from Usuario u where u.username = :username and u.senha = :senha")
  public Usuario buscaLogin(String username, String senha);

}
