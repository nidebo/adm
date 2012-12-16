package usuarios;

public class Usuario extends Persona {
	String password;
	
	public Usuario(String idUsuario, int codigoPostal, String password) {
		super(idUsuario);
		this.CodigoPostal=codigoPostal;
		this.password=password;
	}

	
	public Boolean validatePassword(String pass) {
		if(pass==password)
			return true;
		return false;
	}


	public void changePassword(String password) {
		this.password = password;
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
