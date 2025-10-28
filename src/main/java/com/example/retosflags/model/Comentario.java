package com.example.retosflags.model;
import jakarta.persistence.*;

@Entity
@Table(name = "comentarios")
public class Comentario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 1000)
    private String comment;
    @ManyToOne
    @JoinColumn(name="users_id",nullable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name = "reto_id", nullable = false)
    private Reto reto;

    public Comentario(){
    }
    public Comentario(String comentario, User user, Reto reto){
        this.comment=comentario;
        this.user=user;
        this.reto=reto;
    }
    public Long getId(){
        return id;
    }
    public String getComment(){
        return comment;
    }
    public void setComment(String comment){
        this.comment=comment;
    }
    public User getUser(){
        return user;
    }
    public void setUser(User user){
        this.user=user;
    }
    public Reto getReto(){
        return reto;
    }
    public void setReto(Reto reto){
        this.reto=reto;
    }
}
