package utiles;

public class Amigo {

	String _id;
	int cp;
	
	public Amigo() {
	}
	
	public Amigo (String _id) {
		this._id = _id;
		
	}

	
	public String getID() {
		return _id;
	}

	public void setID(String _id) {
		this._id = _id;
	}
	
	public int getCP() {
		return cp;
	}
	
	public void setCP(int cp) {
		this.cp = cp;
	}

	
}
