package repository;

import core.Livro;

import java.util.ArrayList;
import java.util.List;

public final class BaseDeLivros {
    private static BaseDeLivros instance;
    private final List<Livro> livros;

    private BaseDeLivros() {
        this.livros = new ArrayList<>(CSVParser.parseBooks());
    }

    public static BaseDeLivros getInstance() {
        if (instance == null) {
            instance = new BaseDeLivros();
        }
        return instance;
    }

    public List<Livro> getLivros() {
        return new ArrayList<>(livros); // Return copy for safety
    }

    public void salvarDados() {
        CSVParser.salvarLivros(livros);
    }
}