package facade;

import core.GerenteLivro;
import core.GerenteLocal;
import core.GerenteDevolucao;
import core.GerenteUser;

import java.util.Scanner;

public class MenuFacade {
	
	GerenteUser gUsers;     
	GerenteLocal gLocal;
	GerenteDevolucao gDev;
	GerenteLivro gLivros;
	
	public MenuFacade(){
		
		gUsers = new GerenteUser();     //Inicialização dos gerenciadores
		gLocal = new GerenteLocal();
		gDev = new GerenteDevolucao();
		gLivros = new GerenteLivro();
		
	};

	public void menu() {
		
		int opcao;
		Scanner scan = new Scanner(System.in);
		
        do {
            // Exibe as opções do menu
            System.out.println("\n=== GERENCIADOR DE BIBLIOTECA ===");
            System.out.println("1. Gerenciamento de Livros");
            System.out.println("2. Gerenciamento de Usuários");
            System.out.println("3. Gerenciamento de Empréstimos e Devoluções");
            System.out.println("4. Gerenciamento de Localização e Armazenamento");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            
            // Lê a opção do usuário
            opcao = scan.nextInt();
            scan.nextLine(); // Consome a quebra de linha
            
            // Processa a opção selecionada
            switch (opcao) {
                case 1:
                    gLivros.iniciar();
                    break;
                case 2:
                	gUsers.menu();
                    break;
                case 3:
                	gDev.menu();
                	break;
                case 4:
                	gLocal.menu();
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
        
        scan.close();
    }	    
}
