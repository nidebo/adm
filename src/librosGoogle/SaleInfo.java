package librosGoogle;

public class SaleInfo{
	String country;
	String saleability;
	boolean isEbook;
	Price listPrice;
	Price retailPrice;
	String buyLink;
	
	
		
	public String getCountry() {
		return country;
	}



	public void setCountry(String country) {
		this.country = country;
	}



	public String getSaleability() {
		return saleability;
	}



	public void setSaleability(String saleability) {
		this.saleability = saleability;
	}



	public boolean isEbook() {
		return isEbook;
	}



	public void setEbook(boolean isEbook) {
		this.isEbook = isEbook;
	}



	public Price getListPrice() {
		return listPrice;
	}



	public void setListPrice(Price listPrice) {
		this.listPrice = listPrice;
	}



	public Price getRetailPrice() {
		return retailPrice;
	}



	public void setRetailPrice(Price retailPrice) {
		this.retailPrice = retailPrice;
	}



	public String getBuyLink() {
		return buyLink;
	}



	public void setBuyLink(String buyLink) {
		this.buyLink = buyLink;
	}


	public class Price{
		Float amount;
		String currencyCode;
	}
	
	
}
