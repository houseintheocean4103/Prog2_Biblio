package repository;

import core.Livro;

import java.io.*;           //Biblioteca de Fluxo de Entrada e Saída
import java.util.ArrayList; //Biblioteca de Listas Dinâmicas
import java.util.List;      //Biblioteca de Listas Estáticas

public class CSVParser {
    // Caminho fixo do arquivo CSV
    private static final String CSV_FILE_PATH = "livros.csv";

    // Lê todos os livros do arquivo CSV e retorna como lista
    public static List<Livro> parseBooks() {
        List<Livro> books = new ArrayList<>();
        int distId = 0; // Contador para atribuir IDs únicos

        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue; // ignora linhas vazias

                // Converte a linha do CSV em objeto Livro
                Livro book = parseBookLine(line, distId++);
                if (book != null) {
                    books.add(book);
                }
            }
            //Sinaliza o sucesso ou falha da operação, capturando a exceção
            System.out.println("Importação concluída! " + books.size() + " livros carregados.");
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }

        return books;
    }

    // Salva todos os livros em um arquivo CSV, sobrescrevendo o anterior
    public static void salvarLivros(List<Livro> livros) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE_PATH))) {
            for (Livro livro : livros) {
                writer.write(formatBookLine(livro)); // Formata cada livro em linha CSV
                writer.newLine();
            }
            //Sinaliza o sucesso ou falha da operação, capturando a exceção
            System.out.println("Dados salvos com sucesso! " + livros.size() + " livros exportados.");
        } catch (IOException e) {
            System.out.println("Erro ao salvar arquivo: " + e.getMessage());
        }
    }

    // Converte um objeto Livro em linha string no formato CSV
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

    // Converte uma linha do CSV em objeto Livro
    private static Livro parseBookLine(String line, int distId) {
        String[] fields = line.split(","); // Divide pelos separadores

        if (fields.length < 8) { // Valida quantidade de campos
            System.err.println("Linha inválida: " + line);
            return null;
        }

        try {
            // Cria novo livro a partir dos campos extraídos
            return new Livro(
                    fields[0].trim(), // título
                    fields[1].trim(), // ISBN
                    fields[2].trim(), // autor
                    fields[3].trim(), // ano
                    fields[4].trim(), // editora
                    fields[5].trim(), // gênero
                    fields[6].trim(), // sinopse
                    fields[7].trim(), // local
                    distId            // id único
            );
          //Sinaliza o sucesso ou falha da operação, capturando a exceção
        } catch (Exception e) {
            System.err.println("Erro ao processar linha: " + line + " - " + e.getMessage());
            return null;
        }
    }
}
