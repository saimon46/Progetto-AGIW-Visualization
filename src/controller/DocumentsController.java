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

import java.util.StringTokenizer;

import model.*;


@ManagedBean
@SessionScoped
public class DocumentsController {

	private String keyword;
	private final String indexDoc = "indexdoc";
	
	private int previousPages;
	private int nextPages;

	@ManagedProperty(value="#{docs}")
	private List<MetaDoc> docs;
	
	@ManagedProperty(value="#{categorybyKeyword}")
	private Map<String, Integer> categorybyKeyword = new HashMap<String, Integer>();
	
	
	public String addPages() {
		this.previousPages += 10;
		this.nextPages += 10;
		if(!this.docs.isEmpty()){
			this.docs.clear();
			this.setDocs(docs);
		}
		return searchDocs();
			
	}
	
	public String removePages() {
		if(this.previousPages == 0 && this.nextPages == 10)
			return "index";
		else{
			this.previousPages -= 10;
			this.nextPages -= 10;
			if(!this.docs.isEmpty()){
				this.docs.clear();
				this.setDocs(docs);
			}
			
			return searchDocs();
		}
	}
	
	public String searchDocs_begin() {
		//THIS SET OR RESET THE FIRST 10 DOCS
		this.previousPages = 0;
		this.nextPages = 10;
		this.docs = new ArrayList<MetaDoc>();
		if(!this.docs.isEmpty())
			this.docs.clear();
		
		//THIS RUN TOTAL QUERY FOR THE CATEGORIES BY KEYWORD
		this.searchCategories();
		
		return searchDocs();
	}
	
	public String searchDocs() {

		Node node = nodeBuilder().node();
		Client client = node.client();
		
		try{
			SearchResponse response = client.prepareSearch(indexDoc)
					.setTypes("page")
					.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
					.setQuery(QueryBuilders.matchQuery("Keyword", keyword)) // Query
					.setQuery(QueryBuilders.matchQuery("ContentIndex", keyword))
					.setFrom(previousPages).setSize(nextPages).setExplain(true) //10 docs
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
			
			node.close();
			
			if(this.docs.isEmpty())
				return "errorSearchDoc"; /*Keyword non trovata*/
			else 
				return "allDocs";

		}catch(Exception e){
			/*Errore*/
			return "index";
		}
	}
	
	public void searchCategories() {
		Node node = nodeBuilder().node();
		Client client = node.client();
		
		try{
			SearchResponse response = client.prepareSearch(indexDoc)
					.setTypes("page")
					.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
					.setQuery(QueryBuilders.matchQuery("Keyword", keyword)) // Query
					.setQuery(QueryBuilders.matchQuery("ContentIndex", keyword))
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

				StringTokenizer mainCategory = new StringTokenizer(doc.getCategory(), "-");
				int value = 0;
				if (this.categorybyKeyword.containsKey(mainCategory.toString())){
					 value = this.categorybyKeyword.get(mainCategory.toString()) +1;
					this.categorybyKeyword.put(mainCategory.toString(), value);
				}else
					this.categorybyKeyword.put(mainCategory.toString(), 1);
				
			}
			
			node.close();

		}catch(Exception e){
		
		}
	}
	
	
	
	//Getters and Setters

	
	public List<MetaDoc> getDocs() {
		return docs;
	}

	public Map<String, Integer> getCategorybyKeyword() {
		return categorybyKeyword;
	}

	public void setCategorybyKeyword(Map<String, Integer> categorybyKeyword) {
		this.categorybyKeyword = categorybyKeyword;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public void setDocs(List<MetaDoc> docs) {
		this.docs = docs;
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
