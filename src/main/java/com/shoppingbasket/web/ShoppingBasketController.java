package com.shoppingbasket.web;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shoppingbasket.model.Basket;
import com.shoppingbasket.model.Item;
import com.shoppingbasket.service.ShoppingBasketService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@Scope("session")
@RequestMapping("/showBasketList")
public class ShoppingBasketController {

	private static final org.slf4j.Logger log =
			org.slf4j.LoggerFactory.getLogger(ShoppingBasketController.class);
	
	@Autowired
	private ShoppingBasketService service;
	
	
	@ModelAttribute
	public void addBasketsToModel(Model model) {
		//log.info("calling addBasketsToModel()...");
		//log.info("exiting addBasketsToModel()...");
	}
	
	@GetMapping(value="/basketList")
	public String basketList(HttpSession session, Model model) {
		log.info("calling basketList()...");
		DecimalFormat df = new DecimalFormat("#.##");
		double grandTotal = 0;
		
		List<Basket> baskets = (List<Basket>) session.getAttribute("baskets");
		log.info("Baskets: " + baskets);
		
		if( baskets == null ) {
			baskets = new ArrayList<Basket>();
		}
		
		for(Basket b : baskets) {
			grandTotal += b.getBasketTotal();
		}
		grandTotal = Double.parseDouble(df.format(grandTotal));
		model.addAttribute("grandTotal", grandTotal);
		model.addAttribute("baskets", baskets);
		session.setAttribute("baskets", baskets);
		
		
		log.info("exiting basketList()...");
		
		return "showBasketList";
	}
	
	@GetMapping(value="/createBasket")
	public String showCustomerBasketList(HttpSession session, Model model) {
		log.info("calling showCustomerBasketList()...");
		DecimalFormat df = new DecimalFormat("#.##");
		double grandTotal = 0;
		
		List<Basket> baskets = (List<Basket>) session.getAttribute("baskets");
		log.info("Baskets: " + baskets);
		
		if( baskets != null ) {
			log.info("Baskets Size: " + baskets.size());
			Basket basket = new Basket();
			basket.setCustomerId(getMaxCustId(baskets));
			basket.setBasketId(getMaxBasketId(baskets));
			basket.setItems(new ArrayList<Item>());
			baskets.add(basket);
		} else {
		    baskets = new ArrayList<Basket>();
			Basket basket = new Basket();
			basket.setCustomerId(1);
			basket.setBasketId(1);
			basket.setItems(new ArrayList<Item>());
			baskets.add(basket);
		}
		
		for(Basket b : baskets) {
			grandTotal += b.getBasketTotal();
		}
		grandTotal = Double.parseDouble(df.format(grandTotal));
		model.addAttribute("grandTotal", grandTotal);
		
		model.addAttribute("baskets", baskets);
		session.setAttribute("baskets", baskets);
		
		log.info("exiting showCustomerBasketList()...");
		
		return "showBasketList";
	}
	
	@GetMapping(value="/clear")
	public String clearBasketList(HttpSession session, Model model) {
		
		model.addAttribute("grandTotal", 0.00);
		model.addAttribute("baskets", new ArrayList<Basket>());
		session.removeAttribute("baskets");
		return "showBasketList";
	}
	
	@GetMapping(value="/itemList/{id}")
	public String itemList(@PathVariable("id") int id, HttpSession session, Model model) {
		log.info("Path variable id: " + id);
		
		List<Item> items = service.getItems();
		
		model.addAttribute("items", items);
		session.setAttribute("items", items);
		return "itemList";
	}
	
	@GetMapping(value="/basketItems/{id}")
	public String basketItems(@PathVariable("id") int id, HttpSession session, Model model) {
		log.info("calling basketItems()...");
		log.info("Basket id: " + id);
		
		
		Basket basket = null;
		List<Basket> baskets = (List<Basket>) session.getAttribute("baskets");
		
		log.info("Baskets: " + baskets);
		
		if( baskets != null ) {
			for(Basket b : baskets) {
				if(b.getBasketId() == id) {
					basket = b;
					break;
				}
			}
		}
		
		log.info("Current basket: " + basket);
		
		session.setAttribute("currentBasket", basket);
		model.addAttribute("currentBasket", basket);
		session.setAttribute("basketItems", basket.getItems());
		model.addAttribute("basketItems", basket.getItems());
		
		log.info("exiting basketItems()...");
		return "basketItems";
	}
	
	@GetMapping(value="/addItem/{id}")
	public String addItemToBasket(@PathVariable("id") int id, HttpSession session, Model model) {
		log.info("calling addItemToBasket()...");
		log.info("Item id: " + id);
		
		Item selectedItem = null;
		List<Item> items = service.getItems();
		for(Item i : items) {
			if(i.getItemId() == id) {
				selectedItem = i;
				break;
			}
		}
		
		Basket currBasket = (Basket)session.getAttribute("currentBasket");
		if(currBasket != null) {
			currBasket.getItems().add(selectedItem);
		}
		
		
		log.info("Current basket: " + currBasket);
		
		session.setAttribute("currentBasket", currBasket);
		model.addAttribute("currentBasket", currBasket);
		session.setAttribute("basketItems", currBasket.getItems());
		model.addAttribute("basketItems", currBasket.getItems());
		
		log.info("exiting addItemToBasket()...");
		return "basketItems";
	}
	
	@GetMapping(value="/removeItem/{id}")
	public String removeItemFromBasket(@PathVariable("id") int id, HttpSession session, Model model) {
		log.info("calling removeItemFromBasket()...");
		log.info("Item id: " + id);
		
		Item deleteItem = null;
		Basket currBasket = (Basket)session.getAttribute("currentBasket");
		if(currBasket != null &&
				currBasket.getItems() != null) {
			for(Item item : currBasket.getItems()) {
				if(item.getItemId() == id) {
					deleteItem = item;
					break;
				}
			}
			currBasket.getItems().remove(deleteItem);
		}
		
		
		log.info("Current basket: " + currBasket);
		
		session.setAttribute("currentBasket", currBasket);
		model.addAttribute("currentBasket", currBasket);
		session.setAttribute("basketItems", currBasket.getItems());
		model.addAttribute("basketItems", currBasket.getItems());
		
		log.info("exiting removeItemFromBasket()...");
		return "basketItems";
	}
	
	private int getMaxCustId(List<Basket> baskets) {
		int maxCustId = 1;
		for(Basket b : baskets) {
			if(b.getCustomerId() >= maxCustId) {
				maxCustId = b.getCustomerId() + 1;
			}
		}
		return maxCustId;
	}
	
	private int getMaxBasketId(List<Basket> baskets) {
		int maxBasketId = 1;
		for(Basket b : baskets) {
			if(b.getBasketId() >= maxBasketId) {
				maxBasketId = b.getBasketId() + 1;
			}
		}
		return maxBasketId;
	}


}
