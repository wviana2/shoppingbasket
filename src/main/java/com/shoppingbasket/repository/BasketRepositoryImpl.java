package com.shoppingbasket.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.shoppingbasket.model.Basket;

@Service("basketRepository")
public class BasketRepositoryImpl implements BasketRepository {

	private static final List<Basket> baskets = new ArrayList<>();
	
	@Override
	public List<Basket> findAll() {
		return baskets;
	}

	@Override
	public void save(Basket basket) {
		baskets.add(basket);
	}

	@Override
	public void deleteAll() {
		baskets.clear();
	}

	@Override
	public Basket getOne(long id) {
		Basket basket = null;
		for(int i=0; i<baskets.size(); i++) {
			basket = baskets.get(i);
			
			if(basket.getId() == id) {
				break;
			}
		}
		return basket;
	}

}
