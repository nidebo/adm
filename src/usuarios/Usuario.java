package usuarios;

public class Usuario extends Persona {
	String password;
	
	public Usuario(String idUsuario, String codigoPostal, String password) {
		super(idUsuario);
		this.cp=codigoPostal;
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
		this._id = idUsuario;
	}

	public void setNombre(String nombre) {
		this.name = nombre;
	}

//	public void setApellido(String apellido) {
//		this.apellido = apellido;
//	}

//	public void setFechaNacimiento(String fechaNacimiento) {
//		this.fechaNacimiento = fechaNacimiento;
//	}

	public void setCodigoPostal(String codigoPostal) {
		cp = codigoPostal;
	}
	
}
