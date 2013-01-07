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
		if (listaNombresUsuarios==null)
			return null;
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
	public ArrayList<String> quienTieneElLibro(String IdLibro){
		ArrayList<String> listaLibros;
		listaLibros=servidor.obtenerListas("adminUsuariosPorLibro");
		//Lista total de libros compartidos por usuarios
		if(listaLibros==null)
			return null;
		for(int i=0;i<listaLibros.size();i++){
			if(listaLibros.get(i)==IdLibro){//Libro encontrado. Si no, no podemos hacer la llamada al servidor
				ArrayList<String> listaUsuariosConLibro;
				listaUsuariosConLibro=servidor.obtenerLibrosLista(IdLibro, "adminUsuariosPorLibro");
				return listaUsuariosConLibro;
			}
		}
		return null;
	}
	
	public boolean AddLibroUsuario(String libro){
		boolean flag1=true, flag2 = true;
		ArrayList<String> listaLibrosCompartidos;
		ArrayList<String> listaNombresUsuarios;
		listaNombresUsuarios=servidor.obtenerListas("adminLibrosPorUsuario");
		if(listaNombresUsuarios==null)
			return false;
		listaLibrosCompartidos=servidor.obtenerListas("adminUsuariosPorLibro");
		if (listaLibrosCompartidos==null)
			return false;
		if(!listaLibrosCompartidos.contains(libro)){
			flag1=servidor.creaListaDeUsuario(libro, "adminUsuariosPorLibro");
		}
		if(!listaNombresUsuarios.contains(usuarioActual)){
			//En este IF no se porque mierdas pasa algo raro
			flag2=servidor.creaListaDeUsuario(usuarioActual, "adminLibrosPorUsuario");
		}
		if(flag1==false || flag2==false)
			return false;
		listaLibrosCompartidos=servidor.obtenerLibrosLista(libro, "adminUsuariosPorLibro");
		listaNombresUsuarios=servidor.obtenerLibrosLista(usuarioActual, "adminLibrosPorUsuario");
		if(!listaLibrosCompartidos.contains(usuarioActual)){
			flag1=servidor.agregaLibroALista(libro, "adminUsuariosPorLibro", usuarioActual);
		}
		if(!listaNombresUsuarios.contains(libro)){
			flag2=servidor.agregaLibroALista(usuarioActual, "adminLibrosPorUsuario", libro);
		}
		return (flag1 && flag2);
	}
	
	public boolean BorraLibroUsuario(String libro){
		boolean flag1=true, flag2 = true;
		ArrayList<String> listaLibrosCompartidos;
		ArrayList<String> listaNombresUsuarios;
		listaLibrosCompartidos=servidor.obtenerListas("adminUsuariosPorLibro");
		listaNombresUsuarios=servidor.obtenerListas("adminLibrosPorUsuario");
		if(listaLibrosCompartidos==null)
			return false;
		if (listaNombresUsuarios==null)
			return false;
		flag1=flag2=true;
		if(!listaLibrosCompartidos.contains(libro)){
			flag1=false;
		}if(!listaNombresUsuarios.contains(usuarioActual)){
			flag2=false;
		}
		if(flag1){
			listaLibrosCompartidos=servidor.obtenerLibrosLista(libro, "adminUsuariosPorLibro");
			if(!listaLibrosCompartidos.contains(usuarioActual))
				flag1=true;
			else
				flag1=servidor.borraLibroDeLista(libro, "adminUsuariosPorLibro", usuarioActual);
		}
		if(flag2){
			listaNombresUsuarios=servidor.obtenerLibrosLista(libro, "adminLibrosPorUsuario");
			if(!listaNombresUsuarios.contains(usuarioActual))
				flag2=true;
			else
				flag2=servidor.borraLibroDeLista(usuarioActual, "adminLibrosPorUsuario", libro);
		}
		return (flag1 && flag2);
	}

}
