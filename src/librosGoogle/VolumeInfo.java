package librosGoogle;

import java.util.ArrayList;

public class VolumeInfo {
	String title;
	String subtitle;
	ArrayList<String> authors;
	String publisher;
	String publishedDate;
	String description;
	ArrayList<Identifiers> industryIdentifiers;
	int pageCount;
	Dimensions dimensions;
	String printType;
	String mainCategory;
	ArrayList<String> categories;
	Float averageRating;
	int ratingCount;
	String contentVersion;
	ImageLinks imageLinks;
	String language;
	String infoLink;
	String canonicalVolumeLink;
	
	
	
	
	
	
	public String getTitle() {
		return title;
	}
	public String getSubtitle() {
		return subtitle;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public ArrayList<String> getAuthors() {
		return authors;
	}
	public void setAuthors(ArrayList<String> authors) {
		this.authors = authors;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public String getPublishedDate() {
		return publishedDate;
	}
	public void setPublishedDate(String publishedDate) {
		this.publishedDate = publishedDate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public ArrayList<Identifiers> getIndustryIdentifiers() {
		return industryIdentifiers;
	}
	public void setIndustryIdentifiers(ArrayList<Identifiers> industryIdentifiers) {
		this.industryIdentifiers = industryIdentifiers;
	}
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	public Dimensions getDimensions() {
		return dimensions;
	}
	public void setDimensions(Dimensions dimensions) {
		this.dimensions = dimensions;
	}
	public String getPrintType() {
		return printType;
	}
	public void setPrintType(String printType) {
		this.printType = printType;
	}
	public String getMainCategory() {
		return mainCategory;
	}
	public void setMainCategory(String mainCategory) {
		this.mainCategory = mainCategory;
	}
	public ArrayList<String> getCategories() {
		return categories;
	}
	public void setCategories(ArrayList<String> categories) {
		this.categories = categories;
	}
	public Float getAverageRating() {
		return averageRating;
	}
	public void setAverageRating(Float averageRating) {
		this.averageRating = averageRating;
	}
	public int getRatingCount() {
		return ratingCount;
	}
	public void setRatingCount(int ratingCount) {
		this.ratingCount = ratingCount;
	}
	public String getContentVersion() {
		return contentVersion;
	}
	public void setContentVersion(String contentVersion) {
		this.contentVersion = contentVersion;
	}
	public ImageLinks getImageLinks() {
		return imageLinks;
	}
	public void setImageLinks(ImageLinks imageLinks) {
		this.imageLinks = imageLinks;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getInfoLink() {
		return infoLink;
	}
	public void setInfoLink(String infoLink) {
		this.infoLink = infoLink;
	}
	public String getCanonicalVolumeLink() {
		return canonicalVolumeLink;
	}
	public void setCanonicalVolumeLink(String canonicalVolumeLink) {
		this.canonicalVolumeLink = canonicalVolumeLink;
	}
	
	public class ImageLinks{
		String smallThumbnail;
		String thumbnail;
		String small;
		String medium;
		String large;
		String extraLarge;
		public String getSmallThumbnail() {
			return smallThumbnail;
		}
		public void setSmallThumbnail(String smallThumbnail) {
			this.smallThumbnail = smallThumbnail;
		}
		public String getThumbnail() {
			return thumbnail;
		}
		public void setThumbnail(String thumbnail) {
			this.thumbnail = thumbnail;
		}
		public String getSmall() {
			return small;
		}
		public void setSmall(String small) {
			this.small = small;
		}
		public String getMedium() {
			return medium;
		}
		public void setMedium(String medium) {
			this.medium = medium;
		}
		public String getLarge() {
			return large;
		}
		public void setLarge(String large) {
			this.large = large;
		}
		public String getExtraLarge() {
			return extraLarge;
		}
		public void setExtraLarge(String extraLarge) {
			this.extraLarge = extraLarge;
		}
		
		
	}
	


		public class Identifiers{
			String type;
			String identifier;
			
			
			public Identifiers(String type, String identifier) {
				super();
				this.type = type;
				this.identifier = identifier;
			}
			public String getType() {
				return type;
			}
			public void setType(String type) {
				this.type = type;
			}
			public String getIdentifier() {
				return identifier;
			}
			public void setIdentifier(String identifier) {
				this.identifier = identifier;
			}

		}

	

}
