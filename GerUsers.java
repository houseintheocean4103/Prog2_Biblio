import java.util.ArrayList;
import java.util.Scanner;

public class GerUsers {
	
	ArrayList<User> usuarios = new ArrayList<>();

	Scanner scan = new Scanner(System.in);

	int numCadastros;

	// Função para imprimir linha em branco (estética e organização)
	public void pularLinha(){
		System.out.println();
	}

	// Função para limpar o buffer de entrada e quebrar a linha depois de uma entrada de número inteiro com scan.nextInt()
	public void limparBufferInt(){
		scan.nextLine();
	}
	
	// Função para cadastrar quantos usuários quiser
	public void cadastroUsuario() {

		System.out.println("================ Cadastro de usuários ================\n");
		System.out.print("Digite o número de usuários que deseja cadastrar: ");
		numCadastros = scan.nextInt();
		limparBufferInt();

		pularLinha();
		
		// Para cadastrar a quantidade de usuários determinada em numCadastros
		for(int i = 0; i < numCadastros; i++) {

			String nomeCompleto, cpf, email, telefone, endereco, dataNascimento;

			System.out.print("Nome completo: ");
			nomeCompleto = scan.nextLine();

			pularLinha();

			System.out.print("CPF: ");
			cpf = scan.nextLine();

			pularLinha();

			System.out.print("E-mail: ");
			email = scan.nextLine();

			pularLinha();

			System.out.print("Telefone: ");
			telefone = scan.nextLine();

			pularLinha();

			System.out.print("Endereço: ");
			endereco = scan.nextLine();

			pularLinha();

			System.out.print("Data de nascimento (digite apenas números): ");
			dataNascimento = scan.nextLine();

			pularLinha();

			System.out.println("------------------------------------------------------------------");

			pularLinha();

			User novoUsuario = new User(nomeCompleto, cpf, email, telefone, endereco, dataNascimento);
			this.usuarios.add(novoUsuario); // Adição de um novo usuário ao arraylist usuarios

		}

	}
	
	// Para editar os dados de um usuário
	public void edicaoUsuario() {

		System.out.println("================ Edição de dados ================\n");
		System.out.print("Digite o nome do usuário: ");
		String nomeBuscaUsuario = scan.nextLine();
		
		pularLinha();

		// Para precorrer a lista de usuários a fim de encontrar o usuário que se quer editar os dados
		for (User usuarioBuscado : usuarios){
			// Se o nome coincidir e o usuário estiver ativo no sistema, será possível fazer a edição
			if (usuarioBuscado.getNome().equals(nomeBuscaUsuario) && usuarioBuscado.isAtivo()){

				System.out.println("Opções para edição (escolha um número): \n"
					+ "[1] Nome\n"
					+ "[2] CPF\n"
					+ "[3] E-mail\n"
					+ "[4] Telefone\n"
					+ "[5] Endereço\n"
					+ "[6] Data de nascimento\n"
					+ "[0] Cancelar\n");

				System.out.print("Digite o número do dado que deseja editar: ");
				int opcao = scan.nextInt();
				limparBufferInt();

				pularLinha();

				switch (opcao) {
					// A mesma lógica de case 1 está presente nos outro cases
					case 1:
						System.out.println("#### Edição de nome ####");
						System.out.println("Nome atual: " + usuarioBuscado.getNome());
						System.out.print("Novo nome: ");
						String novoNome = scan.nextLine(); // Entrada para a alteração de nome
						usuarioBuscado.setNome(novoNome); // A alteração desejada pelo usuário é salva 
						break;

					case 2:
						System.out.println("#### Edição de CPF ####");
						System.out.println("CPF atual: " + usuarioBuscado.getCpf());
						System.out.print("Novo CPF: ");
						String novoCPF = scan.nextLine();
						usuarioBuscado.setCpf(novoCPF);
						break;

					case 3:
						System.out.println("#### Edição de E-mail ####");
						System.out.println("E-mail atual: " + usuarioBuscado.getEmail());
						System.out.print("Novo E-mail: ");
						String novoEmail = scan.nextLine();
						usuarioBuscado.setEmail(novoEmail);
						break;

					case 4:
						System.out.println("#### Edição de telefone ####");
						System.out.println("Telefone atual: " + usuarioBuscado.getTelefone());
						System.out.print("Novo telefone: ");
						String novoTelefone = scan.nextLine();
						usuarioBuscado.setTelefone(novoTelefone);
						break;

					case 5:
						System.out.println("#### Edição de endereço ####");
						System.out.println("Endereço atual: " + usuarioBuscado.getEndereco());
						System.out.print("Novo endereço: ");
						String novoEndereco = scan.nextLine();
						usuarioBuscado.setEndereco(novoEndereco);
						break;

					case 6:
						System.out.println("#### Edição de data de nascimento ####");
						System.out.println("Data de nascimento atual: " + usuarioBuscado.getdataNascimento());
						System.out.print("Nova data de nascimento: ");
						String novaDataNascimento = scan.nextLine();
						usuarioBuscado.setdataNascimento(novaDataNascimento);
						break;

					case 0:
						System.out.println("Operação cancelada");
						break;

					default:
						System.out.println("Entrada incorreta");
						
				}

				pularLinha();

			}

		}

	}

	// Função para listar os usuários cadastrados
	public void listarUsuarios(){

		int cont = 0; // Variável de contagem

		// Para contar quantos usuários do arraylist usuarios estão ativos no sistema
		for (User user : usuarios){
			if(user.isAtivo()){
				cont++;
			}
		}

		// Se houver 1 ou mais usuários ativos, os dados serão listados
		if (cont >= 1){

			System.out.println("================ Lista de usuários e seus dados ================");
			for (User user : usuarios){
				if(user.isAtivo()){
					System.out.println(user.toString());
				}
			}

		} 
		// Se não houver usuários para listar, a mensagem abaixo será impressa
		else{
			System.out.println("Não há usuários para listar");
			pularLinha();
		}

	}

	// Função para desativar um usuário
	public void desativarUsuario(){

		System.out.println("================ Desativação de usuário ================\n");
		System.out.print("Digite o nome do usuário: ");
		String usuarioADesativar = scan.nextLine();
		
		pularLinha();

		// Para precorrer a lista de usuários a fim de encontrar o usuário que se quer desativar
		for (User desatvUsuario : usuarios){
			// Se o nome concidir, a função fará uma verificação de status de ativação
			if (desatvUsuario.getNome().equals(usuarioADesativar)){

				// Se o usuário estiver ativo, haverá uma pergunta de confirmação a respeito da operação de desativação
				if (desatvUsuario.isAtivo()){

					System.out.println("### Confirmação ###");
					System.out.println("Escolha uma das opções: ");
					System.out.print("[1] Confirmar desativação de usuário \n[0] Cancelar operação\n");
					System.out.print("Digite o número da operação escolhida: ");
					int opcaoDesatv = scan.nextInt();
					limparBufferInt();
					pularLinha();

					// Se digitado 1, o usuário será desativado
					if (opcaoDesatv == 1){
						desatvUsuario.desativar();
						System.out.println("Usuário " + desatvUsuario.getNome() + " foi desativado no sistema");
						pularLinha();
					} 
					// Se digitado 0, o usuário não será desativado
					else {
						System.out.println("Operação cancelada");
						pularLinha();
					}

				} 
				// Se o usuário já estiver desativado, a mensagem abaixo será apresentada
				else{
					System.out.println("O usuário " + desatvUsuario.getNome() + " já está desativado");
					pularLinha();
				}

			} 
		}

	}

}
