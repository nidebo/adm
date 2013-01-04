package listasLibros;

import internet.ListaServer;
import java.util.ArrayList;
import apiGoogle.InterfazAPI;

public class ListaCompartibles {
	//ArrayList<Libro> compartidos = new ArrayList<Libro>(); 
	InterfazAPI api=new InterfazAPI();
	ListaServer servidor=new ListaServer();
	String usuarioActual;
	
	public ListaCompartibles(String nombreDeUsuario) {
		usuarioActual=nombreDeUsuario;
	}
	
	//Devuelve los isbn de todos los libros compartibles excepto los mios
	public ArrayList<ParLibroUsuario> getListaCompartibles(){
		ArrayList<String> listaNombresUsuarios;
		ArrayList<ParLibroUsuario> listaLibrosCompartibles = new ArrayList<ParLibroUsuario>();
		listaNombresUsuarios=servidor.obtenerListas("adminLibrosPorUsuario");
		//Lista total de usuarios con libros compartidos
		for(int i=0;i<listaNombresUsuarios.size();i++){//Cargamos nombres listas
			if(listaNombresUsuarios.get(i)!=usuarioActual){
				ArrayList<String> listaLibrosDeUnUsuario;
				listaLibrosDeUnUsuario=servidor.obtenerLibrosLista(listaNombresUsuarios.get(i), "adminLibrosPorUsuario");
				for(int j=0;j<listaLibrosDeUnUsuario.size();j++){//Cargamos libros en listas
					ParLibroUsuario tupla= new ParLibroUsuario(listaNombresUsuarios.get(i),listaLibrosDeUnUsuario.get(j));
					listaLibrosCompartibles.add(tupla);
				}
			}
		}
		return listaLibrosCompartibles;
	}
	
	//Devuelve los usuarios que tienen un libro como intercambiable
	public ArrayList<String> quienTieneElLibro(String IsbnLibro){
		ArrayList<String> listaLibros;
		listaLibros=servidor.obtenerListas("adminUsuariosPorLibro");
		//Lista total de libros compartidos por usuarios
		if(listaLibros==null)
			return null;
		for(int i=0;i<listaLibros.size();i++){
			if(listaLibros.get(i)==IsbnLibro){//Libro encontrado. Si no, no podemos hacer la llamada al servidor
				ArrayList<String> listaUsuariosConLibro;
				listaUsuariosConLibro=servidor.obtenerLibrosLista(IsbnLibro, "adminUsuariosPorLibro");
				return listaUsuariosConLibro;
			}
		}
		return null;
	}

}
