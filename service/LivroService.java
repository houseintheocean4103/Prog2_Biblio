package service;

import core.Livro;
import java.util.List;
import java.util.Optional;

public interface LivroService {
    void adicionarLivro(Livro livro);

    List<Livro> getTodosLivros();
    Optional<Livro> buscarPorId(int id);
    List<Livro> buscarPorTitulo(String titulo);
    List<Livro> buscarPorAutor(String autor);
    List<Livro> buscarPorGenero(String genero);
    List<Livro> buscarPorEditora(String editora);
    List<Livro> buscarPorLocalizacao(String local);
    List<Livro> buscarPorStatus(boolean ativo);
    int getTotalLivros();

    boolean atualizarLivro(int id, Livro livroAtualizado);

    boolean removerLivro(int id);

    void salvarDados();

    void ativarLivro(int id);
    void desativarLivro(int id);
}