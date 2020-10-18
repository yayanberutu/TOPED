package del.ac.id.demo.controller;


import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import del.ac.id.demo.model.Item;
import del.ac.id.demo.model.Store;
import del.ac.id.demo.repository.StoreRepository;

@RestController
public class StoreController {
	@Autowired MongoTemplate mongoTemplate;
	@Autowired StoreRepository storeRepository;
	
	
	@RequestMapping("/store")
	public ModelAndView getAllStore() {
		List<Store> listStore = storeRepository.findAll();  
		ModelAndView mv = new ModelAndView("/store");
		mv.addObject("listStore", listStore);
		System.out.println(listStore.size());
		return mv;
	}
	
	@RequestMapping("/getItemByStoreName/{store_name}")
	public ModelAndView getItemsByStore(@PathVariable (value="store_name") String store_name) {
//		System.out.print("Store Name: ");
//		System.out.println(store_name);
		Query query = new Query(Criteria.where("store_name").is(store_name));
		List<Item> listItem = mongoTemplate.find(query, Item.class);
		ModelAndView mv = new ModelAndView("/storeitem");
//		System.out.print("SIZE listITEM: ");
//		System.out.println(listItem.size());
		mv.addObject("items", listItem);
		return mv;
	}
}
