package com.example.retosflags.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.retosflags.dto.RetoDTO;
import com.example.retosflags.mapper.RetoMapper;
import com.example.retosflags.model.Comentario;
import com.example.retosflags.model.Reto;
import com.example.retosflags.model.User;
import com.example.retosflags.repository.CommentRepository;
import com.example.retosflags.repository.RetoRepository;

@Service
public class RetoService {
    @Autowired
    private RetoRepository retoRepository;
    @Autowired
    private RetoMapper retoMapper;

    public void addReto(RetoDTO reto){
        Reto guardar=retoMapper.toDomain(reto);
        retoRepository.save(guardar);
    }

    public void deleteReto(RetoDTO reto){
        Reto borrar=retoMapper.toDomain(reto);
        retoRepository.delete(borrar);
    }

    public void deleteRetoById(Long id){
        retoRepository.deleteById(id);
    }

    public RetoDTO getRetoById(Long id){
        Reto reto=retoRepository.findById(id).orElse(null);
        if(reto!=null){
            return retoMapper.toDTO(reto);
        }
        return null;
    }

    public List<RetoDTO> getAllRetos(){
        Collection<Reto> retos=retoRepository.findAll();
        return retoMapper.toDTOs(retos);
    }

    public List<RetoDTO> findByAuthorId(Long id){
        Collection<Reto> retosAutor=retoRepository.findByUserId(id);
        return retoMapper.toDTOs(retosAutor);
    }

    public String getFlag(RetoDTO reto) {
        Long id=reto.id();
        Reto r=retoRepository.findById(id).orElse(null);
        if(r!=null){
            return r.getFlag();
        }else{
            return null;
        }
    }

    public RetoDTO createRetoDTO(Long id, String titulo, String descripcion, String enlace, String flag, User u) {
        Reto reto=new Reto(id,titulo,descripcion,enlace,flag,u);
        return retoMapper.toDTO(reto);
    }
}
