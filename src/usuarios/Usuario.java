package usuarios;

public class Usuario extends Persona {

	public Usuario(String idUsuario, int codigoPostal) {
		super(idUsuario);
		this.CodigoPostal=codigoPostal;
	}

	
	public void setIdUsuario(String idUsuario) {
		this.id = idUsuario;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public void setCodigoPostal(int codigoPostal) {
		CodigoPostal = codigoPostal;
	}
	
}
