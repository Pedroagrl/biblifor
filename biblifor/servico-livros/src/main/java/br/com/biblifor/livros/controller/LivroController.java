package br.com.biblifor.livros.controller;

import br.com.biblifor.livros.model.Livro;
import br.com.biblifor.livros.service.LivroService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/livros")
@CrossOrigin(origins = "*") // permite a interface web consumir a API
public class LivroController {

    private final LivroService service;

    public LivroController(LivroService service) {
        this.service = service;
    }

    // LISTAR TODOS OS LIVROS
    @GetMapping
    public List<Livro> listar() {
        return service.listarTodos();
    }

    // BUSCAR LIVRO POR ID
    @GetMapping("/{id}")
    public ResponseEntity<Livro> buscar(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // CADASTRAR NOVO LIVRO
    @PostMapping
    public ResponseEntity<Livro> cadastrar(@RequestBody Livro livro) {
        Livro salvo = service.cadastrar(livro);
        return ResponseEntity.ok(salvo);
    }

    // ATUALIZAR DISPONIBILIDADE (usado pelo servico de emprestimos)
    @PatchMapping("/{id}/disponibilidade")
    public ResponseEntity<String> atualizarDisponibilidade(
            @PathVariable Long id,
            @RequestBody Map<String, Boolean> body) {
        boolean disponivel = body.get("disponivel");
        boolean atualizado = service.atualizarDisponibilidade(id, disponivel);
        if (atualizado) return ResponseEntity.ok("Disponibilidade atualizada.");
        return ResponseEntity.notFound().build();
    }

    // DELETAR LIVRO
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable Long id) {
        boolean deletado = service.deletar(id);
        if (deletado) return ResponseEntity.ok("Livro removido.");
        return ResponseEntity.notFound().build();
    }
}
