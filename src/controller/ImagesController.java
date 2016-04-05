package controller;

import java.util.*;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.node.Node;
import org.elasticsearch.search.SearchHit;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

import model.*;


@ManagedBean
@SessionScoped
public class ImagesController {
	
	private String keyword;
	private final String indexImg = "indeximg";
	
	private int previousPages;
	private int nextPages;

	@ManagedProperty(value="#{imgs}")
	private List<MetaImg> imgs;
	
	public String addPages() {
		this.previousPages += 10;
		this.nextPages += 10;
		if(!this.imgs.isEmpty()){
			this.imgs.clear();
		}
		return searchImgs();
			
	}
	
	public String removePages() {
		if(this.previousPages == 0 && this.nextPages == 10)
			return "index";
		else{
			this.previousPages -= 10;
			this.nextPages -= 10;
			if(!this.imgs.isEmpty()){
				this.imgs.clear();
			}
			
			return searchImgs();
		}
	}
	
	public String searchImgs_begin() {
		//THIS SET OR RESET THE FIRST 10 IMGS
		this.previousPages = 0;
		this.nextPages = 10;
		this.imgs = new ArrayList<MetaImg>();
		if(!this.imgs.isEmpty())
			this.imgs.clear();
		
		return searchImgs();
	}
	
	public String searchImgs() {

		Node node = nodeBuilder().node();
		Client client = node.client();
		
		try{	
			SearchResponse response = client.prepareSearch(indexImg)
					.setTypes("page")
					.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
					.setQuery(QueryBuilders.matchQuery("Keyword", keyword))
					.setQuery(QueryBuilders.matchQuery("ContentSource", keyword)) 
					.setFrom(previousPages).setSize(nextPages).setExplain(true)  //10 Imgs
					.execute()
					.actionGet();

			for (SearchHit hit : response.getHits()) {
				Img img = new Img((String)hit.getSource().get("Keyword"),
						(String)hit.getSource().get("URLImg"),
						(String)hit.getSource().get("URLSource"),
						(String)hit.getSource().get("TitleSource"),
						(String)hit.getSource().get("ContentSource"),
						(String)hit.getSource().get("Category"));

				MetaImg curr = new MetaImg(img,(double)hit.getScore());
				this.imgs.add(curr);
			}

			node.close();
			
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

	public int getPreviousPages() {
		return previousPages;
	}

	public void setPreviousPages(int previousPages) {
		this.previousPages = previousPages;
	}

	public int getNextPages() {
		return nextPages;
	}

	public void setNextPages(int nextPages) {
		this.nextPages = nextPages;
	}
}
