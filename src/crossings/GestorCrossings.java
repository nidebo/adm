package crossings;

import java.util.ArrayList;

public class GestorCrossings {
	String usuarioActual;
	ArrayList<Crossing> listaCrossings=new ArrayList<Crossing>();
	
	public GestorCrossings(String usuarioActual){
		this.usuarioActual=usuarioActual;
		
		
		
		////////////////////////////
		Crossing uno= new Crossing("Libro que quiero", usuarioActual, true);
		uno.setUsuario2("Nico");
		
		Crossing dos= new Crossing("Libro que quieren de mí", "Juan", false);
		dos.setUsuario1(usuarioActual);
		
		listaCrossings.add(uno);
		listaCrossings.add(dos);
		////////////////////////////
	}
	
	public Boolean AddCrossing(String LibroQueQuiero){
		Crossing nuevoIntercambio=new Crossing(LibroQueQuiero,usuarioActual,true);
		if(!existe(nuevoIntercambio)){
			listaCrossings.add(nuevoIntercambio);
			return true;
		}
		else 
			return false;
	}

	public Boolean AddCrossing(String LibroQueMeSolicitan, String UsuarioQueMeLoSolicita){
		Crossing nuevoIntercambio=new Crossing(LibroQueMeSolicitan,UsuarioQueMeLoSolicita,false);
		if(!existe(nuevoIntercambio)){
			listaCrossings.add(nuevoIntercambio);
			return true;
		}
		else 
			return false;
	}
	
	public Boolean AddCrossing(Crossing objeto){
		if(!existe(objeto)){
			listaCrossings.add(objeto);
			return true;
		}
		else 
			return false;
	}
	
	private boolean existe(Crossing cross) {
		boolean ex = false;
		Crossing aux = null;
		if (!listaCrossings.isEmpty())
			for (int i=0; i<listaCrossings.size(); i++) { 
				aux = listaCrossings.get(i); 
				 	if (aux.getIsbn1()==cross.getIsbn1()&&aux.getUsuario2()==cross.getUsuario2())
				 		ex = true;
				 	if (aux.getIsbn2()==cross.getIsbn2()&&aux.getUsuario1()==cross.getUsuario1())
					 	ex=true;
			}
		return ex;
	}
	
	public Boolean BorrarCrossing(String LibroQueMeSolicitan, String UsuarioQueMeLoSolicita){
		Crossing crossingABorrar=new Crossing(LibroQueMeSolicitan,UsuarioQueMeLoSolicita,false);
		boolean existe = false;
		Crossing aux = null;
		if (!listaCrossings.isEmpty())
			for (int i=0; i<listaCrossings.size(); i++) { 
				aux = listaCrossings.get(i); 
				 	if (aux.getIsbn1()==crossingABorrar.getIsbn1()&&aux.getUsuario2()==crossingABorrar.getUsuario2()){
				 		existe = true;
				 		listaCrossings.remove(i);
				 		i=listaCrossings.size();
				 	}
			}
		return existe;			
	}
	
	public Boolean BorrarCrossing(String LibroQueQuiero){
		Crossing crossingABorrar=new Crossing(LibroQueQuiero,usuarioActual,true);
		boolean existe = false;
		Crossing aux = null;
		if (!listaCrossings.isEmpty())
			for (int i=0; i<listaCrossings.size(); i++) { 
				aux = listaCrossings.get(i); 
				 	if (aux.getIsbn2()==crossingABorrar.getIsbn2()&&aux.getUsuario1()==crossingABorrar.getUsuario1()){
				 		existe = true;
				 		listaCrossings.remove(i);
				 		i=listaCrossings.size();
				 	}
			}
		return existe;			
	}
	
	public Boolean BorrarCrossing(Crossing objeto){
		boolean existe = false;
		Crossing aux = null;
		if (!listaCrossings.isEmpty())
			for (int i=0; i<listaCrossings.size(); i++) { 
				aux = listaCrossings.get(i); 
				 	if ((aux.getIsbn1()==objeto.getIsbn1()&&aux.getUsuario2()==objeto.getUsuario2())
				 			||(aux.getIsbn2()==objeto.getIsbn2()&&aux.getUsuario1()==objeto.getUsuario1())){
				 		existe = true;
				 		listaCrossings.remove(i);
				 		i=listaCrossings.size();
				 	}
			}
		return existe;			
	}
	
	public Crossing GetCrossing(String LibroQueMeSolicitan, String UsuarioQueMeLoSolicita){
		Crossing crossingABuscar=new Crossing(LibroQueMeSolicitan,UsuarioQueMeLoSolicita,false);
		Crossing aux = null;
		if (!listaCrossings.isEmpty())
			for (int i=0; i<listaCrossings.size(); i++) { 
				aux = listaCrossings.get(i); 
				if (aux.getIsbn1()==crossingABuscar.getIsbn1()&&aux.getUsuario2()==crossingABuscar.getUsuario2()){
					return listaCrossings.get(i);
				}
			}
		return null;			
	}
	
	public Crossing GetCrossing(String LibroQueQuiero){
		Crossing crossingABuscar=new Crossing(LibroQueQuiero,usuarioActual,true);
		Crossing aux = null;
		if (!listaCrossings.isEmpty())
			for (int i=0; i<listaCrossings.size(); i++) { 
				aux = listaCrossings.get(i); 
				if (aux.getIsbn2()==crossingABuscar.getIsbn2()&&aux.getUsuario1()==crossingABuscar.getUsuario1()){
					return listaCrossings.get(i);
				}
			}
		return null;			
	}
	
	public int getNumeroCrossingsUsuarioActual(){
		return listaCrossings.size();
	}
}
