package com.example.retosflags.controller;

import com.example.retosflags.model.Reto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;


@Controller
public class RetoController {
    private final List<Reto> retos = new ArrayList<>();
    private Long nextId = 1L;
    private final Map<String, Set<Long>> retosResueltosPorUsuario = new HashMap<>();

    @GetMapping("/")
    public String login(Model model, HttpSession session) {
        if (session.getAttribute("user") != null) {
            return "redirect:/home";
        }
        return "login";
    }
    
    @GetMapping("/home")
    public String home(Model model, HttpSession session) {
        model.addAttribute("retos", retos);
        model.addAttribute("user", session.getAttribute("user"));
        return "index";
    }

    @GetMapping("/crear")
    public String crearForm(Model model, HttpSession session) {
        if (session.getAttribute("user") == null) return "redirect:/";
        model.addAttribute("reto", new Reto());
        return "crear";
    }

    @PostMapping("/crear") //sanitizar enlace en un futuro
    public String crearReto(@RequestParam String titulo,
                            @RequestParam String descripcion,
                            @RequestParam String enlace,
                            @RequestParam String flag,
                            Model model, HttpSession session) {
        Object u = session.getAttribute("user");
        String autor = (u instanceof com.example.retosflags.model.User) ? ((com.example.retosflags.model.User) u).getUsername() : "anon";
        Reto reto = new Reto(nextId++, titulo, descripcion, enlace, flag, autor);
        retos.add(reto);
        return "redirect:/home";
    }

    @GetMapping("/resolver/{id}")
    public String resolverForm(@PathVariable Long id, Model model, HttpSession session) {
        Reto reto = retos.stream().filter(r -> r.getId().equals(id)).findFirst().orElse(null);
        model.addAttribute("reto", reto);
        model.addAttribute("user", session.getAttribute("user"));
        model.addAttribute("resultado", null);
        return "resolver";
    }

    @PostMapping("/resolver/{id}")
    public String resolverReto(@PathVariable Long id, @RequestParam String flag, Model model, HttpSession session) {
        Reto reto = retos.stream().filter(r -> r.getId().equals(id)).findFirst().orElse(null);
        boolean correcto = reto != null && reto.getFlag().equals(flag);
        model.addAttribute("reto", reto);
        model.addAttribute("user", session.getAttribute("user"));
        model.addAttribute("resultado", correcto ? "¡Flag correcta!" : "Flag incorrecta");
        model.addAttribute("correcto", correcto);
        if (correcto) {
            Object u = session.getAttribute("user");
            if (u instanceof com.example.retosflags.model.User) {
                String username = ((com.example.retosflags.model.User) u).getUsername();
                retosResueltosPorUsuario.computeIfAbsent(username, k -> new HashSet<>()).add(id);
            }
        }
        return "resolver";
    }

    @GetMapping("/perfil")
    public String perfil(Model model, HttpSession session) {
        Object u = session.getAttribute("user");
        if (u == null) return "redirect:/";
        String username = ((com.example.retosflags.model.User) u).getUsername();
        List<Reto> propios = new ArrayList<>();
        List<Reto> resueltos = new ArrayList<>();
        for (Reto r : retos) {
            if (username.equals(r.getAutor())) propios.add(r);
        }
        Set<Long> solvedIds = retosResueltosPorUsuario.getOrDefault(username, Collections.emptySet());
        for (Reto r : retos) {
            if (solvedIds.contains(r.getId())) resueltos.add(r);
        }
        model.addAttribute("user", u);
        model.addAttribute("retosPublicados", propios);
        model.addAttribute("retosResueltos", resueltos);
        return "perfil";
    }
}
