package core;

public class Livro {
	
	//Getters e Setters
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}

	public void setEditora(String editora) {
		this.editora = editora;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public void setSinopse(String sinopse) {
		this.sinopse = sinopse;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public String getIsbn() {
		return isbn;
	}

	public String getTitulo() {
		return titulo;
	}

	public String getAutor() {
		return autor;
	}

	public String getAno() {
		return ano;
	}

	public String getEditora() {
		return editora;
	}

	public String getGenero() {
		return genero;
	}

	public String getSinopse() {
		return sinopse;
	}

	public String getLocal() {
		return local;
	}

	public int getId() {
		return id;
	}
	
	//Atributos
	public String titulo;
	public String isbn;
	public String autor;
	public String ano;
	public String editora;
	public String genero;
	public String sinopse;
	public String local;
	public int id;
	private boolean ativo = true;
	
	//Construtor
	public Livro(String a, String b, String c, String d, String e, String f, String g, String h, int i){
		this.titulo = a;
		this.isbn = b;
		this.autor = c;
		this.ano = d;
		this.editora = e;
		this.genero = f;
		this.sinopse = g;
		this.local = h;
		this.id = i; 
	}
	
	//Retorna o status
	public boolean isAtivo() {
		return ativo;
	}
	
	//Alteram o status
	public void ativar() {
		this.ativo = true;
	}
	
	public void desativar() {
		this.ativo = false;
	}
	
	//Modificam a formatação do objeto
	@Override
    public String toString() {
        return String.format("(\"ID: %d,"
        		+ "Título: %s, "
        		+ "ISBN: %s, "
        		+ "Autor: %s,"
        		+ "Ano: %s, "
        		+ "Editora: %s, "
        		+ "Gênero: %s, "
        		+ "Sinopse: %s, "
        		+ "Localização: %s, "
        		+ "Ativo: %b",
        		id, titulo, isbn, autor, ano, editora, genero, sinopse, local, this.isAtivo());
    }
}
