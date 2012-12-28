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
		ActualizarTodoDeServidor();//Actualizar de local, y COMPROBAR (nueva funcion) de servidor
		//Si no está en local, solo de servidor
		usuarioActual=nombreDeUsuario;
	}
	
	public void ActualizarTodoDeServidor(){
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
	
	public void addListaVacia(String nombre){//No avisa si ya existe
		if(!existe(nombre)){
		ListaLibros lis = new ListaLibros(nombre);
				
		//Queda comprobar, si es posible, si ha sido bien agregada o no
		servidor.creaListaDeUsuario(nombre, usuarioActual);
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
		//Queda comprobar, si es posible, si ha sido bien agregada o no
		servidor.borraListaDeUsuario(nombreLista, usuarioActual);
		lista.remove(lis);
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
		//Nos fiamos de que la lista está correctamente actualizada del servidor
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
		//Queda comprobar, si es posible, si ha sido bien agregada o no
		servidor.borraLibroDeLista(nombreLista, usuarioActual, isbn);
		lis.borraLibroPorIsbn(isbn);//id==isbn?
	}
	
	public void addLibroEnLista(Libro lib, String nombreLista){//id==isbn?
		ListaLibros lis = null;
		for (int i=0; i<lista.size(); i++) { 
			lis = lista.get(i);
			if (lis.getNombreLista()==nombreLista)
				i=lista.size();
		}
		
	
		//Queda comprobar, si es posible, si ha sido bien agregada o no
		servidor.agregaLibroALista(nombreLista, usuarioActual, lib.getIsbn());
		lis.addLibro(lib);
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
