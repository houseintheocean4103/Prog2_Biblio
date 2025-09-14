package core;
import service.LivroService;
import service.impl.LivroServiceImpl;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Classe responsável por gerenciar empréstimos e devoluções de livros
public class GerenteDevolucao {
    
    // Scanner único para toda a classe, evita conflitos de entrada
    Scanner scan = new Scanner(System.in);
    
    // Lista que armazena todos os empréstimos realizados
    ArrayList<Emprestimo> emprestimos = new ArrayList<>();
    
    // Integração com o sistema de usuários existente
    GerenteUser gerUsuarios = new GerenteUser();
    
    // Integração com o sistema de livros existente
    LivroService livroservice;
    
    // Contador global para IDs únicos de empréstimos
    static int proximoId = 1;
    
    // Construtor que recebe o gerenciador de livros por dependência
    public GerenteDevolucao() {
    	this.livroservice = new LivroServiceImpl();
    }
    
    private List<Livro> getLivrosAtualizados() {
        return livroservice.getTodosLivros();
    }
    
    // Classe interna que representa um empréstimo individual
    class Emprestimo {
        int id;                    // Identificador único do empréstimo
        String cpfUsuario;         // CPF do usuário (mais natural que ID numérico)
        int idLivro;              // ID do livro emprestado
        LocalDate dataEmprestimo; // Quando o empréstimo foi feito
        LocalDate dataDevolucao;  // Quando deve ser devolvido
        boolean devolvido;        // Se já foi devolvido ou não
        int renovacoes;           // Quantas vezes foi renovado
        
        // Construtor que inicializa um novo empréstimo
        public Emprestimo(String cpfUsuario, int idLivro) {
            this.id = proximoId++;                              // ID único e auto-incrementado
            this.cpfUsuario = cpfUsuario;
            this.idLivro = idLivro;
            this.dataEmprestimo = LocalDate.now();              // Data atual
            this.dataDevolucao = LocalDate.now().plusDays(15);  // 15 dias para devolver
            this.devolvido = false;                             // Começa como não devolvido
            this.renovacoes = 0;                                // Zero renovações inicialmente
        }
        
        // Verifica se o empréstimo está atrasado
        public boolean atrasado() {
            return LocalDate.now().isAfter(dataDevolucao) && !devolvido;
        }
        
        // Calcula quantos dias de atraso tem
        public int diasAtraso() {
            if (!atrasado()) return 0;  // Se não está atrasado, retorna zero
            // Calcula diferença em dias usando epoch days
            return (int) LocalDate.now().toEpochDay() - (int) dataDevolucao.toEpochDay();
        }
        
        // Calcula a multa por atraso (R$ 1,50 por dia)
        public double multa() {
            return diasAtraso() * 1.5;
        }
        
        // Representação textual do empréstimo com status dinâmico
        @Override
        public String toString() {
            return String.format("ID: %d | Usuario: %s | Livro: %d | Emprestimo: %s | Devolucao: %s | %s", 
                    id, cpfUsuario, idLivro, dataEmprestimo, dataDevolucao, 
                    devolvido ? "DEVOLVIDO" : (atrasado() ? "ATRASADO" : "ATIVO"));
        }
    }
    
    // Busca um usuário pelo CPF na lista do GerUsers
    public User buscarUsuario(String cpf) {
        for (User u : gerUsuarios.usuarios) {
            // Retorna apenas usuários ativos com CPF exato
            if (u.cpf.equals(cpf) && u.isAtivo()) {
                return u;
            }
        }
        return null;
    }
    
    // Busca um livro pelo ID na lista do gerLivros
    public Livro buscarLivro(int id) {
        for (Livro l : getLivrosAtualizados()) {
            // Retorna apenas livros ativos com ID exato
            if (l.id == id && l.isAtivo()) {
                return l;
            }
        }
        return null;
    }
    
    // Verifica se um livro específico está emprestado (não disponível)
    public boolean livroEmprestado(int idLivro) {
        for (Emprestimo e : emprestimos) {
            // Se alguém pegou este livro e ainda não devolveu, está emprestado
            if (e.idLivro == idLivro && !e.devolvido) {
                return true;
            }
        }
        return false;
    }
    
    // Conta quantos livros um usuário tem emprestado atualmente
    public int contarEmprestimos(String cpf) {
        int count = 0;
        for (Emprestimo e : emprestimos) {
            // Conta apenas empréstimos não devolvidos
            if (e.cpfUsuario.equals(cpf) && !e.devolvido) {
                count++;
            }
        }
        return count;
    }
    
    // Verifica se um usuário tem algum livro em atraso
    public boolean temAtraso(String cpf) {
        for (Emprestimo e : emprestimos) {
            // Se encontrar qualquer empréstimo atrasado, retorna true
            if (e.cpfUsuario.equals(cpf) && e.atrasado()) {
                return true;
            }
        }
        return false;
    }
    
    // Realiza um novo empréstimo com todas as validações necessárias
    public void fazerEmprestimo() {
        System.out.print("CPF do usuario: ");
        String cpf = scan.nextLine();
        
        // Primeira validação: Usuário existe e está ativo?
        User usuario = buscarUsuario(cpf);
        if (usuario == null) {
            System.out.println("Usuario nao encontrado ou inativo.");
            return;
        }
        
        // Segunda validação: Usuário não tem livros em atraso?
        if (temAtraso(cpf)) {
            System.out.println("Usuario tem livros em atraso. Nao pode pegar mais livros.");
            return;
        }
        
        // Terceira validação: Usuário não excedeu o limite de 3 livros?
        if (contarEmprestimos(cpf) >= 3) {
            System.out.println("Usuario ja tem 3 livros emprestados (limite maximo).");
            return;
        }
        
        System.out.print("ID do livro: ");
        int idLivro = scan.nextInt();
        scan.nextLine(); // Consome a quebra de linha deixada pelo nextInt()
        
        // Quarta validação: Livro existe e está ativo?
        Livro livro = buscarLivro(idLivro);
        if (livro == null) {
            System.out.println("Livro nao encontrado ou inativo.");
            return;
        }
        
        // Quinta validação: Livro está disponível (não emprestado)?
        if (livroEmprestado(idLivro)) {
            System.out.println("Livro ja esta emprestado.");
            return;
        }
        
        // Se passou por todas as validações, cria o empréstimo
        Emprestimo emp = new Emprestimo(cpf, idLivro);
        emprestimos.add(emp);
        System.out.printf("Emprestimo feito! ID: %d\nDevolucao ate: %s\n", 
                         emp.id, emp.dataDevolucao);
    }
    
    // Processa a devolução de um livro
    public void devolverLivro() {
        System.out.print("ID do emprestimo: ");
        int id = scan.nextInt();
        scan.nextLine();
        
        // Busca o empréstimo ativo com este ID
        for (Emprestimo e : emprestimos) {
            if (e.id == id && !e.devolvido) {
                e.devolvido = true; // Marca como devolvido
                System.out.println("Livro devolvido!");
                
                // Se houver multa, calcula e exibe
                if (e.multa() > 0) {
                    System.out.printf("Multa por atraso: R$ %.2f\n", e.multa());
                }
                return;
            }
        }
        System.out.println("Emprestimo nao encontrado ou ja devolvido.");
    }
    
    // Renova um empréstimo, estendendo o prazo de devolução
    public void renovar() {
        System.out.print("ID do emprestimo: ");
        int id = scan.nextInt();
        scan.nextLine();
        
        // Busca o empréstimo ativo
        for (Emprestimo e : emprestimos) {
            if (e.id == id && !e.devolvido) {
                // Primeira validação: Não excedeu o limite de renovações?
                if (e.renovacoes >= 1) {
                    System.out.println("Limite de renovacao atingido (apenas 1 renovacao).");
                    return;
                }
                
                // Segunda validação: Não está em atraso?
                if (e.atrasado()) {
                    System.out.println("Livro em atraso nao pode ser renovado.");
                    return;
                }
                
                // Renovação: adiciona 15 dias e incrementa contador
                e.dataDevolucao = e.dataDevolucao.plusDays(15);
                e.renovacoes++;
                System.out.printf("Renovado! Nova data: %s\n", e.dataDevolucao);
                return;
            }
        }
        System.out.println("Emprestimo nao encontrado.");
    }
    
    // Lista todos os empréstimos que ainda não foram devolvidos
    public void listarAtivos() {
        System.out.println("\n=== EMPRESTIMOS ATIVOS ===");
        for (Emprestimo e : emprestimos) {
            if (!e.devolvido) {
                System.out.println(e);
                // Se estiver atrasado, destaca a multa
                if (e.atrasado()) {
                    System.out.printf("  MULTA: R$ %.2f\n", e.multa());
                }
            }
        }
    }
    
    // Lista apenas os empréstimos que estão em atraso
    public void listarAtrasados() {
        System.out.println("\n=== LIVROS ATRASADOS ===");
        for (Emprestimo e : emprestimos) {
            if (e.atrasado()) {
                System.out.println(e);
                
                // Busca e exibe informações do usuário
                User usuario = buscarUsuario(e.cpfUsuario);
                if (usuario != null) {
                    System.out.printf("  Usuario: %s\n", usuario.nome);
                }
                System.out.printf("  Multa: R$ %.2f\n", e.multa());
            }
        }
    }
    
    // Gera um relatório completo de um usuário específico
    public void relatorioUsuario() {
        System.out.print("CPF do usuario: ");
        String cpf = scan.nextLine();
        
        User usuario = buscarUsuario(cpf);
        if (usuario == null) {
            System.out.println("Usuario nao encontrado.");
            return;
        }
        
        System.out.printf("\n=== RELATORIO: %s ===\n", usuario.nome);
        System.out.printf("CPF: %s\n", usuario.cpf);
        
        // Variáveis para estatísticas
        double multaTotal = 0;
        int emprestimosAtivos = 0;
        
        // Percorre todos os empréstimos deste usuário
        for (Emprestimo e : emprestimos) {
            if (e.cpfUsuario.equals(cpf)) {
                System.out.println(e);
                if (!e.devolvido) emprestimosAtivos++;      // Conta ativos
                if (e.atrasado()) multaTotal += e.multa();  // Soma multas
            }
        }
        
        // Exibe estatísticas consolidadas
        System.out.printf("Total emprestimos ativos: %d\n", emprestimosAtivos);
        System.out.printf("Total multas: R$ %.2f\n", multaTotal);
    }
    
    // Menu principal com todas as funcionalidades
    public void menu() {
        int opcao;
        
        // Loop principal do menu
        do {
            System.out.println("\n=== EMPRESTIMOS E DEVOLUCOES ===");
            System.out.println("[1] Cadastrar usuario");
            System.out.println("[2] Fazer emprestimo");
            System.out.println("[3] Devolver livro");
            System.out.println("[4] Renovar emprestimo");
            System.out.println("[5] Ver emprestimos ativos");
            System.out.println("[6] Ver livros atrasados");
            System.out.println("[7] Relatorio de usuario");
            System.out.println("[8] Listar usuarios");
            System.out.println("[0] Voltar");
            
            opcao = scan.nextInt();
            scan.nextLine(); // Consome a quebra de linha
            
            // Processa a opção escolhida
            switch (opcao) {
                case 1:
                    gerUsuarios.cadastroUsuario(); // Reutiliza funcionalidade existente
                    break;
                case 2:
                    fazerEmprestimo();
                    break;
                case 3:
                    devolverLivro();
                    break;
                case 4:
                    renovar();
                    break;
                case 5:
                    listarAtivos();
                    break;
                case 6:
                    listarAtrasados();
                    break;
                case 7:
                    relatorioUsuario();
                    break;
                case 8:
                    gerUsuarios.listarUsuarios(); // Reutiliza funcionalidade existente
                    break;
                case 0:
                    System.out.println("Voltando...");
                    break;
                default:
                    System.out.println("Opcao invalida!");
            }
        } while (opcao != 0); // Continua até o usuário escolher sair
    }
}
