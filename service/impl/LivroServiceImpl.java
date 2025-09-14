package service.impl;

import core.Livro;
import repository.BaseDeLivros;
import service.LivroService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Implementação concreta da interface LivroService
// Contém a lógica de negócio para manipular livros
public class LivroServiceImpl implements LivroService {
    private final BaseDeLivros baseDeLivros; // Singleton que armazena os livros
    private final List<Livro> livros;        // Lista real de livros

    public LivroServiceImpl() {
        this.baseDeLivros = BaseDeLivros.getInstance(); // Pega instância única
        this.livros = baseDeLivros.getLivros();         // Acessa lista de livros
    }

    @Override
    public void adicionarLivro(Livro livro) {
        // Gera novo ID único, baseado no maior já existente
        int novoId = livros.stream()
                .mapToInt(Livro::getId)
                .max()
                .orElse(0) + 1;

        // Cria um novo livro com o ID gerado
        Livro novoLivro = new Livro(
                livro.getTitulo(),
                livro.getIsbn(),
                livro.getAutor(),
                livro.getAno(),
                livro.getEditora(),
                livro.getGenero(),
                livro.getSinopse(),
                livro.getLocal(),
                novoId
        );

        livros.add(novoLivro); // Adiciona à lista
    }

    // Retorna a lista
    @Override
    public List<Livro> getTodosLivros() {
        return livros;
    }

    // Busca livro pelo ID usando Stream API, repetindo-se a lógica para os outros critérios
    @Override
    public Optional<Livro> buscarPorId(int id) {   
        return livros.stream()
                .filter(livro -> livro.getId() == id)
                .findFirst();
    }

    @Override
    public List<Livro> buscarPorTitulo(String titulo) {
        // Busca parcial (ignora maiúsculas/minúsculas)
        return livros.stream()
                .filter(livro -> livro.getTitulo().toLowerCase().contains(titulo.toLowerCase()))
                .toList();
    }

    @Override
    public List<Livro> buscarPorAutor(String autor) {
        return livros.stream()
                .filter(livro -> livro.getAutor().toLowerCase().contains(autor.toLowerCase()))
                .toList();
    }

    @Override
    public List<Livro> buscarPorGenero(String genero) {
        return livros.stream()
                .filter(livro -> livro.getGenero().equalsIgnoreCase(genero))
                .toList();
    }

    @Override
    public List<Livro> buscarPorEditora(String editora) {
        return livros.stream()
                .filter(livro -> livro.getEditora().equalsIgnoreCase(editora))
                .toList();
    }

    @Override
    public List<Livro> buscarPorLocalizacao(String local) {
        return livros.stream()
                .filter(livro -> livro.getLocal().equalsIgnoreCase(local))
                .toList();
    }

    @Override
    public List<Livro> buscarPorStatus(boolean ativo) {
        // Retorna todos os livros ativos ou inativos
        return livros.stream()
                .filter(livro -> livro.isAtivo() == ativo)
                .toList();
    }

    @Override
    public int getTotalLivros() {
        return livros.size();
    }

    @Override
    public boolean atualizarLivro(int id, Livro livroAtualizado) {
        // Substitui livro existente por outro com novos dados
        for (int i = 0; i < livros.size(); i++) {
            if (livros.get(i).getId() == id) {
                Livro livroModificado = new Livro(
                        livroAtualizado.getTitulo(),
                        livroAtualizado.getIsbn(),
                        livroAtualizado.getAutor(),
                        livroAtualizado.getAno(),
                        livroAtualizado.getEditora(),
                        livroAtualizado.getGenero(),
                        livroAtualizado.getSinopse(),
                        livroAtualizado.getLocal(),
                        id // mantém o mesmo ID
                );
                livros.set(i, livroModificado); // substitui na lista
                return true;
            }
        }
        return false; // se não encontrou, retorna falso
    }

    @Override
    public boolean removerLivro(int id) {
        // Remove da lista
        return livros.removeIf(livro -> livro.getId() == id);
    }

    @Override
    public void salvarDados() {
        // Atualiza os dados chamando o repositório
        baseDeLivros.salvarDados();
    }

    @Override
    public void ativarLivro(int id) {
        // Ativa o livro encontrado
        buscarPorId(id).ifPresent(Livro::ativar);
    }

    @Override
    public void desativarLivro(int id) {
        // Desativa o livro encontrado
        buscarPorId(id).ifPresent(Livro::desativar);
    }
}
