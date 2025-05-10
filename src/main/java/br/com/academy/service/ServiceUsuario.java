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
  public void salvarUsuario(Usuario user) throws EmailExistsException, CriptoExistException {
    try {
      // Verifica se e-mail já existe
      if (usuarioRepository.findByEmail(user.getEmail()) != null) {
        throw new EmailExistsException("Este e-mail já está cadastrado no sistema!");
      }

      user.setSenha(Util.md5(user.getSenha()));
      usuarioRepository.save(user);

    } catch (NoSuchAlgorithmException e) {
      throw new CriptoExistException("Erro na criptografia da senha");
    }
  }

  public Usuario loginUser(String username, String senha) throws CriptoExistException, ServiceExc {
    try {
      String senhaCriptografada = Util.md5(senha);
      Usuario userLogin = usuarioRepository.buscaLogin(username, senhaCriptografada);
      if (userLogin == null) {
        throw new ServiceExc("Usuário ou senha incorretos");
      }
      return userLogin;
    } catch (NoSuchAlgorithmException e) {
      throw new CriptoExistException("Erro na criptografia da senha");
    }
  }
}
