package crossings;

public class Crossing {
		private String usuario1;
		private String usuario2;
		private String isbn1;
		private String isbn2;
		private String estado;

		public Crossing(String libro, String usuario, Boolean UsuarioSoyYo){
			if (UsuarioSoyYo==true){
				this.isbn2=libro;
				this.usuario1=usuario;
			}
			else{
				this.isbn1=libro;
				this.usuario2=usuario;
			}
		}
		
		public String getUsuario1() {
			return usuario1;
		}

		public void setUsuario1(String usuario1) {
			this.usuario1 = usuario1;
		}

		public String getUsuario2() {
			return usuario2;
		}

		public void setUsuario2(String usuario2) {
			this.usuario2 = usuario2;
		}

		public String getIsbn1() {
			return isbn1;
		}

		public void setIsbn1(String isbn1) {
			this.isbn1 = isbn1;
		}

		public String getIsbn2() {
			return isbn2;
		}

		public void setIsbn2(String isbn2) {
			this.isbn2 = isbn2;
		}
		
		public String getEstado() {
			return estado;
		}

		public void setEstado(String Estado) {
			this.estado = Estado;
		}
}
