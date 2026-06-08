package br.com.biblifor.usuarios.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String email;

    // Historico salvo como texto separado por ponto-e-virgula no banco
    @Column(length = 2000)
    private String historico = "";

    public Usuario() {}

    public Usuario(String nome, String email) {
        this.nome = nome;
        this.email = email;
    }

    public Long getId()              { return id; }
    public void setId(Long id)       { this.id = id; }
    public String getNome()          { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail()         { return email; }
    public void setEmail(String e)   { this.email = e; }

    public List<String> getHistorico() {
        if (historico == null || historico.isEmpty()) return new ArrayList<>();
        List<String> lista = new ArrayList<>();
        for (String s : historico.split(";")) {
            if (!s.isBlank()) lista.add(s.trim());
        }
        return lista;
    }

    public void adicionarHistorico(String registro) {
        if (historico == null) historico = "";
        historico = historico.isEmpty() ? registro : historico + ";" + registro;
    }

    public void setHistorico(String historico) { this.historico = historico; }
}
