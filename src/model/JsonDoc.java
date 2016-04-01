package model;

public class JsonDoc {
	
	private String title;
	//altri attributi da estrarre dal JSon
	
	public JsonDoc (String title){
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
