package listasLibros;

import java.util.List;

public class BookList {

	String kind;
	int totalItems;
	List<BookAPI> items;
	
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
	public int getTotalItems() {
		return totalItems;
	}
	public void setTotalItems(int totalItems) {
		this.totalItems = totalItems;
	}
	public List<BookAPI> getItems() {
		return items;
	}
	public void setItems(List<BookAPI> items) {
		this.items = items;
	}



	
	
	
	
}
