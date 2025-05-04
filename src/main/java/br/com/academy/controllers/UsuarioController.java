package br.com.academy.controllers;

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
    mv.addObject("usuario", new Usuario());
    mv.setViewName("login/login");
    return mv;
  }

  @GetMapping("/index")
  public ModelAndView index() {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("home/index");
    mv.addObject("aluno",new Aluno());
    return mv;
  }

  @GetMapping("/cadastro")
  public ModelAndView cadastrar() {
    ModelAndView mv = new ModelAndView();
    mv.addObject("usuario", new Usuario());
    mv.setViewName("login/cadastro");
    return mv;
  }

  @PostMapping("salvarUsuario")
  public ModelAndView cadastrar(Usuario usuario) throws Exception {
    ModelAndView mv = new ModelAndView();
    serviceUsuario.salvarUsuario(usuario);
    mv.setViewName("redirect:/");
    return mv;
  }

  @PostMapping("/login")
  public ModelAndView login(@Valid Usuario usuario, BindingResult br, HttpSession session) throws NoSuchAlgorithmException, ServiceExc {
    ModelAndView mv = new ModelAndView();
    mv.addObject("usuario", new Usuario());
    if (br.hasErrors()) {
      mv.setViewName("login/login");
    }
    Usuario userLogin = serviceUsuario.loginUser(usuario.getUsername(), Util.md5(usuario.getSenha()));
    if(userLogin ==null) {
      mv.addObject("msg", "Usuario Não Encontrado. Tente novamente");
    } else {
      session.setAttribute("usuarioLogado", userLogin);
      return index();
    }
    return mv;
  }

  @PostMapping("/logout")
  public ModelAndView logout(HttpSession session) {
    session.invalidate();
    return login();
  }

}
