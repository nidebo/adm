package listasLibros;

import internet.ListaServer;

import java.util.ArrayList;

import apiGoogle.InterfazAPI;


public class GestorListas {
	ArrayList<ListaLibros> lista = new ArrayList<ListaLibros>(); 
	InterfazAPI api=new InterfazAPI();
	ListaServer servidor=new ListaServer();
	String usuarioActual;
	
	public GestorListas(String nombreDeUsuario) { //Constructor
		usuarioActual=nombreDeUsuario;
	}
	
	public void addListaVacia(String nombre){//No avisa si ya existe
		if(!existe(nombre)){
		ListaLibros lis = new ListaLibros(nombre);
		lista.add(lis);
		
		//Queda comprobar, si es posible, si ha sido bien agregada o no
		servidor.creaListaDeUsuario(nombre, usuarioActual);
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
		//Queda comprobar, si es posible, si ha sido bien agregada o no
		servidor.borraListaDeUsuario(nombreLista, usuarioActual);
	}
	
	private boolean existe(String nombreLista) {//Sin servidor
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
	public ArrayList<Libro> getListaDeLibros(String nombreLista){//Sin servidor
		if (lista==null){
			ArrayList<String> ids=new ArrayList<String>();
			ids=servidor.obtenerLibrosLista(nombreLista, usuarioActual);
			for(int i=0;i<ids.size();i++){
				//lista.add(object)
			}
		}
		ListaLibros lis = null;
		for (int i=0; i<lista.size(); i++) { 
			lis = lista.get(i);
			if (lis.getNombreLista()==nombreLista)
				i=lista.size();
		}
		return lis.getListaLibros();
	}
	
	public void borraLibroDeLista(String isbn, String nombreLista){//id==isbn?
		ListaLibros lis = null;
		for (int i=0; i<lista.size(); i++) { 
			lis = lista.get(i);
			if (lis.getNombreLista()==nombreLista)
				i=lista.size();
		}
		lis.borraLibroPorIsbn(isbn);//id==isbn?
		//Queda comprobar, si es posible, si ha sido bien agregada o no
		servidor.borraLibroDeLista(nombreLista, usuarioActual, isbn);
	}
	
	public void addLibroEnLista(String isbn, String nombreLista){//id==isbn?
		ListaLibros lis = null;
		for (int i=0; i<lista.size(); i++) { 
			lis = lista.get(i);
			if (lis.getNombreLista()==nombreLista)
				i=lista.size();
		}
		
		
		//añadir cosas al constructor en la clase libro!!!!
		Libro lib=new Libro(isbn);//idid==isbn?
		//añadir cosas al constructor en la clase libro
		
		lis.addLibro(lib);
		//Queda comprobar, si es posible, si ha sido bien agregada o no
		servidor.borraLibroDeLista(nombreLista, usuarioActual, isbn);
	}
	
	//If null, sacarlo del servidor
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
	 * 	bï¿½sicamente es tratar como diferentes guardar y borrar lista
	 * 	asi se podrï¿½a borrar cuando te de la gana y guardar... pues igual
	 *  el problema de esto es que es muy manual y habrï¿½a que tener siempre
	 *  conexion a la red.
	 *  
	 *  Por otra parte, cosa que yo creo que es mejor idea pero no se si es posible:
	 *  Unir las dos funciones guardar y borrar listas para que ï¿½nicamente
	 *  se llame cuando se vaya a cerrar la aplicaciï¿½n. Habria que detectar que hayan
	 *  o no habido cambios (esto es sencillo, con un flag), y serï¿½a una funcion
	 *  actualizar que ya llamase a guardar o borrar libros de ListaLibros, que serï¿½a
	 *  desde donde se accede al servidor.
	 *  La funcion esta tendrï¿½a que ser llamada al cerrar la aplicaciï¿½n (de aqui el no
	 *  sabes si esto es posible de hacer) cuando se hayan detectado cambios y haya
	 *  conexiï¿½n a internet (cada x tiempo).
	 * 
	public void guardaLista(String nombreLista, String uname){//Necesitarï¿½ nombre de usuario
		ListaLibros lis = null;
		for (int i=0; i<lista.size(); i++) { 
			lis = lista.get(i);
			if (lis.getNombreLista()==nombreLista)
				i=lista.size();
		}
		lis.guardaListaLibros(nombreLista,uname);//Necesitarï¿½ nombre de usuario
	}
	
	
	public void cargaListaLibros(){
		//No harï¿½a falta identificador, se supone que esto se harï¿½a al iniciar
		//la aplicaciï¿½n

	}
	
	public void borraListaLibros(){
		//las dos opciones anteriores

	}*/
	
}
