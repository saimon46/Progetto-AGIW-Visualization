package model;

public class MetaDoc implements Comparable <MetaDoc>{
	
	private Double score;
	private Doc doc;
	
	public MetaDoc (Doc doc, Double score){
		this.doc = doc;
		this.score = score;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public Doc getDoc() {
		return doc;
	}

	public void setDoc(Doc doc) {
		this.doc = doc;
	}	
	
	public int compareTo(MetaDoc metaDoc){
		return this.score.compareTo(metaDoc.getScore());		
	}
}
