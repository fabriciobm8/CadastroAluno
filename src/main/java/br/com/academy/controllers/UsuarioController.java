package br.com.academy.controllers;

import br.com.academy.model.Usuario;
import br.com.academy.repository.UsuarioRepository;
import br.com.academy.service.ServiceUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
    mv.setViewName("login/login");
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

}
