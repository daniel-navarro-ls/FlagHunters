package com.example.retosflags.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "retos")
public class Reto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String titulo;
    @Column(nullable = false, length = 1000)
    private String descripcion;
    @Column(nullable = false)
    private String enlace;
    @Column(nullable = false)
    private String flag;
    @Column(name = "user_id")
    private Long userId;
    @OneToMany(mappedBy = "reto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comentario> comentarios=new ArrayList<>();

    public Reto() {}

    public Reto(Long id, String titulo, String descripcion, String enlace, String flag, Long userId) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.enlace = enlace;
        this.flag = flag;
        this.userId = userId;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public String getEnlace() { return enlace; }
    public void setEnlace(String enlace) { this.enlace = enlace; }
    public String getFlag() { return flag; }
    public void setFlag(String flag) { this.flag = flag; }
    public Long getUser() { return userId; }
    public void setAutor(Long id) { this.userId = id; }
}
