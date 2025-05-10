package br.com.academy.controllers;

import br.com.academy.dto.LoginDTO;
import br.com.academy.exceptions.CriptoExistException;
import br.com.academy.exceptions.EmailExistsException;
import br.com.academy.exceptions.ServiceExc;
import br.com.academy.model.Aluno;
import br.com.academy.model.Usuario;
import br.com.academy.repository.UsuarioRepository;
import br.com.academy.service.ServiceUsuario;
import br.com.academy.util.Util;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.security.NoSuchAlgorithmException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UsuarioController {

  @Autowired
  private UsuarioRepository usuarioRepository;
  @Autowired
  private ServiceUsuario serviceUsuario;


  @GetMapping("/")
  public ModelAndView login() {
    ModelAndView mv = new ModelAndView();
    mv.addObject("loginDTO", new LoginDTO());  // Alterado para LoginDTO
    mv.setViewName("login/login");
    return mv;
  }

  @GetMapping("/index")
  public ModelAndView index() {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("home/index");
    mv.addObject("aluno", new Aluno());
    return mv;
  }

  @GetMapping("/cadastro")
  public ModelAndView cadastrar() {
    ModelAndView mv = new ModelAndView();
    mv.addObject("usuario", new Usuario());
    mv.setViewName("login/cadastro");
    return mv;
  }

  @PostMapping("/salvarUsuario")
  public ModelAndView cadastrar(@Valid Usuario usuario, BindingResult br, HttpSession session) {
    ModelAndView mv = new ModelAndView();

    if (br.hasErrors()) {
      mv.setViewName("login/cadastro");  // Corrigido para apontar para o template correto
      mv.addObject("usuario", usuario);
      return mv;
    }

    try {
      serviceUsuario.salvarUsuario(usuario);
      mv.addObject("mensagemSucesso", "Usuário cadastrado com sucesso!");
      mv.setViewName("redirect:/");  // Redireciona para login após sucesso
    } catch (EmailExistsException e) {
      mv.setViewName("login/cadastro");  // Volta para cadastro em caso de erro
      mv.addObject("usuario", usuario);
      mv.addObject("mensagemErro", e.getMessage());
    } catch (Exception e) {
      mv.setViewName("login/cadastro");
      mv.addObject("usuario", usuario);
      mv.addObject("mensagemErro", "Erro ao cadastrar usuário");
    }

    return mv;
  }

  @PostMapping("/login")
  public ModelAndView login(@Valid LoginDTO loginDTO, BindingResult br, HttpSession session) {
    ModelAndView mv = new ModelAndView("login/login");

    if (br.hasErrors()) {
      String errorMessage = br.getFieldError().getDefaultMessage();
      mv.addObject("msg", errorMessage);
      return mv;
    }

    try {
      Usuario user = serviceUsuario.loginUser(loginDTO.getUsername(), loginDTO.getSenha());
      session.setAttribute("usuarioLogado", user);
      return new ModelAndView("redirect:/index");
    } catch (ServiceExc e) {
      mv.addObject("msg", e.getMessage());
    } catch (CriptoExistException e) {
      mv.addObject("msg", e.getMessage()); // Use a mensagem da exceção
    }

    return mv;
  }

  @PostMapping("/logout")
  public ModelAndView logout(HttpSession session) {
    session.invalidate();
    return login();
  }

}