package br.com.biblifor.usuarios.controller;

import br.com.biblifor.usuarios.model.Usuario;
import br.com.biblifor.usuarios.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    // LISTAR TODOS OS USUARIOS
    @GetMapping
    public List<Usuario> listar() {
        return service.listarTodos();
    }

    // BUSCAR USUARIO POR ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscar(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // CADASTRAR NOVO USUARIO
    @PostMapping
    public ResponseEntity<Usuario> cadastrar(@RequestBody Usuario usuario) {
        Usuario salvo = service.cadastrar(usuario);
        return ResponseEntity.ok(salvo);
    }

    // DELETAR USUARIO
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable Long id) {
        boolean deletado = service.deletar(id);
        if (deletado) return ResponseEntity.ok("Usuario removido.");
        return ResponseEntity.notFound().build();
    }
}
