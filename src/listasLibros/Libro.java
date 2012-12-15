package listasLibros;

public class Libro {
	int i_isbn;
	String s_titulo;
	String s_autor;
	//Habria que ver como meter la foto, ruta de algo?
	String s_editorial;
	String s_descripcion;
	String s_fechaPublicacion; //Se podría unsar formato Date o jCalendar o similares
	int n_numeroDePaginas;
	Float f_puntuacion;
	//Añadir más campos dependerá de como es la api que usemos

	///// MARIO
	//Habrá que hacer un constructor que cargue todo lo posible desde la api
	
	public int getIsbn() {
		return i_isbn;
	}

	public String getTitulo() {
		return s_titulo;
	}

	public String getAutor() {
		return s_autor;
	}

	public String getEditorial() {
		return s_editorial;
	}

	public String getDescripcion() {
		return s_descripcion;
	}

	public String getFechaPublicacion() {
		return s_fechaPublicacion;
	}

	public int getNumeroDePaginas() {
		return n_numeroDePaginas;
	}

	public Float getPuntuacion() {
		return f_puntuacion;
	}

}
