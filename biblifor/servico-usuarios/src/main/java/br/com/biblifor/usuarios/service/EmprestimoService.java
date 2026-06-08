package br.com.biblifor.usuarios.service;

import br.com.biblifor.usuarios.model.Emprestimo;
import br.com.biblifor.usuarios.model.Usuario;
import br.com.biblifor.usuarios.repository.EmprestimoRepository;
import br.com.biblifor.usuarios.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class EmprestimoService {

    private final EmprestimoRepository emprestimoRepo;
    private final UsuarioRepository usuarioRepo;
    private final RestTemplate restTemplate;

    @Value("${livros.service.url}")
    private String livrosServiceUrl;

    public EmprestimoService(EmprestimoRepository emprestimoRepo,
                             UsuarioRepository usuarioRepo,
                             RestTemplate restTemplate) {
        this.emprestimoRepo = emprestimoRepo;
        this.usuarioRepo = usuarioRepo;
        this.restTemplate = restTemplate;
    }

    public List<Emprestimo> listarTodos() { return emprestimoRepo.findAll(); }

    public String realizarEmprestimo(Long livroId, Long usuarioId) {
        Optional<Usuario> optUsuario = usuarioRepo.findById(usuarioId);
        if (optUsuario.isEmpty()) return "Erro: Usuario nao encontrado.";
        Usuario usuario = optUsuario.get();

        Map livro;
        try {
            livro = restTemplate.getForObject(livrosServiceUrl + "/livros/" + livroId, Map.class);
        } catch (Exception e) {
            return "Erro: Servico de livros indisponivel.";
        }

        if (livro == null) return "Erro: Livro nao encontrado.";
        if (!(Boolean) livro.get("disponivel")) return "Erro: Livro ja emprestado.";

        String tituloLivro = (String) livro.get("titulo");

        restTemplate.patchForObject(
            livrosServiceUrl + "/livros/" + livroId + "/disponibilidade",
            Map.of("disponivel", false), String.class
        );

        Emprestimo emprestimo = new Emprestimo(livroId, tituloLivro, usuarioId, usuario.getNome());
        emprestimoRepo.save(emprestimo);

        usuario.adicionarHistorico("Emprestou \"" + tituloLivro + "\" em " + emprestimo.getDataEmprestimo());
        usuarioRepo.save(usuario);

        return "Sucesso! Devolucao prevista para: " + emprestimo.getDataDevolucao();
    }

    public String devolverLivro(Long emprestimoId) {
        Optional<Emprestimo> opt = emprestimoRepo.findById(emprestimoId);
        if (opt.isEmpty()) return "Erro: Emprestimo nao encontrado.";

        Emprestimo emprestimo = opt.get();
        if (!emprestimo.isAtivo()) return "Erro: Este emprestimo ja foi encerrado.";

        try {
            restTemplate.patchForObject(
                livrosServiceUrl + "/livros/" + emprestimo.getLivroId() + "/disponibilidade",
                Map.of("disponivel", true), String.class
            );
        } catch (Exception e) {
            return "Erro: Servico de livros indisponivel.";
        }

        emprestimo.setAtivo(false);
        emprestimoRepo.save(emprestimo);
        return "Sucesso! Livro \"" + emprestimo.getTituloLivro() + "\" devolvido.";
    }
}
