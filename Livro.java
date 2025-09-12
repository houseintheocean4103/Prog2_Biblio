
public class Livro {
	
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
	
	public boolean isAtivo() {
		return ativo;
	}
	
	public void ativar() {
		this.ativo = true;
	}
	
	public void desativar() {
		this.ativo = false;
	}
	
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
