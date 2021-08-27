package com.shoppingbasket.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.shoppingbasket.model.Item;

@Service("itemRepository")
public class ItemRepositoryImpl implements ItemRepository {

	private static final List<Item> items = new ArrayList<>();
	
	@Override
	public void deleteAll() {
		items.clear();

	}

	@Override
	public void save(Item item) {
		items.add(item);

	}

	@Override
	public void deleteById(long id) {
		Item item = null;
		for(int i=0; i<items.size(); i++) {
			item = items.get(i);
			if(item.getItemId() == id) {
				items.remove(i);
			}
		}
	}

	@Override
	public List<Item> findAll(long basketId) {
		List<Item> basketItems = new ArrayList<>();
		
		if(basketId == 0) {
			return basketItems;
		}
		
		for(Item item : items) {
			if(item.getBasket().getId() == basketId) {
				basketItems.add(item);
			}
		}
		return basketItems;
	}

}
