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
	ListaCompartibles shared;
	
	public GestorListas(String nombreDeUsuario) { //Constructor
		usuarioActual=nombreDeUsuario;
		shared= new ListaCompartibles(nombreDeUsuario);
		ActualizarTodoDeServidor();
		//Actualizar de local, y COMPROBAR de servidor
		//Si no estan en local, solo de servidor
		
		//Hay que meter un flag al actualizar todo de servidor, para tener encuenta si podemos
		//trabajar o ir devolviendo errores al recibir solicitudes
	}
	
	private void ActualizarTodoDeServidor(){
		ArrayList<String> listaNombresListas;
		listaNombresListas=servidor.obtenerListas(usuarioActual);
		for(int i=0;i<listaNombresListas.size();i++){//Cargamos nombres listas
			AddListaVacia(listaNombresListas.get(i));
			ArrayList<String> listaLibros;
			listaLibros=servidor.obtenerLibrosLista(listaNombresListas.get(i), usuarioActual);
			for(int j=0;j<listaLibros.size();j++){//Cargamos libros en listas
				Libro lib=api.ObtenerLibroPorId(listaLibros.get(j));
				AddLibroEnLista(lib,listaNombresListas.get(i));
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
	public Boolean AddListaVacia(String nombre){
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
			if (lis.getNombreLista().equals(nombreLista))
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
				 	if (aux.getNombreLista().equals(nombreLista))
				 		ex = true;
				}
		return ex;
	}
	
	//Devuelve todos los libros de una lista
	public ArrayList<Libro> GetListaDeLibros(String nombreLista){//Sin servidor
		//Nos fiamos de que la lista est� correctamente actualizada del servidor
		ListaLibros lis = null;
		for (int i=0; i<lista.size(); i++) { 
			lis = lista.get(i);
			if (lis.getNombreLista().equals(nombreLista))
				i=lista.size();
		}
		return lis.getListaLibros();
	}
	
	public Boolean BorraLibroDeLista(String idLibro, String nombreLista){
		Boolean correcto=false;
		ListaLibros lis = null;
		for (int i=0; i<lista.size(); i++) { 
			lis = lista.get(i);
			if (lis.getNombreLista().equals(nombreLista))
				i=lista.size();
		}

		correcto = servidor.borraLibroDeLista(nombreLista, usuarioActual, idLibro);
		if (correcto)
			lis.borraLibroPorId(idLibro);
		return correcto;
	}
	
	public Boolean AddLibroEnLista(Libro lib, String nombreLista){
		Boolean correcto=false;
		ListaLibros lis = null;
		for (int i=0; i<lista.size(); i++) { 
			lis = lista.get(i);
			if (lis.getNombreLista().equals(nombreLista))
				i=lista.size();
		}
		
		correcto = servidor.agregaLibroALista(nombreLista, usuarioActual, lib.getId());
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
	
	//Libros compartidos:
	
	public ArrayList<ParLibroUsuario> getListaCompletaDeLibrosCompartibles(){
		return shared.getListaCompartibles();
	} //Error si devuelve null
	
	public ArrayList<String> quienTieneElLibro(String IdLibro){
		return shared.quienTieneElLibro(IdLibro);
	} //Error si devuelve null
	
	public boolean AddLibroEnCompartibles(String IdLibro){
		/*if(shared.AddLibroUsuario(IdLibro)==false)
			AddListaVacia("FALSE_5_juan");
		else
			AddListaVacia("TRUE_5_juan");
		*/
		return shared.AddLibroUsuario(IdLibro);
	} //Si false, ha habido error
	
	public boolean BorraLibroDeCompartibles(String IdLibro){
		return shared.BorraLibroUsuario(IdLibro);
	} //Si false, ha habido error
}
