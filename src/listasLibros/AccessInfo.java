package listasLibros;
public class AccessInfo{
	String country;
	String viewability;
	boolean embeddable;
	boolean publicDomain;
	String textToSpeechPermission;
	Epub epub;
	Pdf pdf;
	String accessViewStatus;
	public class Epub{
		boolean isAvalaible;
		String acsTokenLink;
	}
	
	public class Pdf{
		boolean isAvailable;
	}
}