package com.example.retosflags.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.retosflags.model.Comentario;
import com.example.retosflags.model.Reto;
import com.example.retosflags.repository.CommentRepository;
import com.example.retosflags.repository.RetoRepository;

@Service
public class RetoService {
    @Autowired
    private RetoRepository retoRepository;

    public void addReto(Reto reto){
        retoRepository.save(reto);
    }

    public void deleteReto(Reto reto){
        retoRepository.delete(reto);
    }

    public void deleteRetoById(Long id){
        retoRepository.deleteById(id);
    }

    public Reto getRetoById(Long id){
        return retoRepository.findById(id).orElse(null);
    }

    public List<Reto> getAllRetos(){
        return retoRepository.findAll();
    }

    public List<Reto> findByAuthorId(Long id){
        return retoRepository.findByUserId(id);
    }
}
