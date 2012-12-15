package usuarios;

import java.util.ArrayList;


public class AmigoList {
	
	private ArrayList<Persona> friends= new ArrayList<Persona>();

	public AmigoList(){
		
	}
	
	public ArrayList<Persona> getListaAmigos() {
		return friends;
	}

	public void addAmigo(Persona i){//No avisa si ya existe el libro
		if (!existe(i))
			friends.add(i);
	}

	public Persona getAmigoPorId(String id){
		Persona p = null;
		for (int i=0; i<friends.size(); i++) { 
			p = friends.get(i);
			if (p.getId()==id)
				i=friends.size();
		}
		return p;
	}

	private boolean existe(Persona p) {
		boolean ex = false;
		Persona aux = null;
		if (!friends.isEmpty())
			for (int i=0; i<friends.size(); i++) { 
				aux = friends.get(i); 
				 	if (aux.getId()==p.getId())
				 		ex = true;
				}
		return ex;
	}
	
	public void borraAmigoPorId(String id){
		Persona p = null;
		for (int i=0; i<friends.size(); i++) { 
			 p = friends.get(i); 
			 	if (p.getId()==id)
			 		friends.remove(p);
			}
	}
			
}
