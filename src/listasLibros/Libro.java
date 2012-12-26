package listasLibros;

import java.util.ArrayList;

import zzzDuplicados.VolumeInfo.ImageLinks;

public class Libro {
	String id;
	String isbn;
	String titulo;
	ArrayList<String> autores;
	String editorial;
	String descripcion;
	String fechaPublicacion;
	int numeroDePaginas;
	ArrayList<String> categorias;
	Float puntuacionMedia;
	String pais;
	String idioma;
	ImageLinks imageLinks;
	

	public Libro(String id, String isbn, String titulo,
			ArrayList<String> autores, String editorial, String descripcion,
			String fechaPublicacion, int numeroDePaginas,
			ArrayList<String> categorias, Float puntuacionMedia, String pais,
			String idioma, ImageLinks imageLinks) {
		super();
		
		this.id = id;
		this.isbn = isbn;
		this.titulo = titulo;
		this.autores = autores;
		this.editorial = editorial;
		this.descripcion = descripcion;
		this.fechaPublicacion = fechaPublicacion;
		this.numeroDePaginas = numeroDePaginas;
		this.categorias = categorias;
		this.puntuacionMedia = puntuacionMedia;
		this.pais = pais;
		this.idioma = idioma;
		this.imageLinks = imageLinks;
	}
	
	public Libro(String isbn){
		this.id=isbn;
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public ArrayList<String> getAutores() {
		return autores;
	}

	public void setAutores(ArrayList<String> autores) {
		this.autores = autores;
	}

	public String getEditorial() {
		return editorial;
	}

	public void setEditorial(String editorial) {
		this.editorial = editorial;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getFechaPublicacion() {
		return fechaPublicacion;
	}

	public void setFechaPublicacion(String fechaPublicacion) {
		this.fechaPublicacion = fechaPublicacion;
	}

	public int getNumeroDePaginas() {
		return numeroDePaginas;
	}

	public void setNumeroDePaginas(int numeroDePaginas) {
		this.numeroDePaginas = numeroDePaginas;
	}

	public ArrayList<String> getCategorias() {
		return categorias;
	}

	public void setCategorias(ArrayList<String> categorias) {
		this.categorias = categorias;
	}

	public Float getPuntuacionMedia() {
		return puntuacionMedia;
	}

	public void setPuntuacionMedia(Float puntuacionMedia) {
		this.puntuacionMedia = puntuacionMedia;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getIdioma() {
		return idioma;
	}

	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}

	public ImageLinks getImageLinks() {
		return imageLinks;
	}

	public void setImageLinks(ImageLinks imageLinks) {
		this.imageLinks = imageLinks;
	}

}