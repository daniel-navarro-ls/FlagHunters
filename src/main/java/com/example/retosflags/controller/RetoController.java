package com.example.retosflags.controller;

import com.example.retosflags.model.Reto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@Controller
public class RetoController {
    private final List<Reto> retos = new ArrayList<>();
    private Long nextId = 1L;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("retos", retos);
        return "index";
    }

    @GetMapping("/crear")
    public String crearForm(Model model) {
        model.addAttribute("reto", new Reto());
        return "crear";
    }

    @PostMapping("/crear")
    public String crearReto(@RequestParam String titulo,
                            @RequestParam String descripcion,
                            @RequestParam String enlace,
                            Model model) {
        Reto reto = new Reto(nextId++, titulo, descripcion, enlace, "");
        retos.add(reto);
        return "redirect:/";
    }

    @GetMapping("/resolver/{id}")
    public String resolverForm(@PathVariable Long id, Model model) {
        Reto reto = retos.stream().filter(r -> r.getId().equals(id)).findFirst().orElse(null);
        model.addAttribute("reto", reto);
        model.addAttribute("resultado", null);
        return "resolver";
    }

    @PostMapping("/resolver/{id}")
    public String resolverReto(@PathVariable Long id, @RequestParam String flag, Model model) {
        Reto reto = retos.stream().filter(r -> r.getId().equals(id)).findFirst().orElse(null);
        boolean correcto = reto != null && reto.getFlag().equals(flag);
        model.addAttribute("reto", reto);
        model.addAttribute("resultado", correcto ? "¡Flag correcta!" : "Flag incorrecta");
        model.addAttribute("correcto", correcto);
        return "resolver";
    }
}
