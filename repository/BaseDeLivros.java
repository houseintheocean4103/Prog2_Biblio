package repository;

import core.Livro;

import java.util.ArrayList;
import java.util.List;

// Classe responsável por centralizar a lista de livros carregados
// Implementa o padrão Singleton: só pode existir 1 instância no programa
public final class BaseDeLivros {
    // Instância única da classe
    private static BaseDeLivros instance;

    // Lista interna de livros (mantida em memória)
    private final List<Livro> livros;

    // Construtor privado: impede criação fora da própria classe
    private BaseDeLivros() {
        // Inicializa a lista de livros lendo do CSV
        this.livros = new ArrayList<>(CSVParser.parseBooks());
    }

    // Retorna a instância única (cria uma, se ainda não existir)
    public static BaseDeLivros getInstance() {
        if (instance == null) {
            instance = new BaseDeLivros();
        }
        return instance;
    }

    // Retorna a lista de livros
    public List<Livro> getLivros() {
        return livros; 
    }

    // Persiste os dados atuais de 'livros' no arquivo CSV
    public void salvarDados() {
        CSVParser.salvarLivros(livros);
    }
}
