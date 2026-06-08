package br.com.biblifor.usuarios.service;

import br.com.biblifor.usuarios.model.Usuario;
import br.com.biblifor.usuarios.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public List<Usuario> listarTodos()          { return repository.findAll(); }
    public Optional<Usuario> buscarPorId(Long id) { return repository.findById(id); }
    public Usuario cadastrar(Usuario usuario)    { return repository.save(usuario); }

    public boolean deletar(Long id) {
        if (repository.existsById(id)) { repository.deleteById(id); return true; }
        return false;
    }
}
