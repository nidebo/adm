package listasLibros;

import java.util.ArrayList;

import librosGoogle.VolumeInfo.ImageLinks;


public class Libro {
	String id;
	//String isbn;
	String title;
	ArrayList<String> authors;
	String publisher;
	String description;
	String publishedDate;
	int pageCount;
	String mainCategory;
	ArrayList<String> categories;
	Float averageRating;
	String country;
	String language;
	ImageLinks imageLinks;
	

	public Libro(String id, String isbn, String titulo,
			ArrayList<String> autores, String editorial, String descripcion,
			String fechaPublicacion, int numeroDePaginas, String categoriaPrincipal,
			ArrayList<String> categorias, Float puntuacionMedia, String pais,
			String idioma, ImageLinks imageLinks) {
		super();
		
		this.id=id;
		//this.isbn = isbn;
		this.title = titulo;
		this.authors = autores;
		this.publisher = editorial;
		this.description = descripcion;
		this.publishedDate = fechaPublicacion;
		this.pageCount = numeroDePaginas;
		this.mainCategory=categoriaPrincipal;
		this.categories = categorias;
		this.averageRating = puntuacionMedia;
		this.country = pais;
		this.language = idioma;
		this.imageLinks = imageLinks;
	}
	/*
	public Libro(String isbn){
		this.isbn=isbn;
	}*/
	
	public Libro(){
	}

	public String getId() {
		return id;
	}
	/*
	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
*/
	public String getTitulo() {
		return title;
	}

	public void setTitulo(String titulo) {
		this.title = titulo;
	}

	public ArrayList<String> getAutores() {
		return authors;
	}

	public void setAutores(ArrayList<String> autores) {
		this.authors = autores;
	}

	public String getEditorial() {
		return publisher;
	}

	public void setEditorial(String editorial) {
		this.publisher = editorial;
	}

	public String getDescripcion() {
		return description;
	}

	public void setDescripcion(String descripcion) {
		this.description = descripcion;
	}

	public String getFechaPublicacion() {
		return publishedDate;
	}

	public void setFechaPublicacion(String fechaPublicacion) {
		this.publishedDate = fechaPublicacion;
	}

	public int getNumeroDePaginas() {
		return pageCount;
	}

	public void setNumeroDePaginas(int numeroDePaginas) {
		this.pageCount = numeroDePaginas;
	}

	public String getCategoriaPrincipal() {
		return mainCategory;
	}

	public void setCategoriaPrincipal(String categoriaPrincipal) {
		this.mainCategory = categoriaPrincipal;
	}

	
	public ArrayList<String> getCategorias() {
		return categories;
	}

	public void setCategorias(ArrayList<String> categorias) {
		this.categories = categorias;
	}

	public Float getPuntuacionMedia() {
		return averageRating;
	}

	public void setPuntuacionMedia(Float puntuacionMedia) {
		this.averageRating = puntuacionMedia;
	}

	public String getPais() {
		return country;
	}

	public void setPais(String pais) {
		this.country = pais;
	}

	public String getIdioma() {
		return language;
	}

	public void setIdioma(String idioma) {
		this.language = idioma;
	}

	public ImageLinks getImageLinks() {
		return imageLinks;
	}

	public void setImageLinks(ImageLinks imageLinks) {
		this.imageLinks = imageLinks;
	}

}