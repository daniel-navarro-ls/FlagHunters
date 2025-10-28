package com.example.retosflags.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.retosflags.model.Comentario;

public interface CommentRepository extends JpaRepository<Comentario, Long> {
    List<Comentario> findByRetoId(Long id);
    List<Comentario> findByUserId(Long id);
}
