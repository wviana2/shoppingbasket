package com.shoppingbasket.repository;

import java.util.List;

import com.shoppingbasket.model.Item;

public interface ItemRepository {

	List<Item> findAll(long basketId);
	void deleteAll();
	void save(Item item);
	void deleteById(long id);
}
