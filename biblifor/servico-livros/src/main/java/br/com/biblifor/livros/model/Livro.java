package br.com.biblifor.livros.model;

import jakarta.persistence.*;

@Entity
@Table(name = "livros")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String autor;

    @Column(nullable = false)
    private boolean disponivel = true;

    public Livro() {}

    public Livro(String titulo, String autor) {
        this.titulo = titulo;
        this.autor = autor;
        this.disponivel = true;
    }

    public Long getId()                  { return id; }
    public void setId(Long id)           { this.id = id; }
    public String getTitulo()            { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getAutor()             { return autor; }
    public void setAutor(String autor)   { this.autor = autor; }
    public boolean isDisponivel()        { return disponivel; }
    public void setDisponivel(boolean d) { this.disponivel = d; }
}
