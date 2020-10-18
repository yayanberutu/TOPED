package del.ac.id.demo.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import del.ac.id.demo.model.Item;
import del.ac.id.demo.repository.*;

@RestController
public class ItemController {
	@Autowired ItemRepository itemRepository;
	@Autowired MongoTemplate mongoTemplate;
	
	@RequestMapping("/item")
	public ModelAndView item(HttpServletRequest request) {
		if(request.getSession().getAttribute("user") == null) {
			return new ModelAndView("redirect:/login");
		}
		
		List<Item> items = mongoTemplate.findAll(Item.class);

		ModelAndView mv = new ModelAndView("item");
		mv.addObject("newItem", new Item());
		mv.addObject("items", items);
		System.out.println(items.get(0).getItemDetail().getWeight());
		return mv;
	}
	
	@RequestMapping(value="/item/addNew", method=RequestMethod.POST)
	public ModelAndView addItem(@ModelAttribute Item item, HttpServletRequest request) {
		mongoTemplate.save(item);
		ModelAndView mv = new ModelAndView("redirect:/item");
		return mv;
	}
	
	@RequestMapping(value="/updateItem", method=RequestMethod.POST)
	public ModelAndView updateItem(@ModelAttribute Item item, HttpServletRequest request) {
		Query query = new Query(Criteria.where("id").is(item.getId()));
		System.out.println(item.getId());
		Update update = new Update();
		update.set("item_name", item.getItem_name());
		update.set("stock", item.getStock());
		update.set("price", item.getPrice());
		update.set("discount", item.getDiscount());
		update.set("item_detail.weight", item.getItemDetail().getWeight());
		update.set("item_detail.condition", item.getItemDetail().getCondition());
		update.set("item.insurance", item.getItemDetail().getInsurance());
		mongoTemplate.updateFirst(query, update, Item.class);
		ModelAndView mv = new ModelAndView("redirect:/item");
		return mv;
	}
	
	@GetMapping("/test")
	public String test() {
		return "coba";
	}
	
	@RequestMapping("/item/buy")
	public RedirectView update(@RequestParam(name="id") String id, @RequestParam(name="stock") double stock, @RequestParam(name="rating") double rating) {
		Query query = new Query(Criteria.where("id").is(id));
		Item item = itemRepository.findById(id).get();
		Update update = new Update();
		update.set("stock", item.getStock()-stock);
		update.set("rating", (item.getRating() + rating)/2);
		mongoTemplate.updateFirst(query, update, Item.class);
		return new RedirectView("/item");
	}
	
	@GetMapping("/item/getbyId/{id}")
	public Item getItembyId(@PathVariable(value="id") String id) {
		System.out.println("getItembyId");
		
		Item item = itemRepository.findById(id).get();
		System.out.println(item.getId());
		return item;
	}
	
	@GetMapping("/item/delete/{id}")
	public ModelAndView delete(@PathVariable(value="id") String id) {
//		System.out.println("getItembyId");
	
		itemRepository.deleteById(id);
		return new ModelAndView("redirect:/item");
	}
	
}
