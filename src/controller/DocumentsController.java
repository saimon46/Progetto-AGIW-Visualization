package controller;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import model.JsonDoc;
import model.JsonDocFacade;
import model.JsonImg;
import model.JsonImgFacade;

@ManagedBean
@SessionScoped
public class DocumentsController {
	
	private String keyword;
	private String titleDoc;
	private String titleImg;
	
	
	//@ManagedProperty(value="#{docs}")
	private List<JsonDoc> docs;
	
	//@ManagedProperty(value="#{imgs}")
	private List<JsonImg> imgs;
	
	@EJB(beanName="jsonDocFacade")
	private JsonDocFacade docFacade;
	
	@EJB(beanName="jsonImgFacade")
	private JsonImgFacade imgFacade;
	
	public String searchDocs() {
		try{
			this.docs.add(docFacade.searchDocs(keyword));
			return "allDocs";
		}catch(Exception e){
			/*Keyword non trovata*/
			return "errorSearch";
		}
	}
	
	public String searchImgs() {
		try{
			this.imgs.add(imgFacade.searchImgs(keyword));
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

	public List<JsonDoc> getDocs() {
		return docs;
	}

	public void setDocs(List<JsonDoc> docs) {
		this.docs = docs;
	}

	public List<JsonImg> getImgs() {
		return imgs;
	}

	public void setImgs(List<JsonImg> imgs) {
		this.imgs = imgs;
	}
}
