package com.shoppingbasket.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.shoppingbasket.model.Item;

@Scope("session")
@Component
public class ShoppingBasketServiceImpl implements ShoppingBasketService {

	@Override
	public List<Item> getItems() {
		
		Item item = null;
		List<Item> items = new ArrayList<Item>();
		item = new Item();
		item.setItemId(100);
		item.setName("Princess Soap");
		item.setDescription("Bath soap");
		item.setPrice(95.56);
		items.add(item);
		
		item = new Item();
		item.setItemId(101);
		item.setName("Kemil Liquid");
		item.setDescription("Dishwasing soap");
		item.setPrice(73.21);
		items.add(item);
		
		item = new Item();
		item.setItemId(102);
		item.setName("Panamian loaf");
		item.setDescription("White bread");
		item.setPrice(51.63);
		items.add(item);
		
		item = new Item();
		item.setItemId(103);
		item.setName("Osgroz");
		item.setDescription("Milk");
		item.setPrice(127.20);
		items.add(item);
		
		return items;
	}

	
}
