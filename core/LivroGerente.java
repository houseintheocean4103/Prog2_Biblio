package core;

import service.LivroService;
import service.impl.LivroServiceImpl;
import service.MenuService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class LivroGerente {
    private final LivroService livroService;
    private final MenuService menuService;
    private final Scanner scanner;

    public LivroGerente() {
        this.scanner = new Scanner(System.in);
        this.livroService = new LivroServiceImpl();
        this.menuService = new MenuService(scanner);
    }

    public void iniciar() {
        int opcao;
        do {
            menuService.limparTela();
            menuService.exibirMenuPrincipal();
            opcao = menuService.lerInteiro("");

            switch (opcao) {
                case 1 -> buscarLivro();
                case 2 -> listarLivros();
                case 3 -> cadastrarLivro();
                case 4 -> copiarLivro();
                case 5 -> editarLivro();
                case 6 -> desativarLivro();
                case 7 -> ativarLivro();
                case 8 -> deletarLivro();
                case 9 -> exportarDados();
                case 0 -> menuService.exibirMensagem("Saindo do sistema...");
                default -> menuService.exibirErro("Opção inválida!");
            }

            if (opcao != 0) {
                menuService.pausar();
            }
        } while (opcao != 0);

        scanner.close();
    }

    private void buscarLivro() {
        menuService.exibirMenuBusca();
        int opcao = menuService.lerInteiro("");

        switch (opcao) {
            case 1 -> buscarPorTitulo();
            case 2 -> buscarPorISBN();
            case 3 -> buscarPorSinopse();
            case 4 -> buscarPorId();
            case 0 -> {
            }
            default -> menuService.exibirErro("Opção inválida!");
        }
    }

    private void buscarPorTitulo() {
        String titulo = menuService.lerString("Digite o título: ");
        var livros = livroService.buscarPorTitulo(titulo);
        exibirResultadosBusca(livros);
    }

    private void buscarPorISBN() {
        String isbn = menuService.lerString("Digite o ISBN: ");
        List<Livro> livrosEncontrados = new ArrayList<>();
        for (Livro livro : livroService.getTodosLivros()) {
            if (livro.getIsbn().equalsIgnoreCase(isbn)) {
                livrosEncontrados.add(livro);
            }
        }
        exibirResultadosBusca(livrosEncontrados);
    }

    private void buscarPorSinopse() {
        String sinopse = menuService.lerString("Digite a sinopse: ");
        List<Livro> livrosEncontrados = new ArrayList<>();
        for (Livro livro : livroService.getTodosLivros()) {
            if (livro.getSinopse().toLowerCase().contains(sinopse.toLowerCase())) {
                livrosEncontrados.add(livro);
            }
        }
        exibirResultadosBusca(livrosEncontrados);
    }

    private void buscarPorId() {
        int id = menuService.lerInteiro("Digite o ID: ");
        Optional<Livro> livro = livroService.buscarPorId(id);
        if (livro.isPresent()) {
            menuService.exibirMensagem(livro.get().toString());
        } else {
            menuService.exibirErro("Livro com ID " + id + " não encontrado");
        }
    }

    private void listarLivros() {
        menuService.exibirMenuListagem();
        int opcao = menuService.lerInteiro("");

        List<Livro> livros;
        switch (opcao) {
            case 1 -> livros = livroService.getTodosLivros();
            case 2 -> livros = livroService.buscarPorStatus(true);
            case 3 -> livros = livroService.buscarPorStatus(false);
            case 4 -> {
                String autor = menuService.lerString("Digite o autor: ");
                livros = livroService.buscarPorAutor(autor);
            }
            case 5 -> {
                String editora = menuService.lerString("Digite a editora: ");
                livros = livroService.buscarPorEditora(editora);
            }
            case 6 -> {
                String genero = menuService.lerString("Digite o gênero: ");
                livros = livroService.buscarPorGenero(genero);
            }
            case 7 -> {
                String local = menuService.lerString("Digite a localização: ");
                livros = livroService.buscarPorLocalizacao(local);
            }
            case 8 -> {
                livros = livroService.buscarPorStatus(true);
                menuService.exibirMensagem("Livros disponíveis para empréstimo:");
            }
            case 0 -> {
                return;
            }
            default -> {
                menuService.exibirErro("Opção inválida!");
                return;
            }
        }

        exibirResultadosBusca(livros);
    }

    private void cadastrarLivro() {
        menuService.exibirMenuCadastro();

        String titulo = menuService.lerString("Título: ");
        String isbn = menuService.lerString("ISBN: ");
        String autor = menuService.lerString("Autor: ");
        String ano = menuService.lerString("Ano: ");
        String editora = menuService.lerString("Editora: ");
        String genero = menuService.lerString("Gênero: ");
        String sinopse = menuService.lerString("Sinopse: ");
        String local = menuService.lerString("Localização: ");

        Livro novoLivro = new Livro(titulo, isbn, autor, ano, editora, genero, sinopse, local, 0);

        try {
            livroService.adicionarLivro(novoLivro);
            menuService.exibirMensagem("Livro cadastrado com sucesso!");
        } catch (Exception e) {
            menuService.exibirErro(e.getMessage());
        }
    }

    private void copiarLivro() {
        menuService.exibirMenuCopia();
        int id = menuService.lerInteiro("Digite o ID do livro original: ");

        Optional<Livro> livroOptional = livroService.buscarPorId(id);
        if (livroOptional.isEmpty()) {
            menuService.exibirErro("Livro com ID " + id + " não encontrado");
            return;
        }

        Livro original = livroOptional.get();
        int copias = menuService.lerInteiro("Quantas cópias deseja criar? ");

        for (int i = 0; i < copias; i++) {
            Livro copia = new Livro(
                    original.getTitulo(),
                    original.getIsbn(),
                    original.getAutor(),
                    original.getAno(),
                    original.getEditora(),
                    original.getGenero(),
                    original.getSinopse(),
                    original.getLocal(),
                    0
            );
            livroService.adicionarLivro(copia);
        }

        menuService.exibirMensagem(copias + " cópias criadas com sucesso!");
    }

    private void editarLivro() {
        menuService.exibirMensagem("\n=== EDIÇÃO DE LIVRO ===");
        int id = menuService.lerInteiro("Digite o ID do livro a editar: ");

        Optional<Livro> livroOptional = livroService.buscarPorId(id);
        if (livroOptional.isEmpty()) {
            menuService.exibirErro("Livro com ID " + id + " não encontrado");
            return;
        }

        Livro livro = livroOptional.get();
        menuService.exibirMensagem("Editando: " + livro);

        int opcao;
        do {
            menuService.exibirMenuEdicao();
            opcao = menuService.lerInteiro("");

            Livro livroAtualizado = new Livro(
                    livro.getTitulo(),
                    livro.getIsbn(),
                    livro.getAutor(),
                    livro.getAno(),
                    livro.getEditora(),
                    livro.getGenero(),
                    livro.getSinopse(),
                    livro.getLocal(),
                    livro.getId()
            );

            switch (opcao) {
                case 1 -> livroAtualizado.setTitulo(menuService.lerString("Novo título: "));
                case 2 -> livroAtualizado.setIsbn(menuService.lerString("Novo ISBN: "));
                case 3 -> livroAtualizado.setAutor(menuService.lerString("Novo autor: "));
                case 4 -> livroAtualizado.setAno(menuService.lerString("Novo ano: "));
                case 5 -> livroAtualizado.setEditora(menuService.lerString("Nova editora: "));
                case 6 -> livroAtualizado.setGenero(menuService.lerString("Novo gênero: "));
                case 7 -> livroAtualizado.setSinopse(menuService.lerString("Nova sinopse: "));
                case 8 -> livroAtualizado.setLocal(menuService.lerString("Nova localização: "));
                case 0 -> {
                }
                default -> menuService.exibirErro("Opção inválida!");
            }

            if (opcao != 0 && opcao <= 8) {
                boolean sucesso = livroService.atualizarLivro(id, livroAtualizado);
                if (sucesso) {
                    menuService.exibirMensagem("Alteração realizada com sucesso!");
                } else {
                    menuService.exibirErro("Erro ao atualizar livro");
                }
            }
        } while (opcao != 0);
    }

    private void desativarLivro() {
        int id = menuService.lerInteiro("Digite o ID do livro a desativar: ");
        livroService.desativarLivro(id);
        menuService.exibirMensagem("Livro desativado com sucesso!");
    }

    private void ativarLivro() {
        int id = menuService.lerInteiro("Digite o ID do livro a ativar: ");
        livroService.ativarLivro(id);
        menuService.exibirMensagem("Livro ativado com sucesso!");
    }

    private void deletarLivro() {
        int id = menuService.lerInteiro("Digite o ID do livro a deletar: ");
        boolean sucesso = livroService.removerLivro(id);
        if (sucesso) {
            menuService.exibirMensagem("Livro deletado com sucesso!");
        } else {
            menuService.exibirErro("Livro com ID " + id + " não encontrado");
        }
    }

    private void exportarDados() {
        try {
            livroService.salvarDados();
            menuService.exibirMensagem("Dados exportados com sucesso!");
        } catch (Exception e) {
            menuService.exibirErro(e.getMessage());
        }
    }

    private void exibirResultadosBusca(List<Livro> livros) {
        if (livros.isEmpty()) {
            menuService.exibirMensagem("Nenhum livro encontrado.");
        } else {
            menuService.exibirMensagem("\n=== RESULTADOS DA BUSCA ===");
            for (Livro livro : livros) {
                menuService.exibirMensagem(livro.toString());
                menuService.exibirMensagem("---");
            }
            menuService.exibirMensagem("Total encontrado: " + livros.size());
        }
    }
}
