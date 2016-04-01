package controller;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

import model.JsonDoc;
import model.JsonDocFacade;
import model.JsonImg;
import model.JsonImgFacade;

@ManagedBean
public class DocumentsController {

	private String keyword;
	private String titleDoc;
	private String titleImg;
	
	private JsonDoc docs;
	private JsonImg imgs;
	
	@EJB(beanName="JsonDocFacade")
	private JsonDocFacade docFacade;
	
	@EJB(beanName="JsonImgFacade")
	private JsonImgFacade imgFacade;
	
	public String searchDocs() {
		try{
			this.docs = docFacade.searchDocs(keyword);
			return "allDocs";
		}catch(Exception e){
			/*Keyword non trovata*/
			return "errorSearch";
		}
	}
	
	public String searchImgs() {
		try{
			this.imgs = imgFacade.searchImgs(keyword);
			return "allImgs";
		}catch(Exception e){
			/*Keyword non trovata*/
			return "errorSearch";
		}
	}

	//Getters and Setters
	
	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getTitleDoc() {
		return titleDoc;
	}

	public void setTitleDoc(String titleDoc) {
		this.titleDoc = titleDoc;
	}

	public String getTitleImg() {
		return titleImg;
	}

	public void setTitleImg(String titleImg) {
		this.titleImg = titleImg;
	}

	public JsonDoc getDocs() {
		return docs;
	}

	public void setDocs(JsonDoc docs) {
		this.docs = docs;
	}

	public JsonImg getImgs() {
		return imgs;
	}

	public void setImgs(JsonImg imgs) {
		this.imgs = imgs;
	}
}
