package listasLibros;

import java.util.ArrayList;

public class ListaLibros {
	
	String NombreLista;
	ArrayList<Libro> libros = new ArrayList<Libro>(); 
	
	public ListaLibros(String NombreLista) { //Constructor
		this.NombreLista = NombreLista;
	}
	
	public String getNombreLista() {
		return NombreLista;
	}

	public void setNombreLista(String NombreLista) {
		this.NombreLista = NombreLista;
	}

	public void addLibro(Libro i){//No avisa si ya existe el libro
		if (!existe(i))
			libros.add(i);
	}

	public Libro getLibroPorId(String id){
		Libro lib = null;
		for (int i=0; i<libros.size(); i++) { 
			lib = libros.get(i);
			if (lib.getId()==id)
				i=libros.size();
		}
		return lib;
	}

	private boolean existe(Libro lib) {
		boolean ex = false;
		Libro aux = null;
		if (!libros.isEmpty())
			for (int i=0; i<libros.size(); i++) { 
				aux = libros.get(i); 
				 	if (aux.getId()==lib.getId())
				 		ex = true;
				}
		return ex;
	}
	
	public void borraLibroPorId(String id){
		Libro lib = null;
		for (int i=0; i<libros.size(); i++) { 
			 lib = libros.get(i); 
			 	if (lib.getId()==id)
			 		libros.remove(lib);
			}
	}
	
	public ArrayList<Libro> getListaLibros() {
		return libros;
	}
	
	public int tamanyo(){
		return libros.size();
	}
	
	public Libro getLibroPorIndice(int i){
		return libros.get(i);
	}
}


