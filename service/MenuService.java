package service;

import java.util.Scanner;

public class MenuService {
    private final Scanner scanner;

    public MenuService(Scanner scanner) {
        this.scanner = scanner;
    }

    public void exibirMenuPrincipal() {
        System.out.println("\n=== SISTEMA DE GERENCIAMENTO DE LIVROS ===");
        System.out.println("Qual serviço deseja realizar ?");
        System.out.println("[1] Buscar livro");
        System.out.println("[2] Listar livros");
        System.out.println("[3] Cadastrar livro");
        System.out.println("[4] Criar cópia de livro");
        System.out.println("[5] Editar livro");
        System.out.println("[6] Desativar livro");
        System.out.println("[7] Ativar livro");
        System.out.println("[8] Deletar livro");
        System.out.println("[9] Exportar dados");
        System.out.println("[0] Sair");
        System.out.print("Opção: ");
    }

    public void exibirMenuListagem() {
        System.out.println("\n=== LISTAGEM DE LIVROS ===");
        System.out.println("Qual filtro deseja utilizar?");
        System.out.println("[1] Todos os livros");
        System.out.println("[2] Livros ativos");
        System.out.println("[3] Livros inativos");
        System.out.println("[4] Por autor");
        System.out.println("[5] Por editora");
        System.out.println("[6] Por gênero");
        System.out.println("[7] Por localização");
        System.out.println("[8] Livros emprestados");
        System.out.println("[0] Voltar ao menu principal");
        System.out.print("Opção: ");
    }

    public void exibirMenuBusca() {
        System.out.println("\n=== BUSCA DE LIVROS ===");
        System.out.println("Qual o parâmetro de busca?");
        System.out.println("[1] Título");
        System.out.println("[2] ISBN");
        System.out.println("[3] Sinopse");
        System.out.println("[4] ID");
        System.out.println("[0] Voltar ao menu principal");
        System.out.print("Opção: ");
    }

    public void exibirMenuEdicao() {
        System.out.println("\n=== EDIÇÃO DE LIVRO ===");
        System.out.println("Qual dado deseja alterar?");
        System.out.println("[1] Título");
        System.out.println("[2] ISBN");
        System.out.println("[3] Autor(a)");
        System.out.println("[4] Ano de publicação");
        System.out.println("[5] Editora");
        System.out.println("[6] Gênero");
        System.out.println("[7] Sinopse");
        System.out.println("[8] Localização");
        System.out.println("[0] Finalizar edição");
        System.out.print("Opção: ");
    }

    public void exibirMenuCadastro() {
        System.out.println("\n=== CADASTRO DE LIVRO ===");
    }

    public void exibirMenuCopia() {
        System.out.println("\n=== CRIAR CÓPIA DE LIVRO ===");
    }

    // Utility methods
    public int lerInteiro(String mensagem) {
        System.out.print(mensagem);
        try {
            return scanner.nextInt();
        } finally {
            scanner.nextLine();
        }
    }

    public String lerString(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine();
    }

    public void exibirMensagem(String mensagem) {
        System.out.println(mensagem);
    }

    public void exibirErro(String mensagem) {
        System.err.println("Erro: " + mensagem);
    }

    public void limparTela() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void pausar() {
        System.out.print("Pressione ENTER para continuar...");
        scanner.nextLine();
    }
}