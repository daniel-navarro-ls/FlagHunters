package com.example.retosflags.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import com.example.retosflags.repository.RetoRepository;
import com.example.retosflags.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.retosflags.dto.ComentarioDTO;
import com.example.retosflags.dto.RetoDTO;
import com.example.retosflags.mapper.ComentarioMapper;
import com.example.retosflags.mapper.RetoMapper;
import com.example.retosflags.model.Comentario;
import com.example.retosflags.model.Reto;
import com.example.retosflags.model.User;
import com.example.retosflags.repository.CommentRepository;

@Service
public class ComentarioService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private RetoRepository retoRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ComentarioMapper comentarioMapper;
    @Autowired
    private RetoMapper retoMapper;
    @Autowired
    private SanitizationService sanitizationService;

    private void sanitizeComment(Comentario comment){
        sanitizationService.sanitize(comment.getComment());
    }
    public void addComment(Long retoId, Long userId, String comentario){
        sanitizationService.sanitize(comentario);
        Optional<Reto> reto=retoRepository.findById(retoId);
        Optional<User> user=userRepository.findById(userId);
        if(reto.isPresent()&&user.isPresent()){
            Comentario guardar=new Comentario(comentario, user.get(), reto.get());
            commentRepository.save(guardar);
        }
    }

    public void deleteComment(ComentarioDTO comentario){
        Comentario borrar=comentarioMapper.toDomain(comentario);
        commentRepository.delete(borrar);
    }

    public void deleteCommentById(Long id){
        commentRepository.deleteById(id);
    }

    public ComentarioDTO getCommentById(Long id){
        Comentario comentario=commentRepository.findById(id).orElse(null);
        if(comentario!=null){
            return comentarioMapper.toDTO(comentario);
        }
        return null;
    }

    public List<ComentarioDTO> findByRetoId(Long id){
        Collection<Comentario>comentarios=commentRepository.findByRetoId(id);
        return comentarioMapper.toDTOs(comentarios);
    }

    public List<ComentarioDTO> findByUserId(Long id) {
        Collection<Comentario>comentarios=commentRepository.findByUserId(id);
        return comentarioMapper.toDTOs(comentarios);
    }

    public ComentarioDTO createCommentDTO(String content, User user, RetoDTO reto) {
        sanitizationService.sanitize(content);
        Comentario comentario=new Comentario(content, user, retoMapper.toDomain(reto));
        return comentarioMapper.toDTO(comentario);
    }
    
}
