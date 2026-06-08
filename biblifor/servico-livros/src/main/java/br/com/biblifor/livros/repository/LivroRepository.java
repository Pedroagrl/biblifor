package br.com.biblifor.livros.repository;

import br.com.biblifor.livros.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {
    // Spring Data JPA gera automaticamente: findAll, findById, save, deleteById, etc.
}
