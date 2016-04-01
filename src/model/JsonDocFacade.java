package model;

import javax.ejb.Stateless;


@Stateless(name="JsonDocFacade")
public class JsonDocFacade {
	
	public JsonDoc searchDocs(String keyword) {
		JsonDoc docs = new JsonDoc(keyword);
		return docs;
	}
}
