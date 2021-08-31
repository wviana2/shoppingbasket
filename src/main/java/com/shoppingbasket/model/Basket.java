package com.shoppingbasket.model;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;


@Entity
@Table(name="BASKETS")
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class)
public class Basket implements Serializable {
	
	private static final long serialVersionUID = -149357598196063644L;

	@Id
	@Type(type = "uuid-char")
	@Column(nullable = false, name = "BASKET_ID")
	@GeneratedValue
	private UUID basketId;
	
	@Column(nullable = false, name = "NAME")
	private String name;
	
	@OneToMany(targetEntity=Item.class, mappedBy="basket")
	private List<Item> items;

	public UUID getBasketId() {
		return basketId;
	}

	public void setBasketId(UUID basketId) {
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
