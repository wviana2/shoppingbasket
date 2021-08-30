package com.shoppingbasket.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shoppingbasket.model.Basket;
import com.shoppingbasket.model.Item;
import com.shoppingbasket.repository.BasketRepository;
import com.shoppingbasket.repository.ItemRepository;

@Service("shoppingBasketService")
public class ShoppingBasketServiceImpl implements ShoppingBasketService {

	@Autowired
	private BasketRepository basketRepository;

	@Autowired
	private ItemRepository itemRepository;
	
	@Transactional(readOnly = true)
	@Override
	public List<Basket> getBasketList() {
		List<Basket> baskets = basketRepository.findAll();
		
		if(CollectionUtils.isEmpty(baskets)) {
			baskets = new ArrayList<>();
		}
		return baskets;
	}
	
	@Transactional
	@Override
	public Basket createBasket(Basket basket) {
		Basket b = null;
		if(basket.getBasketId() != null) {
			b = basketRepository.getOne(b.getBasketId());
			b.setName(basket.getName());
			b.setItems(basket.getItems());
		} else {
			b = basket;
		}
		return basketRepository.save(b);
	}
	
	@Transactional(readOnly = true)
	@Override
	public Basket getBasket(Basket basket) {
		return basketRepository.getOne(basket.getBasketId());
	}
	
	@Transactional
	@Override
	public void removeBasket(Basket basket) {
		Basket b = basketRepository.getOne(basket.getBasketId());
		List<Item> items = b.getItems();
		items.forEach(i -> {
			itemRepository.delete(i);
		});
		basketRepository.deleteById(b.getBasketId());
	}
	
	@Transactional
	@Override
	public Item addItem(Item item) {
		Item i = null;
		
		if(item.getItemId() != null) {
			i = itemRepository.getOne(item.getItemId());
			i.setBasket(item.getBasket());
			i.setDescription(item.getDescription());
			i.setName(item.getName());
			i.setPrice(item.getPrice());
		} else {
			i = item;
		}
		return itemRepository.save(i);
	}
	
	@Transactional
	@Override
	public void removeItem(Item item) {
		itemRepository.deleteById(item.getItemId());
	}
	
	@Override
	public List<Item> getItems() {
		
		Item item = null;
		List<Item> items = new ArrayList<Item>();
		item = new Item();
		item.setItemId(UUID.fromString("20e1faeb-270a-4e14-a6c8-2e92b413818b"));
		item.setName("Princess Soap");
		item.setDescription("Bath soap");
		item.setPrice(95.56);
		items.add(item);
		
		item = new Item();
		item.setItemId(UUID.fromString("946bfcc4-bcfe-441e-9e57-ea3ea6a3c938"));
		item.setName("Kemil Liquid");
		item.setDescription("Dishwasing soap");
		item.setPrice(73.21);
		items.add(item);
		
		item = new Item();
		item.setItemId(UUID.fromString("ea4f4bb6-d062-4604-802a-1f78750dee79"));
		item.setName("Panamian loaf");
		item.setDescription("White bread");
		item.setPrice(51.63);
		items.add(item);
		
		item = new Item();
		item.setItemId(UUID.fromString("129a4856-e6d0-44cf-bc99-4a3bfee44cee"));
		item.setName("Osgroz");
		item.setDescription("Milk");
		item.setPrice(127.20);
		items.add(item);
		
		return items;
	}
}
