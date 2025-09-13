import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class GerDev {
    
    Scanner scan = new Scanner(System.in);
    ArrayList<Emprestimo> emprestimos = new ArrayList<>();
    GerUsers gerUsuarios = new GerUsers();
    gerLivros gerLivros;
    static int proximoId = 1;
    
    public GerDev(gerLivros gerLivros) {
        this.gerLivros = gerLivros;
    }
    
    class Emprestimo {
        int id;
        String cpfUsuario;
        int idLivro;
        LocalDate dataEmprestimo;
        LocalDate dataDevolucao;
        boolean devolvido;
        int renovacoes;
        
        public Emprestimo(String cpfUsuario, int idLivro) {
            this.id = proximoId++;
            this.cpfUsuario = cpfUsuario;
            this.idLivro = idLivro;
            this.dataEmprestimo = LocalDate.now();
            this.dataDevolucao = LocalDate.now().plusDays(15);
            this.devolvido = false;
            this.renovacoes = 0;
        }
        
        public boolean atrasado() {
            return LocalDate.now().isAfter(dataDevolucao) && !devolvido;
        }
        
        public int diasAtraso() {
            if (!atrasado()) return 0;
            return (int) LocalDate.now().toEpochDay() - (int) dataDevolucao.toEpochDay();
        }
        
        public double multa() {
            return diasAtraso() * 1.5;
        }
        
        @Override
        public String toString() {
            return String.format("ID: %d | Usuario: %s | Livro: %d | Emprestimo: %s | Devolucao: %s | %s", 
                    id, cpfUsuario, idLivro, dataEmprestimo, dataDevolucao, 
                    devolvido ? "DEVOLVIDO" : (atrasado() ? "ATRASADO" : "ATIVO"));
        }
    }
    
    public User buscarUsuario(String cpf) {
        for (User u : gerUsuarios.usuarios) {
            if (u.cpf.equals(cpf) && u.isAtivo()) {
                return u;
            }
        }
        return null;
    }
    
    public Livro buscarLivro(int id) {
        for (Livro l : gerLivros.livros) {
            if (l.id == id && l.isAtivo()) {
                return l;
            }
        }
        return null;
    }
    
    public boolean livroEmprestado(int idLivro) {
        for (Emprestimo e : emprestimos) {
            if (e.idLivro == idLivro && !e.devolvido) {
                return true;
            }
        }
        return false;
    }
    
    public int contarEmprestimos(String cpf) {
        int count = 0;
        for (Emprestimo e : emprestimos) {
            if (e.cpfUsuario.equals(cpf) && !e.devolvido) {
                count++;
            }
        }
        return count;
    }
    
    public boolean temAtraso(String cpf) {
        for (Emprestimo e : emprestimos) {
            if (e.cpfUsuario.equals(cpf) && e.atrasado()) {
                return true;
            }
        }
        return false;
    }
    
    public void fazerEmprestimo() {
        System.out.print("CPF do usuario: ");
        String cpf = scan.nextLine();
        
        User usuario = buscarUsuario(cpf);
        if (usuario == null) {
            System.out.println("Usuario nao encontrado ou inativo.");
            return;
        }
        
        if (temAtraso(cpf)) {
            System.out.println("Usuario tem livros em atraso. Nao pode pegar mais livros.");
            return;
        }
        
        if (contarEmprestimos(cpf) >= 3) {
            System.out.println("Usuario ja tem 3 livros emprestados (limite maximo).");
            return;
        }
        
        System.out.print("ID do livro: ");
        int idLivro = scan.nextInt();
        scan.nextLine();
        
        Livro livro = buscarLivro(idLivro);
        if (livro == null) {
            System.out.println("Livro nao encontrado ou inativo.");
            return;
        }
        
        if (livroEmprestado(idLivro)) {
            System.out.println("Livro ja esta emprestado.");
            return;
        }
        
        Emprestimo emp = new Emprestimo(cpf, idLivro);
        emprestimos.add(emp);
        System.out.printf("Emprestimo feito! ID: %d\nDevolucao ate: %s\n", 
                         emp.id, emp.dataDevolucao);
    }
    
    public void devolverLivro() {
        System.out.print("ID do emprestimo: ");
        int id = scan.nextInt();
        scan.nextLine();
        
        for (Emprestimo e : emprestimos) {
            if (e.id == id && !e.devolvido) {
                e.devolvido = true;
                System.out.println("Livro devolvido!");
                if (e.multa() > 0) {
                    System.out.printf("Multa por atraso: R$ %.2f\n", e.multa());
                }
                return;
            }
        }
        System.out.println("Emprestimo nao encontrado ou ja devolvido.");
    }
    
    public void renovar() {
        System.out.print("ID do emprestimo: ");
        int id = scan.nextInt();
        scan.nextLine();
        
        for (Emprestimo e : emprestimos) {
            if (e.id == id && !e.devolvido) {
                if (e.renovacoes >= 1) {
                    System.out.println("Limite de renovacao atingido (apenas 1 renovacao).");
                    return;
                }
                if (e.atrasado()) {
                    System.out.println("Livro em atraso nao pode ser renovado.");
                    return;
                }
                e.dataDevolucao = e.dataDevolucao.plusDays(15);
                e.renovacoes++;
                System.out.printf("Renovado! Nova data: %s\n", e.dataDevolucao);
                return;
            }
        }
        System.out.println("Emprestimo nao encontrado.");
    }
    
    public void listarAtivos() {
        System.out.println("\n=== EMPRESTIMOS ATIVOS ===");
        for (Emprestimo e : emprestimos) {
            if (!e.devolvido) {
                System.out.println(e);
                if (e.atrasado()) {
                    System.out.printf("  MULTA: R$ %.2f\n", e.multa());
                }
            }
        }
    }
    
    public void listarAtrasados() {
        System.out.println("\n=== LIVROS ATRASADOS ===");
        for (Emprestimo e : emprestimos) {
            if (e.atrasado()) {
                System.out.println(e);
                User usuario = buscarUsuario(e.cpfUsuario);
                if (usuario != null) {
                    System.out.printf("  Usuario: %s\n", usuario.nome);
                }
                System.out.printf("  Multa: R$ %.2f\n", e.multa());
            }
        }
    }
    
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
        
        double multaTotal = 0;
        int emprestimosAtivos = 0;
        
        for (Emprestimo e : emprestimos) {
            if (e.cpfUsuario.equals(cpf)) {
                System.out.println(e);
                if (!e.devolvido) emprestimosAtivos++;
                if (e.atrasado()) multaTotal += e.multa();
            }
        }
        
        System.out.printf("Total emprestimos ativos: %d\n", emprestimosAtivos);
        System.out.printf("Total multas: R$ %.2f\n", multaTotal);
    }
    
    public void menu() {
        int opcao;
        
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
            scan.nextLine();
            
            switch (opcao) {
                case 1:
                    gerUsuarios.cadastroUsuario();
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
                    gerUsuarios.listarUsuarios();
                    break;
                case 0:
                    System.out.println("Voltando...");
                    break;
                default:
                    System.out.println("Opcao invalida!");
            }
        } while (opcao != 0);
    }
}