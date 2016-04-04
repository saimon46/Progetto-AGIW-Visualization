package model;

public class MetaImg implements Comparable <MetaImg> {
	
	private Double score;
	private Img img;
	
	public MetaImg (Img img, Double score){
		this.img = img;
		this.score = score;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public Img getImg() {
		return img;
	}

	public void setImg(Img img) {
		this.img = img;
	}	
	
	public int compareTo(MetaImg metaImg){
		return this.score.compareTo(metaImg.getScore());		
	}
}
