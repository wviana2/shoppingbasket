package com.shoppingbasket.service;

import java.util.List;

import com.shoppingbasket.model.Basket;
import com.shoppingbasket.model.Item;

public interface ShoppingBasketService {
	
	List<Basket> getBasketList();
	
	Basket createBasket(Basket basket);
	
	Basket getBasket(Basket basket);
	
	void removeBasket(Basket basket);
	
	Item addItem(Item item);
	
	void removeItem(Item item);
	
	List<Item> getItems();

}
