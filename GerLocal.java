import java.io.*;
import java.util.*;
import java.nio.file.*;

// ENUM para gêneros simplificado
// Define os possíveis gêneros literários disponíveis no sistema
enum Genero {
    FICCAO, FANTASIA, CIENCIA, TECNOLOGIA, HISTORIA, BIOGRAFIA, ROMANCE, 
    AVENTURA, SUSPENSE, TERROR, POESIA, DRAMA, COMEDIA, INFANTIL, JUVENIL, 
    AUTO_AJUDA, VIAGENS, GASTRONOMIA, ARTE, FILOSOFIA, PSICOLOGIA, ECONOMIA, 
    POLITICA, DIREITO, ESPORTES, SAUDE, RELIGIAO, ESOTERISMO, DIDATICO, 
    REFERENCIA, HQB, OUTRO;
    
    // Método para validar se uma string representa um gênero válido
    public static boolean isValid(String genero) {
        for (Genero g : values()) {
            if (g.name().equalsIgnoreCase(genero)) {
                return true;
            }
        }
        return false;
    }
}

// ENUM para estantes simplificado
// Define as estantes disponíveis na biblioteca (de A a P)
enum Estante {
    A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P;
    
    // Método para validar se uma string representa uma estante válida
    public static boolean isValid(String estante) {
        try {
            Estante.valueOf(estante.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}

// Classe principal que gerencia a localização dos livros na biblioteca
public class GerLocal {
	
    private ArrayList<Livro> livros; // Lista de todos os livros
    private Scanner scan; // Scanner para entrada de dados do usuário
    private static int proximoId = 0; // Contador para gerar IDs únicos
    
    // Construtor que inicializa a lista de livros e o scanner
    public GerLocal() {
        this.livros = new ArrayList<>();
        this.scan = new Scanner(System.in);
    }
    
    // Carregar livros do CSV
    // Lê um arquivo CSV e importa os dados para a lista de livros
    public void carregarLivros(String arquivoCSV) {
        try {
            // Lê todas as linhas do arquivo
            List<String> linhas = Files.readAllLines(Paths.get(arquivoCSV));
            
            // Processa cada linha (ignorando o cabeçalho na linha 0)
            for (int i = 1; i < linhas.size(); i++) {
                String linha = linhas.get(i);
                if (linha.trim().isEmpty()) continue; // Ignora linhas vazias
                
                // Divide a linha em campos, tratando vírgulas dentro de aspas
                String[] dados = linha.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                
                // Verifica se a linha tem pelo menos 8 campos (dados completos do livro)
                if (dados.length >= 8) {
                    // Extrai e limpa os dados de cada campo
                    String titulo = dados[0].trim().replace("\"", "");
                    String isbn = dados[1].trim().replace("\"", "");
                    String autor = dados[2].trim().replace("\"", "");
                    String ano = dados[3].trim().replace("\"", "");
                    String editora = dados[4].trim().replace("\"", "");
                    String genero = dados[5].trim().replace("\"", "");
                    String sinopse = dados[6].trim().replace("\"", "");
                    String local = dados[7].trim().replace("\"", "");
                    
                    // Cria um novo livro e adiciona à lista
                    Livro livro = new Livro(titulo, isbn, autor, ano, editora, genero, sinopse, local, proximoId);
                    livros.add(livro);
                    proximoId++; // Incrementa o ID para o próximo livro
                }
            }
            System.out.println("Carregados " + livros.size() + " livros do arquivo.");
        } catch (IOException e) {
            System.out.println("Erro ao carregar arquivo: " + e.getMessage());
        }
    }
    
    // Buscar livro por ISBN
    // Retorna o livro com o ISBN especificado ou null se não encontrado
    public Livro buscarPorIsbn(String isbn) {
        for (Livro livro : livros) {
            if (livro.isbn.equals(isbn)) {
                return livro;
            }
        }
        return null;
    }
    
    // Buscar livros por estante
    // Retorna todos os livros que estão em uma determinada estante
    public ArrayList<Livro> buscarPorEstante(String estante) {
        ArrayList<Livro> resultado = new ArrayList<>();
        for (Livro livro : livros) {
            // Verifica se o livro está na estante (com ou sem o prefixo "Estante ")
            if (livro.local.equalsIgnoreCase("Estante " + estante) || 
                livro.local.equalsIgnoreCase(estante)) {
                resultado.add(livro);
            }
        }
        return resultado;
    }
    
    // Buscar livros por gênero
    // Retorna todos os livros de um determinado gênero
    public ArrayList<Livro> buscarPorGenero(String genero) {
        ArrayList<Livro> resultado = new ArrayList<>();
        for (Livro livro : livros) {
            if (livro.genero.equalsIgnoreCase(genero)) {
                resultado.add(livro);
            }
        }
        return resultado;
    }
    
    // Mover livro para outra estante
    // Move um livro para uma nova estante, validando se a estante é válida
    public boolean moverLivro(String isbn, String novaEstante) {
        // Valida se a nova estante é válida
        if (!Estante.isValid(novaEstante)) {
            System.out.println("Estante inválida. Estantes disponíveis: " + 
                              Arrays.toString(Estante.values()));
            return false;
        }
        
        // Busca o livro pelo ISBN
        Livro livro = buscarPorIsbn(isbn);
        if (livro != null) {
            // Atualiza a localização do livro
            livro.local = "Estante " + novaEstante.toUpperCase();
            System.out.println("Livro movido para " + livro.local);
            return true;
        } else {
            System.out.println("Livro com ISBN " + isbn + " não encontrado.");
            return false;
        }
    }
    
    // Reclassificar livro por gênero
    // Altera o gênero de um livro, validando se o gênero é válido
    public boolean reclassificarLivro(String isbn, String novoGenero) {
        // Valida se o novo gênero é válido
        if (!Genero.isValid(novoGenero)) {
            System.out.println("Gênero inválido. Gêneros disponíveis: " + 
                              Arrays.toString(Genero.values()));
            return false;
        }
        
        // Busca o livro pelo ISBN
        Livro livro = buscarPorIsbn(isbn);
        if (livro != null) {
            // Atualiza o gênero do livro
            livro.genero = novoGenero.toUpperCase();
            System.out.println("Livro reclassificado como: " + livro.genero);
            return true;
        } else {
            System.out.println("Livro com ISBN " + isbn + " não encontrado.");
            return false;
        }
    }
    
    // Contar livros por estante
    // Gera um relatório com a quantidade de livros em cada estante
    public void contarPorEstante() {
        Map<String, Integer> contagem = new HashMap<>();
        
        // Inicializa o mapa com todas as estantes possíveis (contagem zero)
        for (Estante estante : Estante.values()) {
            contagem.put(estante.name(), 0);
        }
        
        // Conta os livros em cada estante
        for (Livro livro : livros) {
            String estante = livro.local.replace("Estante ", "").toUpperCase();
            if (contagem.containsKey(estante)) {
                contagem.put(estante, contagem.get(estante) + 1);
            }
        }
        
        // Exibe o relatório
        System.out.println("\n=== LIVROS POR ESTANTE ===");
        for (Map.Entry<String, Integer> entry : contagem.entrySet()) {
            System.out.println("Estante " + entry.getKey() + ": " + entry.getValue() + " livros");
        }
    }
    
    // Contar livros por gênero
    // Gera um relatório com a quantidade de livros de cada gênero
    public void contarPorGenero() {
        Map<String, Integer> contagem = new HashMap<>();
        
        // Conta os livros de cada gênero
        for (Livro livro : livros) {
            String genero = livro.genero.toUpperCase();
            contagem.put(genero, contagem.getOrDefault(genero, 0) + 1);
        }
        
        // Exibe o relatório
        System.out.println("\n=== LIVROS POR GÊNERO ===");
        for (Map.Entry<String, Integer> entry : contagem.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " livros");
        }
    }
    
    // Listar todos os livros
    // Exibe todos os livros do sistema
    public void listarTodos() {
        System.out.println("\n=== TODOS OS LIVROS ===");
        for (Livro livro : livros) {
            System.out.println(livro);
        }
    }
    
    // Interface de menu simplificada
    // Menu principal que permite ao usuário interagir com o sistema
    public void menu() {
        // Carrega os livros do arquivo CSV ao iniciar
        carregarLivros("livros.csv");
        
        int opcao;
        do {
            // Exibe as opções do menu
            System.out.println("\n=== GERENCIADOR DE LOCALIZAÇÃO ===");
            System.out.println("1. Listar todos os livros");
            System.out.println("2. Buscar por estante");
            System.out.println("3. Buscar por gênero");
            System.out.println("4. Mover livro");
            System.out.println("5. Reclassificar livro");
            System.out.println("6. Contagem por estante");
            System.out.println("7. Contagem por gênero");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            
            // Lê a opção do usuário
            opcao = scan.nextInt();
            scan.nextLine(); // Consome a quebra de linha
            
            // Processa a opção selecionada
            switch (opcao) {
                case 1:
                    listarTodos();
                    break;
                case 2:
                    System.out.print("Digite a estante: ");
                    String estante = scan.nextLine();
                    ArrayList<Livro> resultadoEstante = buscarPorEstante(estante);
                    System.out.println("Encontrados " + resultadoEstante.size() + " livros:");
                    for (Livro livro : resultadoEstante) {
                        System.out.println(livro);
                    }
                    break;
                case 3:
                    System.out.print("Digite o gênero: ");
                    String genero = scan.nextLine();
                    ArrayList<Livro> resultadoGenero = buscarPorGenero(genero);
                    System.out.println("Encontrados " + resultadoGenero.size() + " livros:");
                    for (Livro livro : resultadoGenero) {
                        System.out.println(livro);
                    }
                    break;
                case 4:
                    System.out.print("Digite o ISBN do livro: ");
                    String isbnMover = scan.nextLine();
                    System.out.print("Digite a nova estante: ");
                    String novaEstante = scan.nextLine();
                    moverLivro(isbnMover, novaEstante);
                    break;
                case 5:
                    System.out.print("Digite o ISBN do livro: ");
                    String isbnReclassificar = scan.nextLine();
                    System.out.print("Digite o novo gênero: ");
                    String novoGenero = scan.nextLine();
                    reclassificarLivro(isbnReclassificar, novoGenero);
                    break;
                case 6:
                    contarPorEstante();
                    break;
                case 7:
                    contarPorGenero();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
            
            // Pausa antes de continuar (exceto quando sai)
            if (opcao != 0) {
                System.out.print("\nPressione Enter para continuar...");
                scan.nextLine();
            }
            
        } while (opcao != 0); // Repete até o usuário escolher sair
    }
    
    // Método main
    // Ponto de entrada do programa
    //public static void main(String[] args) {
        // Cria uma instância do gerenciador e inicia o menu
        //gerLocal gerenciador = new gerLocal();
        //gerenciador.menu();
    //}
}