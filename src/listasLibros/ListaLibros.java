package listasLibros;

import java.util.ArrayList;

public class ListaLibros {
	
	String s_NombreLista;
	ArrayList<Libro> libros = new ArrayList<Libro>(); 
	
	public ListaLibros(String NombreLista) { //Constructor
		s_NombreLista = NombreLista;
	}
	
	public String getNombreLista() {
		return s_NombreLista;
	}

	public void setNombreLista(String NombreLista) {
		this.s_NombreLista = NombreLista;
	}

	public void addLibro(Libro i){//No avisa si ya existe el libro
		if (!existe(i))
			libros.add(i);
	}

	public Libro getLibroPorId(String id){//id==isbn?
		Libro lib = null;
		for (int i=0; i<libros.size(); i++) { 
			lib = libros.get(i);
			if (lib.getIsbn()==id)//id==isbn?
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
				 	if (aux.getIsbn()==lib.getIsbn())
				 		ex = true;
				}
		return ex;
	}
	
	public void borraLibroPorIsbn(String isbn){//id==isbn?
		Libro lib = null;
		for (int i=0; i<libros.size(); i++) { 
			 lib = libros.get(i); 
			 	if (lib.getIsbn()==isbn)//id==isbn?
			 		libros.remove(lib);
			}
	}
	
	public ArrayList<Libro> getListaLibros() {
		return libros;
	}
	
}


