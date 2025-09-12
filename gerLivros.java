import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Scanner;

public class gerLivros {

    Scanner scan = new Scanner(System.in);
    ArrayList<Livro> livros = new ArrayList<>();
    static int dist_id = 0;

    public gerLivros() {}
    
    private void list(ArrayList<Livro> a) {
    	
    	for(Livro l : a) {
    		System.out.println(l.toString());
    	}
    }

    public void listar() {
    	
    	int opcao;
    	
    	do {
    	
        System.out.println("\nQual filtro deseja utilizar ?\n"
                + "[1] todos\n"
                + "[2] ativos\n"
                + "[3] inativos\n"
                + "[4] autoria\n"
                + "[5] editora\n"
                + "[6] gênero\n"
                + "[7] localização\n"
                + "[8] emprestados\n"
                + "[0] sair\n");

        opcao = scan.nextInt();
        scan.nextLine(); //Consome ENTER

        String busca = null;

        if (opcao == 1) {
            list(livros);
        } else if (opcao == 0){
        	System.out.println("Saindo da listagem...");
        } else {
        	
        if (opcao == 4) {
            System.out.println("Qual o(a) autor(a) ?");
            busca = scan.nextLine();
        } else if (opcao == 5) {
            System.out.println("Qual a editora ?");
            busca = scan.nextLine();
        } else if (opcao == 6) {
            System.out.println("Qual o gênero ?");
            busca = scan.nextLine();
        } else if (opcao == 7) {
            System.out.println("Qual a localização ?");
            busca = scan.nextLine();
        }

        ArrayList<Livro> saida = new ArrayList<>();

        for (Livro i : livros) {
            switch (opcao) {
                case 2:
                    if (i.isAtivo()) saida.add(i);
                    break;
                case 3:
                    if (!i.isAtivo()) saida.add(i);
                    break;
                case 4:
                    if (i.autor.equals(busca)) saida.add(i);
                    break;
                case 5:
                    if (i.editora.equals(busca)) saida.add(i);
                    break;
                case 6:
                    if (i.genero.equals(busca)) saida.add(i);
                    break;
                case 7:
                    if (i.local.equals(busca)) saida.add(i);
                    break;
                default:
                    System.out.println("Opção inválida, tente novamente.");
                    break;
            }
        }
        list(saida);
        } 
        
    	}while(opcao != 0);
    }


    public void cadastrar() {
    	
        System.out.println("Quantos livros deseja cadastrar ?");
        
        int a = scan.nextInt();
        scan.nextLine();

        for (int x = 0; x < a; x++) {
            System.out.println("Digite o nome do livro:");
            String b = scan.nextLine();

            System.out.println("Digite o ISBN do livro:");
            String c = scan.nextLine();           

            System.out.println("Digite o(a) autor(a) do livro:");
            String d = scan.nextLine();

            System.out.println("Digite o ano de publicação do livro:");
            String e = scan.nextLine();            

            System.out.println("Digite o nome da editora do livro:");
            String f = scan.nextLine();

            System.out.println("Digite o gênero do livro:");
            String g = scan.nextLine();

            System.out.println("Digite a sinopse do livro:");
            String h = scan.nextLine();

            System.out.println("Digite a localização do livro:");
            String i = scan.nextLine();

            Livro novo = new Livro(b, c, d, e, f, g, h, i, dist_id);
            this.livros.add(novo);

            System.out.printf("O livro foi cadastrado com ID: %d.\n", dist_id);
            dist_id++;

            System.out.println("Deseja adicionar cópias ?\n"
                    + "[0] Sim\n"
                    + "[1] Não\n");

            String j = scan.nextLine();

            if (j.equals("0")) {
                copiar(novo);
            }
        }
    }

    public void copiar(Livro original) {
    	
        System.out.println("Quantas cópias ?");
        int a = scan.nextInt();
        scan.nextLine();

        for (int i = 0; i < a; i++) {
            Livro novo = new Livro(original.titulo,
                    original.isbn,
                    original.autor,
                    original.ano,
                    original.editora,
                    original.genero,
                    original.sinopse,
                    original.local,
                    dist_id);

            this.livros.add(novo);
            System.out.printf("A cópia foi cadastrada com ID: %d.\n", dist_id);
            dist_id++;
        }

    }

    public Livro buscar() {
    	
    	int opcao;
    	
    	do {
    	
        System.out.println("\nQual o parâmetro de busca ?\n"
                + "[1] titulo\n"
                + "[2] ISBN\n"
                + "[3] sinopse\n"
                + "[4] id\n"
                + "[0] sair\n");

        opcao = scan.nextInt();
        scan.nextLine();

        Livro resultado = null;

        switch (opcao) {
            case 1:
                System.out.println("Qual o título ?");
                String titulo = scan.nextLine();
                for (Livro i : this.livros) {
                    if (i.titulo.equals(titulo)) { 
                    	resultado = i;
                    	break;
                    }
                }
                
                break;
            case 2:
                System.out.println("Qual o ISBN?");
                String isbnBusca = scan.nextLine();
                for (Livro i : this.livros) {
                    if (i.isbn.equals(isbnBusca)) {
                    	resultado = i;
                		break;
                	}
            	}
                break;
            case 3:
                System.out.println("Qual a sinopse ?");
                String sinopse = scan.nextLine();
                for (Livro i : this.livros) {
                    if (i.sinopse.equals(sinopse)) {
                    	resultado = i;
                		break;
                	}
            	}
                break;
            case 4:
                System.out.println("Qual a ID ?");
                int idBusca = scan.nextInt();
                for (Livro i : this.livros) {
                    if (i.id == idBusca) {
                    	resultado = i;
                		break;
                	}
            	}
                break;
            case 0:
                System.out.println("Saindo da busca...");
                break;
            default:
                System.out.println("Opção inválida, tente novamente.");
                break;
        }
        
        if(resultado == null) {
        	System.out.println("O livro não foi achado.\n");
        } else {
        	System.out.println(resultado.toString());
        }
        return resultado;

    	} while (opcao != 0);
    }
    
    public void deletar(Livro del) {
		
		Livro x = null;
		
		for(Livro e : livros) {
			if(e == del){
				livros.remove(e);
				break;
			}
		}				
	}

    public void editar(Livro fonte) {
    	
        int opcao;
        
        do {
            System.out.println("\nQual dado deseja alterar ?\n"
                    + "[1] título\n"
                    + "[2] ISBN\n"
                    + "[3] autor(a)\n"
                    + "[4] ano\n"
                    + "[5] editora\n"
                    + "[6] gênero\n"
                    + "[7] sinopse\n"
                    + "[8] localização\n"
                    + "[0] sair\n");

            opcao = scan.nextInt();
            scan.nextLine();

            switch (opcao) {
                case 1:
                    System.out.println("Novo título: ");
                    fonte.titulo = scan.nextLine();
                    break;
                case 2:
                    System.out.println("Novo ISBN: ");
                    fonte.isbn = scan.nextLine();
                    scan.nextLine();
                    break;
                case 3:
                    System.out.println("Novo(a) autor(a): ");
                    fonte.autor = scan.nextLine();
                    break;
                case 4:
                    System.out.println("Novo ano de publicação: ");
                    fonte.ano = scan.nextLine();
                    scan.nextLine();
                    break;
                case 5:
                    System.out.println("Nova editora: ");
                    fonte.editora = scan.nextLine();
                    break;
                case 6:
                    System.out.println("Novo gênero: ");
                    fonte.genero = scan.nextLine();
                    break;
                case 7:
                    System.out.println("Nova sinopse: ");
                    fonte.sinopse = scan.nextLine();
                    break;
                case 8:
                    System.out.println("Nova localização: ");
                    fonte.local = scan.nextLine();
                    break;
                case 0:
                    System.out.println("Saindo da edição...");
                    break;
                default:
                    System.out.println("Opção inválida, tente novamente.");
                    break;
            }
        } while (opcao != 0);
    }

    public void menu() {
    	
    	int opcao;
    	
    	this.importarCSV("livros.csv");
    	
    	do {
        System.out.println("\nQual serviço deseja realizar ?\n"
                + "[1] busca\n"
                + "[2] listagem\n"
                + "[3] cadastro\n"
                + "[4] cópia\n"
                + "[5] edição\n"
                + "[6] desativar\n"
                + "[7] ativar\n"
                + "[8] deletar\n"
                + "[0] sair\n");

        opcao = scan.nextInt();
        scan.nextLine();
        
        if(livros.isEmpty() & opcao != 3) {
        	System.out.println("Não há livros cadastrados.");
        } else {

        switch (opcao) {
            case 1:
                this.buscar();
                break;
            case 2:
                this.listar();
                break;
            case 3:
                this.cadastrar();
                break;
            case 4:
                this.copiar(this.buscar());
                break;
            case 5:
                this.editar(this.buscar());
                break;
            case 6:
                this.buscar().desativar();
                break;
            case 7:
                this.buscar().ativar();
                break;
            case 8:
                this.deletar(this.buscar());
                break;
        }
        }
    	} while (opcao != 0);
    }
    
    public void importarCSV(String caminhoArquivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                // Ignora linha vazia
                if (linha.trim().isEmpty()) continue;

                // Divide pelos separadores (ajuste se usar ; ou outro)
                String[] campos = linha.split(",");

                // Espera-se que o CSV tenha: titulo, isbn, autor, ano, editora, genero, sinopse, local
                String titulo = campos[0].trim();
                String isbn = (campos[1].trim());
                String autor = campos[2].trim();
                String ano = (campos[3].trim());
                String editora = campos[4].trim();
                String genero = campos[5].trim();
                String sinopse = campos[6].trim();
                String local = campos[7].trim();

                // Cria o livro e adiciona à lista
                Livro novo = new Livro(titulo, isbn, autor, ano, editora, genero, sinopse, local, dist_id);
                livros.add(novo);
                dist_id++;
            }
            System.out.println("Importação concluída! " + livros.size() + " livros carregados.");
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Erro de formatação numérica no arquivo CSV: " + e.getMessage());
        }
    }
}
