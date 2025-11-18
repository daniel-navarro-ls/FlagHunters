package com.example.retosflags.model;

import java.util.ArrayList;
import java.util.List;

import com.example.retosflags.dto.RetoDTO;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comentario> comentarios=new ArrayList<>();

    @ManyToMany
    @JoinTable(
        name = "user_retos_resueltos",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "reto_id")
    )
    private List<Reto> retosResueltos=new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reto> retosSubidos=new ArrayList<>();
    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public List<Comentario> getComentarios(){ return comentarios;}
    public void setComentarios(List<Comentario> comentarios){ this.comentarios=comentarios;}

    public List<Reto> getRetosResueltos() {
        return retosResueltos;
    }

    public void addRetoResuelto(Reto resuelto) {
        if (!retosResueltos.contains(resuelto)){
            retosResueltos.add(resuelto);
        }
    }

    public List<Reto> getRetosSubidos() {
        return retosSubidos;
    }

    public void setRetosSubidos(List<Reto> retos){
        this.retosSubidos=retos;
    }

    public void addRetoSubido(Reto guardar) {
        retosSubidos.add(guardar);
    }    
}
