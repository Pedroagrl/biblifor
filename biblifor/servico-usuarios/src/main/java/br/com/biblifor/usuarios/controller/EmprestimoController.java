package br.com.biblifor.usuarios.controller;

import br.com.biblifor.usuarios.model.Emprestimo;
import br.com.biblifor.usuarios.service.EmprestimoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/emprestimos")
@CrossOrigin(origins = "*")
public class EmprestimoController {

    private final EmprestimoService service;

    public EmprestimoController(EmprestimoService service) {
        this.service = service;
    }

    // LISTAR TODOS OS EMPRESTIMOS
    @GetMapping
    public List<Emprestimo> listar() {
        return service.listarTodos();
    }

    // REALIZAR EMPRESTIMO (consome o servico-livros internamente)
    @PostMapping
    public ResponseEntity<String> realizar(@RequestBody Map<String, Long> body) {
        Long livroId = body.get("livroId");
        Long usuarioId = body.get("usuarioId");
        String resultado = service.realizarEmprestimo(livroId, usuarioId);
        if (resultado.startsWith("Sucesso")) return ResponseEntity.ok(resultado);
        return ResponseEntity.badRequest().body(resultado);
    }

    // DEVOLVER LIVRO (consome o servico-livros internamente)
    @PatchMapping("/{id}/devolver")
    public ResponseEntity<String> devolver(@PathVariable Long id) {
        String resultado = service.devolverLivro(id);
        if (resultado.startsWith("Sucesso")) return ResponseEntity.ok(resultado);
        return ResponseEntity.badRequest().body(resultado);
    }
}
