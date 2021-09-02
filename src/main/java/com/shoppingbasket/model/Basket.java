package com.shoppingbasket.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;


//@Data
@Entity
@Table(name="BASKETS")
@EntityListeners(AuditingEntityListener.class)
public class Basket implements Serializable {
	
	private static final long serialVersionUID = -149357598196063644L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long basketId;
	
	private String name;
	
	@OneToMany(targetEntity=Item.class, mappedBy="basket")
	private List<Item> items;

	public Long getBasketId() {
		return basketId;
	}

	public void setBasketId(Long basketId) {
		this.basketId = basketId;
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
