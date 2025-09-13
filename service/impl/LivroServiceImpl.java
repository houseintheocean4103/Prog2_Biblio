package service.impl;

import core.Livro;
import repository.BaseDeLivros;
import service.LivroService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LivroServiceImpl implements LivroService {
    private final BaseDeLivros baseDeLivros;
    private final List<Livro> livros;

    public LivroServiceImpl() {
        this.baseDeLivros = BaseDeLivros.getInstance();
        this.livros = baseDeLivros.getLivros();
    }

    @Override
    public void adicionarLivro(Livro livro) {
        // Generate a new unique ID
        int novoId = livros.stream()
                .mapToInt(Livro::getId)
                .max()
                .orElse(0) + 1;

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

        livros.add(novoLivro);
    }

    @Override
    public List<Livro> getTodosLivros() {
        return new ArrayList<>(livros);
    }

    @Override
    public Optional<Livro> buscarPorId(int id) {
        return livros.stream()
                .filter(livro -> livro.getId() == id)
                .findFirst();
    }

    @Override
    public List<Livro> buscarPorTitulo(String titulo) {
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
                        id
                );
                livros.set(i, livroModificado);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean removerLivro(int id) {
        return livros.removeIf(livro -> livro.getId() == id);
    }

    @Override
    public void salvarDados() {
        baseDeLivros.salvarDados();
    }

    @Override
    public void ativarLivro(int id) {
        buscarPorId(id).ifPresent(Livro::ativar);
    }

    @Override
    public void desativarLivro(int id) {
        buscarPorId(id).ifPresent(Livro::desativar);
    }
}