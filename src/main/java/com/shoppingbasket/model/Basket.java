package com.shoppingbasket.model;

import java.text.DecimalFormat;
import java.util.List;

public class Basket {
	
	
	private int basketId;
	private int customerId;
	private List<Item> items;
	
	
	
	public int getBasketId() {
		return basketId;
	}
	public void setBasketId(int basketId) {
		this.basketId = basketId;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public List<Item> getItems() {
		return items;
	}
	public void setItems(List<Item> items) {
		this.items = items;
	}
	
	public String getBasketName() {
		return "Basket No. " + getBasketId();
	}
	
	public String getCustId() {
		return "Customer Id " + getCustomerId();
	}

	public double getBasketTotal() {
		double total = 0;
	    DecimalFormat df = new DecimalFormat("#.##");
		
		if(items != null) {
			for(Item i : items) {
				total += i.getPrice();
			}
		}
		total = Double.parseDouble(df.format(total));
		return total;
	}
	
}
