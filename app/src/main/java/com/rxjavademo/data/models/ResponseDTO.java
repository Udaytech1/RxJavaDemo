package com.rxjavademo.data.models;

public class ResponseDTO {
	private int id;
	private String title;
	private Object price;
	private String description;
	private String category;
	private String image;

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	public void setPrice(Object price){
		this.price = price;
	}

	public Object getPrice(){
		return price;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setCategory(String category){
		this.category = category;
	}

	public String getCategory(){
		return category;
	}

	public void setImage(String image){
		this.image = image;
	}

	public String getImage(){
		return image;
	}

	@Override
 	public String toString(){
		return 
			"ResponseDTO{" + 
			"id = '" + id + '\'' + 
			",title = '" + title + '\'' + 
			",price = '" + price + '\'' + 
			",description = '" + description + '\'' + 
			",category = '" + category + '\'' + 
			",image = '" + image + '\'' + 
			"}";
		}
}