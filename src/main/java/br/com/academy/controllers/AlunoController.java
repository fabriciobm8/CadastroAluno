package br.com.academy.controllers;

import br.com.academy.model.Aluno;
import br.com.academy.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AlunoController {
  @Autowired
  private AlunoRepository alunoRepository;

  @GetMapping("/inserirAlunos")
  public ModelAndView insertAlunos(Aluno aluno) {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("aluno/formAluno");
    mv.addObject("aluno",new Aluno());
    return mv;
  }

  @PostMapping("insertAluno")
  public ModelAndView saveAluno(Aluno aluno) {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("redirect:/alunos-adicionados");
    alunoRepository.save(aluno);
    return mv;
  }

  @GetMapping("alunos-adicionados")
  public ModelAndView listagemAlunos() {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("Aluno/listAlunos");
    mv.addObject("alunosList",alunoRepository.findAll());
    return mv;
  }

}
