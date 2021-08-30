package com.shoppingbasket.model;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@Table(name="ITEMS")
@EntityListeners(AuditingEntityListener.class)
public class Item implements Serializable {
	
	private static final long serialVersionUID = -1766963228672417439L;

	@Id
	@GeneratedValue
	@Type(type = "uuid-char")
	@Column(nullable = false, name = "ITEM_ID")
	private UUID itemId;
	
	@Column(nullable = false, name = "NAME")
	private String name;
	
	@Column(nullable = false, name = "DESCRIPTION")
	private String description;
	
	@Column(nullable = false, name = "PRICE")
	private Double price;
	
	@ManyToOne
	@JoinColumn(name = "BASKET_ID", nullable=false)
	private Basket basket;

	public UUID getItemId() {
		return itemId;
	}

	public void setItemId(UUID itemId) {
		this.itemId = itemId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Basket getBasket() {
		return basket;
	}

	public void setBasket(Basket basket) {
		this.basket = basket;
	}
	
}
