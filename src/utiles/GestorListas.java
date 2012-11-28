package utiles;

import java.util.ArrayList;

public class GestorListas {
	ArrayList<ListaLibros> lista = new ArrayList<ListaLibros>(); 
	
	public GestorListas() { //Constructor
		
	}
	
	public void addListaVacia(String nombre){//No avisa si ya existe
		if(!existe(nombre)){
		ListaLibros lis = new ListaLibros(nombre);
		lista.add(lis);
		}
	}
	
	public void BorraLista(String nombreLista){
		ListaLibros lis = null;
		for (int i=0; i<lista.size(); i++) { 
			lis = lista.get(i);
			if (lis.getNombreLista()==nombreLista)
				i=lista.size();
		}
		lista.remove(lis);
	}
	
	private boolean existe(String nombreLista) {
		boolean ex = false;
		ListaLibros aux = null;
		if (!lista.isEmpty())
			for (int i=0; i<lista.size(); i++) { 
				aux = lista.get(i); 
				 	if (aux.getNombreLista()==nombreLista)
				 		ex = true;
				}
		return ex;
	}
	
	//Devuelve todos los libros de una lista
	public ArrayList<Libro> getListaDeLibros(String nombreLista){
		ListaLibros lis = null;
		for (int i=0; i<lista.size(); i++) { 
			lis = lista.get(i);
			if (lis.getNombreLista()==nombreLista)
				i=lista.size();
		}
		return lis.getListaLibros();
	}
	
	public void borraLibroDeLista(int isbn, String nombreLista){
		ListaLibros lis = null;
		for (int i=0; i<lista.size(); i++) { 
			lis = lista.get(i);
			if (lis.getNombreLista()==nombreLista)
				i=lista.size();
		}
		lis.borraLibroPorIsbn(isbn);
	}
	
	public void addLibroEnLista(Libro lib, String nombreLista){
		ListaLibros lis = null;
		for (int i=0; i<lista.size(); i++) { 
			lis = lista.get(i);
			if (lis.getNombreLista()==nombreLista)
				i=lista.size();
		}
		lis.addLibro(lib);
	}
	
	//Devuelve un array con los nombres de las listas
	public ArrayList<String> getNombresListas() {
		ArrayList<String> nombres = new ArrayList<String>(); 
		ListaLibros lis = null;
		for (int i=0; i<lista.size(); i++) { 
			lis = lista.get(i);
			nombres.add(lis.getNombreLista());
		}
		return nombres;
	}
	
	//////////////////////////////////
	///SERVIDOR
	///////////////////////
	
	/*
	 * Dos opciones, por una parte, la de las estructuras que he dejado aqui:
	 * 	básicamente es tratar como diferentes guardar y borrar lista
	 * 	asi se podría borrar cuando te de la gana y guardar... pues igual
	 *  el problema de esto es que es muy manual y habría que tener siempre
	 *  conexion a la red.
	 *  
	 *  Por otra parte, cosa que yo creo que es mejor idea pero no se si es posible:
	 *  Unir las dos funciones guardar y borrar listas para que únicamente
	 *  se llame cuando se vaya a cerrar la aplicación. Habria que detectar que hayan
	 *  o no habido cambios (esto es sencillo, con un flag), y sería una funcion
	 *  actualizar que ya llamase a guardar o borrar libros de ListaLibros, que sería
	 *  desde donde se accede al servidor.
	 *  La funcion esta tendría que ser llamada al cerrar la aplicación (de aqui el no
	 *  sabes si esto es posible de hacer) cuando se hayan detectado cambios y haya
	 *  conexión a internet (cada x tiempo).
	 * */
	public void guardaLista(String nombreLista){//Necesitará nombre de usuario
		ListaLibros lis = null;
		for (int i=0; i<lista.size(); i++) { 
			lis = lista.get(i);
			if (lis.getNombreLista()==nombreLista)
				i=lista.size();
		}
		lis.guardaListaLibros();//Necesitará nombre de usuario
	}
	
	
	public void cargaListaLibros(){
		//No haría falta identificador, se supone que esto se haría al iniciar
		//la aplicación

	}
	
	public void borraListaLibros(){
		//las dos opciones anteriores

	}
	
}
