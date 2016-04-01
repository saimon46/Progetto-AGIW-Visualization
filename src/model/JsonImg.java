package model;

public class JsonImg {
	
	private String title;
	//altri attributi da estrarre dal JSon
	
	public JsonImg (String title){
		this.title = title;
		//...
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
}
