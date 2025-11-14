package com.example.retosflags.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.retosflags.dto.ComentarioDTO;
import com.example.retosflags.dto.RetoDTO;
import com.example.retosflags.dto.UserDTO;
import com.example.retosflags.model.User;
import com.example.retosflags.service.ComentarioService;
import com.example.retosflags.service.RetoService;
import com.example.retosflags.service.UserService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/retos")
public class RetoApiController {
    @Autowired
    private RetoService retoService;
    @Autowired
    private ComentarioService comentarioService;
    @Autowired
    private UserService userService;

    @GetMapping("/")//devolver retos que estan en home
    public ResponseEntity<List<RetoDTO>> getRetos() {
        List<RetoDTO> retos=retoService.getAllRetos();
        if(!retos.isEmpty()){
            return ResponseEntity.ok(retos);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } 
    }

    @PostMapping("/crearReto")
    public ResponseEntity<RetoDTO> crearReto(@RequestParam String titulo,
                                             @RequestParam String descripcion,
                                             @RequestParam String enlace,
                                             @RequestParam String flag,
                                             @RequestParam Long userId) {
        
        RetoDTO reto=retoService.createRetoDTO(userId, titulo, descripcion, enlace, flag, userService.getUser(userId));
        retoService.addReto(reto);
        if(reto!=null){
            return new ResponseEntity<>(reto,HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<RetoDTO> getReto(@PathVariable Long id) {
        RetoDTO reto = retoService.getRetoById(id);
        if(reto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(reto,HttpStatus.ACCEPTED);
    }

    // Intentar resolver un reto
    @PostMapping("/resolver/{id}")
    public ResponseEntity<RetoDTO> resolverReto(@PathVariable Long id, 
                                                           @RequestParam String flag,
                                                           @RequestParam Long userId) {
        RetoDTO reto = retoService.getRetoById(id);
        if(reto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        boolean correcto = retoService.getFlag(reto).equals(flag);
        
        // Registrar reto resuelto si es correcto
        if (correcto) {
            userService.retoResuelto(reto,userId);
        }
        
        return new ResponseEntity<>(reto,HttpStatus.ACCEPTED);
    }

    // Obtener perfil de usuario
    @GetMapping("/perfil/{userId}")
    public ResponseEntity<?> getPerfil(@PathVariable Long userId) {
        User user = userService.getUser(userId);
        if(user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        UserDTO devolver=userService.toDTO(user);
        List<RetoDTO> propios = retoService.findByAuthorId(userId);
        List<RetoDTO> resueltos = userService.getRetosDTOResueltos(user);
        List<ComentarioDTO> userComments = comentarioService.findByUserId(userId);
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("usuario", devolver);
        response.put("retosPropios", propios);
        response.put("retosResueltos", resueltos);
        response.put("comentarios", userComments);
        return new ResponseEntity<>(response,HttpStatus.ACCEPTED);
    }

    // Agregar comentario a un reto
    @PostMapping("/comment/{retoId}")
    public ResponseEntity<ComentarioDTO> addComment(@PathVariable Long retoId, 
                                                   @RequestParam String content,
                                                   @RequestParam Long userId) {
        try {
            comentarioService.addComment(retoId, userId, content);
            
            // Obtener el comentario recién creado para devolverlo
            List<ComentarioDTO> comentarios = comentarioService.findByRetoId(retoId);
            ComentarioDTO nuevoComentario = comentarios.stream()
                .filter(c -> c.user().id().equals(userId))
                .reduce((first, second) -> second) // Último comentario
                .orElse(null);
                
            if(nuevoComentario != null) {
                return new ResponseEntity<>(nuevoComentario, HttpStatus.CREATED);
            }
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Obtener comentarios de un reto
    @GetMapping("/{retoId}/comentarios")
    public ResponseEntity<List<ComentarioDTO>> getComentarios(@PathVariable Long retoId) {
        List<ComentarioDTO> comentarios = comentarioService.findByRetoId(retoId);
        return ResponseEntity.ok(comentarios);
    }

    // Obtener retos de un usuario específico
    @GetMapping("/usuario/{userId}")
    public ResponseEntity<List<RetoDTO>> getRetosByUser(@PathVariable Long userId) {
        List<RetoDTO> retos = retoService.findByAuthorId(userId);
        if(!retos.isEmpty()){
            return ResponseEntity.ok(retos);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    // Eliminar un reto
    @DeleteMapping("/{id}")
    public ResponseEntity<RetoDTO> deleteReto(@PathVariable Long id) {
        RetoDTO reto=retoService.getRetoById(id);
        if(reto==null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }else{}
            retoService.deleteReto(reto);
            return new ResponseEntity<>(reto,HttpStatus.ACCEPTED);
        }
}
