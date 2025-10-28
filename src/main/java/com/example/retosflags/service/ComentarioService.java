package com.example.retosflags.service;

import java.util.List;
import java.util.Optional;
import com.example.retosflags.repository.RetoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.retosflags.model.Comentario;
import com.example.retosflags.repository.CommentRepository;

@Service
public class ComentarioService {

    @Autowired
    private CommentRepository commentRepository;

    public void addComment(Comentario comentario){
        commentRepository.save(comentario);
    }

    public void deleteComment(Comentario comentario){
        commentRepository.delete(comentario);
    }

    public void deleteCommentById(Long id){
        commentRepository.deleteById(id);
    }

    public Comentario getCommentById(Long id){
        return commentRepository.findById(id).orElse(null);
    }

    public List<Comentario> findByRetoId(Long id){
        return commentRepository.findByRetoId(id);
    }

    public List<Comentario> findByUserId(Long id) {
        return commentRepository.findByUserId(id);
    }
    
}
