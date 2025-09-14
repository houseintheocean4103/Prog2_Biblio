package core;

public class User {
	
	public String nome;
	public String cpf;
	public String email;
	public String telefone;
	public String endereco;
	public String dataNascimento;
	private boolean ativo = true;
	
	public User(String nome, String cpf, String email, String telefone, String endereco, String dataNascimento){
		this.nome = nome;
		this.cpf = cpf;
		this.email = email;
		this.telefone = telefone;
		this.endereco = endereco;
		this.dataNascimento = dataNascimento;
	}
	
	public boolean isAtivo() {
		return ativo;
	}
	
	public void ativar() {
		this.ativo = true;
	}
	
	public void desativar() {
		this.ativo = false;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getdataNascimento() {
		return dataNascimento;
	}

	public void setdataNascimento(String data) {
		this.dataNascimento = data;
	}

	@Override
	public String toString(){
		return "- Nome: " + nome + "\n" +
		"- CPF: " + cpf + "\n" +
		"- E-mail: " + email + "\n" + 
		"- Telefone: " + telefone + "\n" + 
		"- Endereço: " + endereco + "\n" + 
		"- Data de nascimento: " + dataNascimento + "\n";

	}

}
