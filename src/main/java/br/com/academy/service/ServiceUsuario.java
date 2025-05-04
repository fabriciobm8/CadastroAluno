package br.com.academy.service;

import br.com.academy.exceptions.CriptoExistException;
import br.com.academy.exceptions.EmailExistsException;
import br.com.academy.exceptions.ServiceExc;
import br.com.academy.model.Usuario;
import br.com.academy.repository.UsuarioRepository;
import br.com.academy.util.Util;
import java.security.NoSuchAlgorithmException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceUsuario {
  @Autowired
  private UsuarioRepository usuarioRepository;
  public void salvarUsuario(Usuario user) throws Exception {

    try {
      if (usuarioRepository.findByEmail(user.getEmail()) != null) {
        throw new EmailExistsException("Já Existe um email cadastrado para: " + user.getEmail());
      }
      user.setSenha(Util.md5(user.getSenha()));

    } catch (NoSuchAlgorithmException e) {
      throw new CriptoExistException("Erro na criptografia da senha");
    }
    usuarioRepository.save(user);
  }

  public Usuario loginUser(String username, String senha) throws ServiceExc {
    try {
      if (username == null || username.trim().isEmpty()) {
        throw new ServiceExc("Nome de usuário não pode estar vazio");
      }

      if (senha == null || senha.trim().isEmpty()) {
        throw new ServiceExc("Senha não pode estar vazia");
      }

      Usuario userLogin = usuarioRepository.buscaLogin(username, senha);
      return userLogin; // Pode ser null se não encontrar
    } catch (Exception e) {
      if (e instanceof ServiceExc) {
        throw e;
      }
      throw new ServiceExc("Erro ao fazer login: " + e.getMessage());
    }
  }

}
