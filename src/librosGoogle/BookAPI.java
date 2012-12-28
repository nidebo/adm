package librosGoogle;

public class BookAPI {
	String id;
	String kind;
	String etag;
	String selfLink;
	VolumeInfo volumeInfo;
	SaleInfo saleInfo;
	AccessInfo accessInfo;
		
	
	public BookAPI(){
	}
	public BookAPI(String id, String kind, String etag, String selfLink,
			VolumeInfo volumeInfo, SaleInfo saleInfo, AccessInfo accessinfo) {
		super();
		this.id = id;
		this.kind = kind;
		this.etag = etag;
		this.selfLink = selfLink;
		this.volumeInfo = volumeInfo;
		this.saleInfo = saleInfo;
		this.accessInfo = accessinfo;
	}
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
	public String getEtag() {
		return etag;
	}
	public void setEtag(String etag) {
		this.etag = etag;
	}
	public String getSelfLink() {
		return selfLink;
	}
	public void setSelfLink(String selfLink) {
		this.selfLink = selfLink;
	}
	public VolumeInfo getVolumeInfo() {
		return volumeInfo;
	}
	public void setVolumeInfo(VolumeInfo volumeInfo) {
		this.volumeInfo = volumeInfo;
	}
	public SaleInfo getSaleInfo() {
		return saleInfo;
	}
	public void setSaleInfo(SaleInfo saleInfo) {
		this.saleInfo = saleInfo;
	}
	public AccessInfo getAccessinfo() {
		return accessInfo;
	}
	public void setAccessinfo(AccessInfo accessinfo) {
		this.accessInfo = accessinfo;
	}
	
}


