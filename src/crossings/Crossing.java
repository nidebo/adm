package crossings;

public class Crossing {
		public String user1;
		public String user2;
		public String book1;
		public String book2;
		public String state;
		public String _id;

		public Crossing(String libro, String usuario, Boolean UsuarioSoyYo){
			if (UsuarioSoyYo==true){
				this.book2=libro;
				this.user1=usuario;
			}
			else{
				this.book1=libro;
				this.user2=usuario;
			}
		}
		
		public String getuser1() {
			return user1;
		}

		public void setuser1(String user1) {
			this.user1 = user1;
		}

		public String getuser2() {
			return user2;
		}

		public void setuser2(String user2) {
			this.user2 = user2;
		}

		public String getbook1() {
			return book1;
		}

		public void setbook1(String book1) {
			this.book1 = book1;
		}

		public String getbook2() {
			return book2;
		}

		public void setbook2(String book2) {
			this.book2 = book2;
		}
		
		public String getstate() {
			return state;
		}

		public void setstate(String state) {
			this.state = state;
		}
}
