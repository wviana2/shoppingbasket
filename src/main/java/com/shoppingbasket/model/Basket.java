package com.shoppingbasket.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.shoppingbasket.repository.ItemRepository;

public class Basket {
	
	
	private Long id;
	
	private String name;
	
	private List<Item> items;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public Double getBasketTotal() {
		Double total = 0d;
		List<Item> items = this.getItems();
		
		if(items != null) {
			for(Item i : items) {
				total += i.getPrice();
			}
		}
		
		return total;
	}

	
}
