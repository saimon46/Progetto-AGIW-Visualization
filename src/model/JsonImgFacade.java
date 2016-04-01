package model;

import javax.ejb.Stateless;


@Stateless(name="JsonImgFacade")
public class JsonImgFacade {
	
	public JsonImg searchImgs(String keyword) {
		JsonImg imgs = new JsonImg(keyword);
		return imgs;
	}
}
