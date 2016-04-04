package controller;

import service.ClientProvider;
import java.util.*;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;

import model.*;

@ManagedBean
@SessionScoped
public class DocumentsController {

	private String keyword;
	private final String indexDoc = "indexdoc";
	private final String indexImg = "indeximg";

	@ManagedProperty(value="#{docs}")
	private List<MetaDoc> docs;

	@ManagedProperty(value="#{imgs}")
	private List<MetaImg> imgs;

	
	public String searchDocs() {

		ClientProvider.instance().getClient().admin().indices().prepareRefresh().execute().actionGet();
		
		this.docs = new ArrayList<MetaDoc>();

		try{			
			SearchResponse response = ClientProvider.instance().getClient().prepareSearch(indexDoc)
					.setTypes("page")
					.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
					.setQuery(QueryBuilders.matchQuery("Keyword", keyword)) // Query
					.setQuery(QueryBuilders.matchQuery("ContentIndex", keyword))
					.setFrom(0).setSize(10).setExplain(true) //First 10 docs
					.execute()
					.actionGet();

			for (SearchHit hit : response.getHits()) {
				Doc doc = new Doc((String)hit.getSource().get("Keyword"),
						(String)hit.getSource().get("URL"),
						(String)hit.getSource().get("Title"),
						(String)hit.getSource().get("Description"),
						(String)hit.getSource().get("ContentHTML"),
						(String)hit.getSource().get("ContentIndex"),
						(String)hit.getSource().get("Category"));

				MetaDoc curr = new MetaDoc(doc,(double)hit.getScore());
				this.docs.add(curr);
			}

			ClientProvider.instance().closeNode();
			
			return "allDocs";

		}catch(Exception e){
			/*Keyword non trovata*/
			return "errorSearch";
		}
	}

	public String searchImgs() {

		ClientProvider.instance().getClient().admin().indices().prepareRefresh().execute().actionGet();
		
		this.imgs = new ArrayList<MetaImg>();

		try{			
			SearchResponse response = ClientProvider.instance().getClient().prepareSearch(indexImg)
					.setTypes("page")
					.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
					.setQuery(QueryBuilders.matchQuery("Keyword", keyword))
					.setQuery(QueryBuilders.matchQuery("ContentIndex", keyword)) 
					.setFrom(0).setSize(10).setExplain(true)  //First 10 Imgs
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

			ClientProvider.instance().closeNode();

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

	public List<MetaDoc> getDocs() {
		return docs;
	}

	public void setDocs(List<MetaDoc> docs) {
		this.docs = docs;
	}

	public List<MetaImg> getImgs() {
		return imgs;
	}

	public void setImgs(List<MetaImg> imgs) {
		this.imgs = imgs;
	}
}
