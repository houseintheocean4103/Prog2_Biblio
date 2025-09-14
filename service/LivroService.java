package service;

import core.Livro;
import java.util.List;
import java.util.Optional;

// Interface que define os serviços de manipulação de livros
// Qualquer classe que implementar essa interface deve fornecer essas operações
public interface LivroService {
    // Adiciona um livro ao repositório
    void adicionarLivro(Livro livro);

    // Retorna todos os livros
    List<Livro> getTodosLivros();

    // Busca livro por ID (pode não encontrar → Optional)
    Optional<Livro> buscarPorId(int id);

    // Buscas por atributos específicos
    List<Livro> buscarPorTitulo(String titulo);
    List<Livro> buscarPorAutor(String autor);
    List<Livro> buscarPorGenero(String genero);
    List<Livro> buscarPorEditora(String editora);
    List<Livro> buscarPorLocalizacao(String local);
    List<Livro> buscarPorStatus(boolean ativo);

    // Retorna quantidade total de livros cadastrados
    int getTotalLivros();

    // Atualiza livro pelo ID
    boolean atualizarLivro(int id, Livro livroAtualizado);

    // Remove livro pelo ID
    boolean removerLivro(int id);

    // Armazena os dados em arquivo .CSV
    void salvarDados();

    // Alterar status de um livro
    void ativarLivro(int id);
    void desativarLivro(int id);
}
