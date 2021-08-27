package com.shoppingbasket.repository;

import java.util.List;

import com.shoppingbasket.model.Basket;

public interface BasketRepository {

	List<Basket> findAll();
	void save(Basket basket);
	void deleteAll();
	Basket getOne(long id);
}
