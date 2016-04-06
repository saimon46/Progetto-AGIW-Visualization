package controller;

import java.util.*;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;

import model.*;
import service.ClientProvider;


@ManagedBean
@SessionScoped
public class ImagesController {
	
	private String keyword;
	private final String indexImg = "indeximg";
	
	private int nextPages;

	@ManagedProperty(value="#{imgs}")
	private List<MetaImg> imgs;
	
	public String addPages() {
		this.nextPages += 12;
		if(!this.imgs.isEmpty()){
			this.imgs.clear();
		}
		return searchImgs();
			
	}
	
	public String removePages() {
		if(this.nextPages == 0)
			return "index";
		else{
			this.nextPages -= 12;
			if(!this.imgs.isEmpty()){
				this.imgs.clear();
			}
			
			return searchImgs();
		}
	}
	
	public String searchImgs_begin() {
		//THIS SET OR RESET THE FIRST 10 IMGS
		this.nextPages = 0;
		this.imgs = new ArrayList<MetaImg>();
		if(!this.imgs.isEmpty())
			this.imgs.clear();
		
		return searchImgs();
	}
	
	public String searchImgs() {

		try{	
			SearchResponse response =  ClientProvider.instance().getClient().prepareSearch(indexImg)
					.setTypes("page")
					.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
					.setQuery(QueryBuilders.queryString(keyword).field("Keyword")) // Query
					.setQuery(QueryBuilders.queryString(keyword).field("ContentSource"))
					.setFrom(nextPages).setSize(12).setExplain(true)  //10 Imgs
					.execute()
					.actionGet();

			for (SearchHit hit : response.getHits()) {
				Img img = new Img((String)hit.getSource().get("Keyword"),
						(String)hit.getSource().get("URLImg"),
						(String)hit.getSource().get("URLSource"),
						"",
						"",
						(String)hit.getSource().get("Category"));

				MetaImg curr = new MetaImg(img,(double)hit.getScore());
				this.imgs.add(curr);
			}
			
			if(this.imgs.isEmpty())
				return "errorSearchImg"; /*Keyword non trovata*/
			else 
				return "allImgs";
			
		}catch(Exception e){
			/*Errore*/
			return "index";
		}
	}

	//Getters and Setters

	public List<MetaImg> getImgs() {
		return imgs;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public void setImgs(List<MetaImg> imgs) {
		this.imgs = imgs;
	}

	public int getNextPages() {
		return nextPages;
	}

	public void setNextPages(int nextPages) {
		this.nextPages = nextPages;
	}
}
