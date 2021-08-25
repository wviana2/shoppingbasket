package com.shoppingbasket.web;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shoppingbasket.model.Basket;
import com.shoppingbasket.model.Item;
import com.shoppingbasket.repository.BasketRepository;
import com.shoppingbasket.repository.ItemRepository;
import com.shoppingbasket.service.ShoppingBasketService;

@Controller
@Scope("session")
@RequestMapping("/showBasketList")
public class ShoppingBasketController {

	private static final Logger logger = LogManager.getLogger(ShoppingBasketController.class);
	
	@Autowired
	private ShoppingBasketService service;

	@Autowired
	private BasketRepository basketRepository;

	@Autowired
	private ItemRepository itemRepository;

	@ModelAttribute
	public void addBasketsToModel(Model model) {
	}

	@GetMapping(value = "/basketList")
	public String basketList(Model model) {
		logger.info("calling basketList()...");
		DecimalFormat df = new DecimalFormat("#.##");
		double grandTotal = 0;

		List<Basket> baskets = basketRepository.findAll();

		if (baskets == null) {
			baskets = new ArrayList<Basket>();
		}

		for (Basket b : baskets) {
			grandTotal += b.getBasketTotal();
		}
		grandTotal = Double.parseDouble(df.format(grandTotal));
		model.addAttribute("grandTotal", grandTotal);
		model.addAttribute("baskets", baskets);

		logger.info("exiting basketList()...");

		return "showBasketList";
	}

	@GetMapping(value = "/addBasket")
	public String addBasket(Model model) {
		model.addAttribute("basket", new Basket());
		return "addBasket";
	}

	@PostMapping(path = "/createBasket", consumes = { MediaType.APPLICATION_FORM_URLENCODED_VALUE })
	public String createBasket(@Valid Basket basket, Model model, Errors errors) {

		logger.info("POST CreateBasket Basket0: " + basket);
		logger.info("Errors: {}", errors);
		logger.info("Errors: {}", errors.hasErrors());

		if (errors.hasErrors()) {
			return "addBasket";
		}

		basketRepository.save(basket);

		DecimalFormat df = new DecimalFormat("#.##");
		double grandTotal = 0;

		List<Basket> baskets = basketRepository.findAll();

		if (baskets == null) {
			baskets = new ArrayList<Basket>();
		}

		for (Basket b : baskets) {
			grandTotal += b.getBasketTotal();
		}

		grandTotal = Double.parseDouble(df.format(grandTotal));
		model.addAttribute("grandTotal", grandTotal);
		model.addAttribute("baskets", baskets);

		return "showBasketList";
	}

	@GetMapping(value = "/cancel")
	public String cancel(Model model) {
		DecimalFormat df = new DecimalFormat("#.##");
		double grandTotal = 0;

		List<Basket> baskets = basketRepository.findAll();

		if (baskets == null) {
			baskets = new ArrayList<Basket>();
		}

		grandTotal = Double.parseDouble(df.format(grandTotal));
		model.addAttribute("grandTotal", grandTotal);
		model.addAttribute("baskets", baskets);

		return "showBasketList";
	}

	@GetMapping(value = "/clear")
	public String clearBasketList(Model model) {
		itemRepository.deleteAll();
		basketRepository.deleteAll();

		DecimalFormat df = new DecimalFormat("#.##");
		double grandTotal = 0;

		List<Basket> baskets = basketRepository.findAll();

		if (baskets == null) {
			baskets = new ArrayList<Basket>();
		}

		grandTotal = Double.parseDouble(df.format(grandTotal));
		model.addAttribute("grandTotal", grandTotal);
		model.addAttribute("baskets", baskets);

		return "showBasketList";
	}

	@GetMapping(value = "/itemList")
	public String itemList(Model model) {

		List<Item> items = service.getItems();

		model.addAttribute("items", items);
		return "itemList";
	}

	@GetMapping(value = "/basketItems/{id}")
	public String basketItems(@PathVariable("id") int id, HttpSession session, Model model) {
		logger.info("calling basketItems()...");
		logger.info("Basket id: " + id);

		Basket basket = null;
		List<Basket> baskets = basketRepository.findAll();

		if (baskets == null) {
			baskets = new ArrayList<Basket>();
		}

		logger.info("Baskets: {}", baskets);

		if (baskets != null) {
			for (Basket b : baskets) {
				if (b.getId() == id) {
					basket = b;
					break;
				}
			}
		}

		logger.info("Current basket: {}", basket);

		session.setAttribute("currentBasket", basket);
		model.addAttribute("currentBasket", basket);
		model.addAttribute("basketItems", basket.getItems());

		logger.info("exiting basketItems()...");
		return "basketItems";
	}

	@GetMapping(value = "/addItem/{id}")
	public String addItemToBasket(@PathVariable("id") int id, HttpSession session, Model model) {
		logger.info("calling addItemToBasket()...");
		logger.info("Item id: " + id);

		Item selectedItem = null;
		List<Item> items = service.getItems();
		for (Item i : items) {
			if (i.getItemId() == id) {
				selectedItem = i;
				break;
			}
		}

		Basket b = (Basket) session.getAttribute("currentBasket");
		selectedItem.setBasket(b);
		itemRepository.save(selectedItem);
		Basket basket = basketRepository.getOne(b.getId());
		model.addAttribute("basketItems", basket.getItems());
		model.addAttribute("currentBasket", basket);

		logger.info("exiting addItemToBasket()...");
		return "basketItems";
	}

	@GetMapping(value = "/removeItem/{id}")
	public String removeItemFromBasket(@PathVariable("id") int id, HttpSession session, Model model) {
		logger.info("calling removeItemFromBasket()...");
		logger.info("Item id: " + id);

		itemRepository.deleteById(Long.valueOf(id));

		Basket b = (Basket) session.getAttribute("currentBasket");
		Basket basket = basketRepository.getOne(b.getId());
		model.addAttribute("basketItems", basket.getItems());
		model.addAttribute("currentBasket", basket);

		logger.info("exiting removeItemFromBasket()...");
		return "basketItems";
	}

}
