package br.com.biblifor.livros.service;

import br.com.biblifor.livros.model.Livro;
import br.com.biblifor.livros.repository.LivroRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LivroService {

    private final LivroRepository repository;

    public LivroService(LivroRepository repository) {
        this.repository = repository;
    }

    public List<Livro> listarTodos() {
        return repository.findAll();
    }

    public Optional<Livro> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public Livro cadastrar(Livro livro) {
        livro.setDisponivel(true);
        return repository.save(livro);
    }

    public boolean atualizarDisponibilidade(Long id, boolean disponivel) {
        Optional<Livro> opt = repository.findById(id);
        if (opt.isPresent()) {
            Livro livro = opt.get();
            livro.setDisponivel(disponivel);
            repository.save(livro);
            return true;
        }
        return false;
    }

    public boolean deletar(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
