package listasLibros;

public class ParLibroUsuario {
	String usuario;
	String isbn;
	
	public ParLibroUsuario (String usuario, String isbn){
		this.usuario=usuario;
		this.isbn=isbn;
	}
	
	public String getUsuario() {
		return usuario;
	}

	public String getIsbn() {
		return isbn;
	}
	
}
