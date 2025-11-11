package com.example.retosflags.controller;

import com.example.retosflags.model.Reto;
import com.example.retosflags.model.User;
import com.example.retosflags.dto.ComentarioDTO;
import com.example.retosflags.dto.RetoDTO;
import com.example.retosflags.dto.UserDTO;
import com.example.retosflags.model.Comentario;
import com.example.retosflags.repository.RetoRepository;
import com.example.retosflags.service.ComentarioService;
import com.example.retosflags.service.RetoService;
import com.example.retosflags.repository.CommentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.*;

import jakarta.servlet.http.HttpSession;


@Controller
public class RetoController {
    private final List<Reto> retos = new ArrayList<>();
    private Long nextId = 1L;
    private final Map<String, Set<Long>> retosResueltosPorUsuario = new HashMap<>();
    
    @Autowired
    private RetoService retoService;
    @Autowired
    private ComentarioService comentarioService;

    @GetMapping("/")
    public String login(Model model, HttpSession session) {
        if (session.getAttribute("user") != null) {
            return "redirect:/home";
        }
        return "login";
    }
    
    @GetMapping("/home")
    public String home(Model model, HttpSession session) {
        Object user=session.getAttribute("user");
        model.addAttribute("retos", retoService.getAllRetos());
        model.addAttribute("user", session.getAttribute("user"));
        model.addAttribute("username", ((User) user).getUsername());
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
        String autor = (u instanceof User) ? ((User) u).getUsername() : "anon";
        RetoDTO reto = retoService.createRetoDTO(null,titulo,descripcion,enlace,flag,(User)u);
        retoService.addReto(reto);;
        return "redirect:/home";
    }

    @GetMapping("/resolver/{id}")
    public String resolverForm(@PathVariable Long id, Model model, HttpSession session) {
        RetoDTO reto = retoService.getRetoById(id);
        Object user=session.getAttribute("user");
        List<ComentarioDTO> comments = comentarioService.findByRetoId(id);
        model.addAttribute("reto", reto);
        model.addAttribute("comments", comments);
        model.addAttribute("user", session.getAttribute("user"));
        model.addAttribute("resultado", null);
        model.addAttribute("username", ((User) user).getUsername());
        return "resolver";
    }

    @PostMapping("/resolver/{id}")
    public String resolverReto(@PathVariable Long id, @RequestParam String flag, Model model, HttpSession session) {
        Object user=session.getAttribute("user");
        RetoDTO reto = retoService.getRetoById(id);
        boolean correcto = reto != null && retoService.getFlag(reto).equals(flag);
        List<ComentarioDTO> comments = comentarioService.findByRetoId(id);
        model.addAttribute("reto", reto);
        model.addAttribute("comments", comments);
        model.addAttribute("user", session.getAttribute("user"));
        model.addAttribute("resultado", correcto ? "¡Flag correcta!" : "Flag incorrecta");
        model.addAttribute("correcto", correcto);
        model.addAttribute("username", ((User) user).getUsername());
        if (correcto) {
            Object u = session.getAttribute("user");
            if (u instanceof User) {
                String username = ((User) u).getUsername();
                retosResueltosPorUsuario.computeIfAbsent(username, k -> new HashSet<>()).add(id);
            }
        }
        return "resolver";
    }

    @GetMapping("/perfil")
    public String perfil(Model model, HttpSession session) {
        Object u = session.getAttribute("user");
        if (u == null) return "redirect:/";
        String username = ((User) u).getUsername();
        List<RetoDTO> propios = retoService.findByAuthorId(((User) u).getId());
        List<RetoDTO> resueltos = new ArrayList<>();
        Set<Long> solvedIds = retosResueltosPorUsuario.getOrDefault(username, Collections.emptySet());
        if (!solvedIds.isEmpty()) {
            for (Long rid : solvedIds) {
                RetoDTO reto=retoService.getRetoById(rid);
                if(reto!=null){
                    resueltos.add(reto);
                }
            }
        }
        List<ComentarioDTO> userComments = comentarioService.findByUserId(((User) u).getId());
        model.addAttribute("user", u);
        model.addAttribute("retosPublicados", propios);
        model.addAttribute("retosResueltos", resueltos);
        model.addAttribute("userComments", userComments);
        return "perfil";
    }

    @PostMapping("/comment/{retoId}")
    public String addComment(@PathVariable Long retoId, @RequestParam String content, HttpSession session) {
        Object user = session.getAttribute("user");
        if (user == null) return "redirect:/";
        comentarioService.addComment(retoId,((User)user).getId(),content);
        return "redirect:/resolver/" + retoId;
    }
}
