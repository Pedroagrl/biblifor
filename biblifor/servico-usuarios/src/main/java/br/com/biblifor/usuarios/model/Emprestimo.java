package br.com.biblifor.usuarios.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "emprestimos")
public class Emprestimo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long livroId;

    @Column(nullable = false)
    private String tituloLivro;

    @Column(nullable = false)
    private Long usuarioId;

    @Column(nullable = false)
    private String nomeUsuario;

    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucao;
    private boolean ativo;

    public Emprestimo() {}

    public Emprestimo(Long livroId, String tituloLivro, Long usuarioId, String nomeUsuario) {
        this.livroId = livroId;
        this.tituloLivro = tituloLivro;
        this.usuarioId = usuarioId;
        this.nomeUsuario = nomeUsuario;
        this.dataEmprestimo = LocalDate.now();
        this.dataDevolucao = LocalDate.now().plusDays(7);
        this.ativo = true;
    }

    public Long getId()                        { return id; }
    public void setId(Long id)                 { this.id = id; }
    public Long getLivroId()                   { return livroId; }
    public void setLivroId(Long livroId)       { this.livroId = livroId; }
    public String getTituloLivro()             { return tituloLivro; }
    public void setTituloLivro(String t)       { this.tituloLivro = t; }
    public Long getUsuarioId()                 { return usuarioId; }
    public void setUsuarioId(Long u)           { this.usuarioId = u; }
    public String getNomeUsuario()             { return nomeUsuario; }
    public void setNomeUsuario(String n)       { this.nomeUsuario = n; }
    public LocalDate getDataEmprestimo()       { return dataEmprestimo; }
    public LocalDate getDataDevolucao()        { return dataDevolucao; }
    public boolean isAtivo()                   { return ativo; }
    public void setAtivo(boolean ativo)        { this.ativo = ativo; }
}
