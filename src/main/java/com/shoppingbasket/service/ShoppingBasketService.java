package com.shoppingbasket.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.shoppingbasket.model.Item;

@Component
public interface ShoppingBasketService {
	
	List<Item> getItems();

}
