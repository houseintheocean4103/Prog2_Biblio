package repository;

import core.Livro;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CSVParser {
    private static final String CSV_FILE_PATH = "livros.csv";

    public static List<Livro> parseBooks() {
        List<Livro> books = new ArrayList<>();
        int distId = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                Livro book = parseBookLine(line, distId++);
                if (book != null) {
                    books.add(book);
                }
            }
            System.out.println("Importação concluída! " + books.size() + " livros carregados.");
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }

        return books;
    }

    public static void salvarLivros(List<Livro> livros) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE_PATH))) {
            for (Livro livro : livros) {
                writer.write(formatBookLine(livro));
                writer.newLine();
            }
            System.out.println("Dados salvos com sucesso! " + livros.size() + " livros exportados.");
        } catch (IOException e) {
            System.out.println("Erro ao salvar arquivo: " + e.getMessage());
        }
    }

    private static String formatBookLine(Livro livro) {
        return String.join(",",
                livro.getTitulo(),
                livro.getIsbn(),
                livro.getAutor(),
                livro.getAno(),
                livro.getEditora(),
                livro.getGenero(),
                livro.getSinopse(),
                livro.getLocal()
        );
    }

    private static Livro parseBookLine(String line, int distId) {
        String[] fields = line.split(",");

        if (fields.length < 8) {
            System.err.println("Linha inválida: " + line);
            return null;
        }

        try {
            return new Livro(
                    fields[0].trim(),
                    fields[1].trim(),
                    fields[2].trim(),
                    fields[3].trim(),
                    fields[4].trim(),
                    fields[5].trim(),
                    fields[6].trim(),
                    fields[7].trim(),
                    distId
            );
        } catch (Exception e) {
            System.err.println("Erro ao processar linha: " + line + " - " + e.getMessage());
            return null;
        }
    }
}