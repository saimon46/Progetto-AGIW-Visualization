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
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;

import model.*;
import service.ClientProvider;

@ManagedBean
@SessionScoped
public class DocumentsController {

	@ManagedProperty(value="#{sessionScope['keyword']}")
	private String keyword;

	private final String indexDoc = "indexdoc";
	private final String indexCatDoc = "indexcatdoc";

	private int nextPages;
	private int nextPagesCategorized;
	private int numberPage;
	private int numberPageCategorized;

	@ManagedProperty(value="#{docs}")
	private List<MetaDoc> docs;

	@ManagedProperty(value="#{categorybyKeyword}")
	private Map<String, Integer> categorybyKeyword;

	private String macroCategorySelected;

	private BigDecimal categoriesTimeIntermediate;
	private BigDecimal timeSearch;
	private MathContext arr = new MathContext(1, RoundingMode.CEILING); //Rounding to excess

	public String addPages() {
		this.nextPages += 10;
		this.numberPage++;
		if(!this.docs.isEmpty()){
			this.docs.clear();
		}
		return searchDocs();

	}

	public String removePages() {
		if(this.nextPages == 0)
			return "index";
		else{
			this.nextPages -= 10;
			this.numberPage--;
			if(!this.docs.isEmpty()){
				this.docs.clear();
			}

			return searchDocs();
		}
	}

	public String addPagesCategorized() {
		this.nextPagesCategorized += 10;
		this.numberPageCategorized++;
		if(!this.docs.isEmpty()){
			this.docs.clear();
		}
		return searchDocsCategorized();

	}

	public String removePagesCategorized() {
		if(this.nextPagesCategorized == 0){
			if(!this.docs.isEmpty())
				this.docs.clear();

			return searchDocs();
		}else{
			this.nextPagesCategorized -= 10;
			this.numberPageCategorized--;
			if(!this.docs.isEmpty()){
				this.docs.clear();
			}

			return searchDocsCategorized();
		}
	}

	public String searchDocs_begin() {
		//THIS SET OR RESET THE FIRST 10 DOCS
		this.nextPages = 0;
		this.numberPage = 1;
		this.docs = new ArrayList<MetaDoc>();
		if(!this.docs.isEmpty())
			this.docs.clear();

		//THIS RUN TOTAL QUERY FOR THE CATEGORIES BY KEYWORD
		this.categoriesTimeIntermediate = this.searchCategories();

		return searchDocs();
	}

	public String searchDocs() {
		this.timeSearch = null; //Reset Time Search
		long start = System.nanoTime();

		try{
			SearchResponse response = ClientProvider.instance().getClient().prepareSearch(indexDoc)
					.setTypes("page")
					.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
					.setQuery(QueryBuilders.boolQuery().should(QueryBuilders.matchQuery("ContentIndex", keyword))
														.should(QueryBuilders.matchQuery("Title", keyword))
														.should(QueryBuilders.matchQuery("Description", keyword))
														.should(QueryBuilders.matchQuery("Category", keyword)))
					.setFrom(nextPages).setSize(10).setExplain(true) //10 docs
					.execute()
					.actionGet();

			for (SearchHit hit : response.getHits()) {
				Doc doc = new Doc((String)hit.getSource().get("Keyword"),
						(String)hit.getSource().get("URL"),
						(String)hit.getSource().get("Title"),
						(String)hit.getSource().get("Description"),
						"",
						"",
						(String)hit.getSource().get("Category"));

				MetaDoc curr = new MetaDoc(doc,(double)Math.floor(hit.getScore() * 10000.0) / 10000.0);
				this.docs.add(curr);
			}

			BigDecimal end = new BigDecimal((System.nanoTime() - start)/ 1000000000.0);
			if(this.categoriesTimeIntermediate != null){
				this.timeSearch = this.categoriesTimeIntermediate.add(end.round(this.arr));
				this.categoriesTimeIntermediate = null;
			}
			else
				this.timeSearch = end.round(this.arr);

			if(this.docs.isEmpty())
				return "errorSearchDoc"; /*Keyword non trovata*/
			else 
				return "allDocs";

		}catch(Exception e){
			/*Errore*/
			return "index";
		}
	}

	public BigDecimal searchCategories() {
		this.timeSearch = null; //RESET TIMESEARCH
		long start = System.nanoTime();

		this.categorybyKeyword = new HashMap<String, Integer>();

		try{
			SearchResponse response =  ClientProvider.instance().getClient().prepareSearch(indexCatDoc)
					.setTypes("page")
					.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
					.setQuery(QueryBuilders.boolQuery().should(QueryBuilders.matchQuery("ContentIndex", keyword))
														.should(QueryBuilders.matchQuery("Title", keyword))
														.should(QueryBuilders.matchQuery("Description", keyword))
														.should(QueryBuilders.matchQuery("Category", keyword)))
					.setSize(250)
					.execute()
					.actionGet();

			for (SearchHit hit : response.getHits()) {
				String category = (String) hit.getSource().get("Category");

				StringTokenizer tokenCategory = new StringTokenizer(category, "-");
				String mainCategory = tokenCategory.nextToken();

				if (this.categorybyKeyword.containsKey(mainCategory)){
					Integer value = this.categorybyKeyword.get(mainCategory) + 1;
					this.categorybyKeyword.replace(mainCategory, value);
				}else
					this.categorybyKeyword.put(mainCategory, 1);
			}
			this.categorybyKeyword = sortByValue(this.categorybyKeyword);

			BigDecimal end = new BigDecimal((System.nanoTime() - start)/ 1000000000.0);
			this.timeSearch = end.round(this.arr);

			return this.timeSearch;

		}catch(Exception e){
			return null;
		}
	}

	public String searchDocsCategorized_begin() {
		//THIS SET OR RESET THE FIRST 10 DOCS
		this.nextPagesCategorized = 0;
		this.numberPageCategorized = 1;
		if(!this.docs.isEmpty())
			this.docs.clear();

		return searchDocsCategorized();
	}

	public String searchDocsCategorized() {
		this.timeSearch = null;	//RESET TIME SEARCH	
		long start = System.nanoTime();

		try{
			//Select the first token (macro-category)
			StringTokenizer tokenCategory = new StringTokenizer(this.macroCategorySelected, "-");
			this.macroCategorySelected = tokenCategory.nextToken();
			
			SearchResponse response =  ClientProvider.instance().getClient().prepareSearch(indexDoc)
					.setTypes("page")
					.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
					.setQuery(QueryBuilders.boolQuery().should(QueryBuilders.matchQuery("ContentIndex", keyword))
														.should(QueryBuilders.matchQuery("Title", keyword))
														.should(QueryBuilders.matchQuery("Description", keyword))
														.should(QueryBuilders.matchQuery("Category", macroCategorySelected)))
					.setFrom(nextPagesCategorized).setSize(10).setExplain(true) //10 docs
					.execute()
					.actionGet();

			for (SearchHit hit : response.getHits()) {
				Doc doc = new Doc((String)hit.getSource().get("Keyword"),
						(String)hit.getSource().get("URL"),
						(String)hit.getSource().get("Title"),
						(String)hit.getSource().get("Description"),
						"",
						"",
						(String)hit.getSource().get("Category"));
				MetaDoc curr = new MetaDoc(doc,(double)Math.floor(hit.getScore() * 10000.0) / 10000.0);
				this.docs.add(curr);
			}

			BigDecimal end = new BigDecimal((System.nanoTime() - start)/ 1000000000.0);
			this.timeSearch = end.round(this.arr);

			if(this.docs.isEmpty())
				return "errorSearchDocCategorized"; /*Keyword non trovata*/
			else 
				return "docsCategorized";

		}catch(Exception e){
			/*Errore*/
			return "index";
		}
	}

	//Getters and Setters

	public List<MetaDoc> getDocs() {
		return docs;
	}

	public BigDecimal getCategoriesTimeIntermediate() {
		return categoriesTimeIntermediate;
	}

	public void setCategoriesTimeIntermediate(BigDecimal categoriesTimeIntermediate) {
		this.categoriesTimeIntermediate = categoriesTimeIntermediate;
	}

	public BigDecimal getTimeSearch() {
		return timeSearch;
	}

	public void setTimeSearch(BigDecimal timeSearch) {
		this.timeSearch = timeSearch;
	}

	public String getMacroCategorySelected() {
		return macroCategorySelected;
	}

	public void setMacroCategorySelected(String macroCategory) {
		this.macroCategorySelected = macroCategory;
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
	
	public int getNumberPageCategorized() {
		return numberPageCategorized;
	}

	public void setNumberPageCategorized(int numberPageCategorized) {
		this.numberPageCategorized = numberPageCategorized;
	}
	
	public int getNextPagesCategorized() {
		return nextPagesCategorized;
	}

	public void setNextPagesCategorized(int nextPagesCategorized) {
		this.nextPagesCategorized = nextPagesCategorized;
	}

	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue( Map<K, V> map ) {
		List<Map.Entry<K, V>> list =
				new LinkedList<>( map.entrySet() );
		Collections.sort( list, new Comparator<Map.Entry<K, V>>()
		{
			@Override
			public int compare( Map.Entry<K, V> o1, Map.Entry<K, V> o2 )
			{
				int rtn = (o1.getValue()).compareTo( o2.getValue() );
				if(rtn == 1)
					return -1;
				if(rtn == -1)
					return 1;
				return rtn;
			}
		} );

		Map<K, V> result = new LinkedHashMap<>();
		for (Map.Entry<K, V> entry : list)
		{
			result.put( entry.getKey(), entry.getValue() );
		}
		return result;
	}
}
