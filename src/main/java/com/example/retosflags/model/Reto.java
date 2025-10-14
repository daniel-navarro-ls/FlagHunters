package com.example.retosflags.model;

public class Reto {
    private Long id;
    private String titulo;
    private String descripcion;
    private String enlace;
    private String flag;
    private String autor;

    public Reto() {}

    public Reto(Long id, String titulo, String descripcion, String enlace, String flag, String autor) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.enlace = enlace;
        this.flag = flag;
        this.autor = autor;
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
    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }
}
