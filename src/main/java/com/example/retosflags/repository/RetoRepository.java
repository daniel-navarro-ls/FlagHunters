package com.example.retosflags.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.retosflags.model.Reto;

public interface RetoRepository extends JpaRepository<Reto, Long> {
    List<Reto> findByAutor(String autor);
}


