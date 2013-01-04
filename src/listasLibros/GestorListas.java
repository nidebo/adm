package listasLibros;

import internet.ListaServer;

import java.util.ArrayList;

import apiGoogle.InterfazAPI;

//Hay que hacer un flag que nos indique si
//estamos trabajando con datos buenos, con incertidumbre, o con mierda
public class GestorListas {
	ArrayList<ListaLibros> lista = new ArrayList<ListaLibros>(); 
	InterfazAPI api=new InterfazAPI();
	ListaServer servidor=new ListaServer();
	String usuarioActual;
	
	public GestorListas(String nombreDeUsuario) { //Constructor
		usuarioActual=nombreDeUsuario;
		ActualizarTodoDeServidor();//Actualizar de local, y COMPROBAR (nueva funcion) de servidor
		//Si no est� en local, solo de servidor
		
	}
	
	private void ActualizarTodoDeServidor(){
		ArrayList<String> listaNombresListas;
		listaNombresListas=servidor.obtenerListas(usuarioActual);
		for(int i=0;i<listaNombresListas.size();i++){//Cargamos nombres listas
			addListaVacia(listaNombresListas.get(i));
			ArrayList<String> listaLibros;
			listaLibros=servidor.obtenerLibrosLista(listaNombresListas.get(i), usuarioActual);
			for(int j=0;j<listaLibros.size();j++){//Cargamos libros en listas
				Libro lib=api.ObtenerLibroPorIsbn(listaLibros.get(j));
				addLibroEnLista(lib,listaNombresListas.get(i));
			}
		}
	}
	public ArrayList<Libro> getListaAll(){
		ArrayList<Libro> all= new ArrayList<Libro>();
		for (int i=0;i<lista.size();i++){
			for (int j=0;j<lista.get(i).tamanyo();j++){
				all.add(lista.get(i).getLibroPorIndice(j));
			}
		}
		return all;
	}
	
	//Si el nombre de la lista ya existe o hay problemas con el servidor, devuelve false
	public Boolean addListaVacia(String nombre){
		Boolean correcto=false;
		if(!existe(nombre)){
			ListaLibros lis = new ListaLibros(nombre);
			correcto=servidor.creaListaDeUsuario(nombre, usuarioActual);
			lista.add(lis);
		}
		return correcto;
	}
	
	public Boolean BorraLista(String nombreLista){
		Boolean correcto=false;
		ListaLibros lis = null;
		for (int i=0; i<lista.size(); i++) { 
			lis = lista.get(i);
			if (lis.getNombreLista()==nombreLista)
				i=lista.size();
		}
		//Queda comprobar, si es posible, si ha sido bien agregada o no
		correcto = servidor.borraListaDeUsuario(nombreLista, usuarioActual);
		if (correcto)
			lista.remove(lis);
		return correcto;
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
		//Nos fiamos de que la lista est� correctamente actualizada del servidor
		ListaLibros lis = null;
		for (int i=0; i<lista.size(); i++) { 
			lis = lista.get(i);
			if (lis.getNombreLista()==nombreLista)
				i=lista.size();
		}
		return lis.getListaLibros();
	}
	
	public Boolean borraLibroDeLista(String isbn, String nombreLista){//id==isbn?
		Boolean correcto=false;
		ListaLibros lis = null;
		for (int i=0; i<lista.size(); i++) { 
			lis = lista.get(i);
			if (lis.getNombreLista()==nombreLista)
				i=lista.size();
		}

		correcto = servidor.borraLibroDeLista(nombreLista, usuarioActual, isbn);
		if (correcto)
			lis.borraLibroPorIsbn(isbn);//id==isbn?
		return correcto;
	}
	
	public Boolean addLibroEnLista(Libro lib, String nombreLista){//id==isbn?
		Boolean correcto=false;
		ListaLibros lis = null;
		for (int i=0; i<lista.size(); i++) { 
			lis = lista.get(i);
			if (lis.getNombreLista()==nombreLista)
				i=lista.size();
		}
		
		correcto = servidor.agregaLibroALista(nombreLista, usuarioActual, lib.getIsbn());
		if (correcto)
			lis.addLibro(lib);
		return correcto;
	}
	

	public ArrayList<String> getNombresListas() {
		//Tambien suponemos que va bien tras ActualizarTodoDeServidor()
		ArrayList<String> nombres = new ArrayList<String>(); 
		ListaLibros lis = null;
		for (int i=0; i<lista.size(); i++) { 
			lis = lista.get(i);
			nombres.add(lis.getNombreLista());
		}
		return nombres;
	}
	
}
