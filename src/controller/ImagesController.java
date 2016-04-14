package controller;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
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
	private int numberPage;

	@ManagedProperty(value="#{imgs}")
	private List<MetaImg> imgs;
	
	private BigDecimal timeSearch;
	private MathContext arr = new MathContext(1, RoundingMode.CEILING); //Rounding to excess
	
	public String addPages() {
		this.nextPages += 12;
		this.numberPage++;
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
			this.numberPage--;
			if(!this.imgs.isEmpty()){
				this.imgs.clear();
			}
			
			return searchImgs();
		}
	}
	
	public String searchImgs_begin() {
		//THIS SET OR RESET THE FIRST 10 IMGS
		this.nextPages = 0;
		this.numberPage = 1;
		this.imgs = new ArrayList<MetaImg>();
		if(!this.imgs.isEmpty())
			this.imgs.clear();
		
		return searchImgs();
	}
	
	public String searchImgs() {
		this.timeSearch = null; //Reset Time Search
		long start = System.nanoTime();
		
		try{	
			SearchResponse response =  ClientProvider.instance().getClient().prepareSearch(indexImg)
					.setTypes("image")
					.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
					.setQuery(QueryBuilders.boolQuery().should(QueryBuilders.matchQuery("ContentSource", keyword))
							.should(QueryBuilders.matchQuery("Keyword", keyword))
							.should(QueryBuilders.matchQuery("TitleSource", keyword)))
					.setFrom(nextPages).setSize(12).setExplain(true)  //10 Imgs
					.execute()
					.actionGet();

			for (SearchHit hit : response.getHits()) {
				Img img = new Img((String)hit.getSource().get("Keyword"),
						(String)hit.getSource().get("URLImg"),
						(String)hit.getSource().get("URLSource"),
						"",
						"");

				MetaImg curr = new MetaImg(img,(double)hit.getScore());
				this.imgs.add(curr);
			}
			
			BigDecimal end = new BigDecimal((System.nanoTime() - start)/ 1000000000.0);
			this.timeSearch = end.round(this.arr);
			
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

	public BigDecimal getTimeSearch() {
		return timeSearch;
	}

	public void setTimeSearch(BigDecimal timeSearch) {
		this.timeSearch = timeSearch;
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

	public int getNumberPage() {
		return numberPage;
	}

	public void setNumberPage(int numberPage) {
		this.numberPage = numberPage;
	}
}
