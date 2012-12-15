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

	public Libro getLibroPorId(int id){
		Libro lib = null;
		for (int i=0; i<libros.size(); i++) { 
			lib = libros.get(i);
			if (lib.getIsbn()==id)
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
	
	public void borraLibroPorIsbn(int isbn){
		Libro lib = null;
		for (int i=0; i<libros.size(); i++) { 
			 lib = libros.get(i); 
			 	if (lib.getIsbn()==isbn)
			 		libros.remove(lib);
			}
	}
	
	public ArrayList<Libro> getListaLibros() {
		return libros;
	}
	
	//Funciones relativas al servidor
	public void guardaListaLibros(){
		//Nico haz algo aqui para meter la lista en el servidor
		//La funcion deberá recibir el nombre o id del usuario, supongo y usar s_NombreLista
		
		//Habría que comprobar si existe la lista y modificarla, y sino, crearla
	}
	
	
	public void cargaListaLibros(){
		//Como lo anterior

	}
	
	public void borraListaLibros(){
		//Como lo anterior

	}
}
