package del.ac.id.demo.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import del.ac.id.demo.model.Item;
import del.ac.id.demo.repository.ItemRepository;


@RestController
public class ItemController {
	@Autowired ItemRepository itemRepository;
	@Autowired MongoTemplate mongoTemplate;
	
	@RequestMapping("/item")
	public ModelAndView item(HttpServletRequest request) {
		if(request.getSession().getAttribute("user") == null) {
			return new ModelAndView("redirect:/login");
		}
		
		List<Item> items = itemRepository.findAll();
		ModelAndView mv = new ModelAndView("item");
		mv.addObject("items", items);
		return mv;
	}
	
	@RequestMapping(value="/addItem", method=RequestMethod.POST)
	public ModelAndView addItem(@ModelAttribute Item item, HttpServletRequest request) {
		
		itemRepository.insert(item);
		ModelAndView mv = new ModelAndView("redirect:/item");
		return mv;
	}
	
	@RequestMapping(value="/updateItem", method=RequestMethod.POST)
	public ModelAndView updateItem(@ModelAttribute Item item, HttpServletRequest request) {
		mongoTemplate.save(item, "items");
		ModelAndView mv = new ModelAndView("redirect:/item");
		return mv;
	}
	
	@GetMapping("/test")
	public String test() {
		return "coba";
	}
	
	@GetMapping("/update/{id}")
	public RedirectView update(@PathVariable (value="id") String id) {
		Query query = new Query(Criteria.where("id").is(id));
		Item item = mongoTemplate.findOne(query, Item.class);
		if(item != null) {
			Update update = new Update().inc("seen", 1);
			mongoTemplate.updateFirst(query, update, Item.class);
		}

		return new RedirectView("/item");
	}
	
	
}
