package usuarios;

public class Persona {
	String id;
	
	String nombre;
	String apellido;
	String fechaNacimiento;
	int CodigoPostal;
	
		
	public Persona(String idUsuario) {
		super();
		this.id = idUsuario;
	}

	public String getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public String getApellido() {
		return apellido;
	}
	
	public String getFechaNacimiento() {
		return fechaNacimiento;
	}
	
	public int getCodigoPostal() {
		return CodigoPostal;
	}
	
}
