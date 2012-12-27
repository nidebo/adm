package usuarios;

public class Persona {
	//String id;
	
	//String nombre;
	//String apellido;
	//String fechaNacimiento;
	//int CodigoPostal;
	String _id;
	String name;
	String cp;
		
	public Persona(String idUsuario) {
		super();
		this._id = idUsuario;
	}

	public String getId() {
		return _id;
	}

	public String getNombre() {
		return name;
	}

//  public String getApellido() {
//		return apellido;
//	}
	
//	public String getFechaNacimiento() {
//		return fechaNacimiento;
//	}
	
	public String getCodigoPostal() {
		return cp;
	}
	
}
