package com.shoppingbasket.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingbasket.model.Basket;
import com.shoppingbasket.model.Item;
import com.shoppingbasket.service.ShoppingBasketService;

@RestController
@RequestMapping("/api")
public class ShoppingBasketController {

	private static final Logger logger = LogManager.getLogger(ShoppingBasketController.class);
	
	@Autowired
	private ShoppingBasketService shoppingBasketService;

	@CrossOrigin
	@GetMapping("/getbasketlist")
	public List<Basket> getBasketList(Model model) {
		logger.info("calling basketList()...");

		return shoppingBasketService.getBasketList();
	}

	@CrossOrigin
	@PostMapping("/createbasket")
	public Basket createBasket(@RequestBody Basket basket) {
		return shoppingBasketService.createBasket(basket);
	}
	
	@CrossOrigin
	@GetMapping("/getbasket")
	public Basket getBasket(@RequestBody Basket basket) {
		return shoppingBasketService.getBasket(basket);
	}

	@CrossOrigin
	@PostMapping("/removebasket")
	public void removeBasket(@RequestBody Basket basket) {
		shoppingBasketService.removeBasket(basket);
	}

	@CrossOrigin
	@GetMapping("/getitemlist")
	public List<Item> getItemList(Model model) {

		return shoppingBasketService.getItems();
	}

	@CrossOrigin
	@PostMapping("/additem")
	public Item addItem(@RequestBody Item item) {
		logger.info("calling additem()...");
		logger.info("Item : {}", item);
		
		return shoppingBasketService.addItem(item);
	}

	@CrossOrigin
	@PostMapping("/removeitem")
	public void removeItem(@RequestBody Item item) {

		logger.info("calling removeitem()...");
		logger.info("Item id: {}", item);

		shoppingBasketService.removeItem(item);
	}

}
