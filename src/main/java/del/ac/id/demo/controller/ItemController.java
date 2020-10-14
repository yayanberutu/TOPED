package del.ac.id.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import del.ac.id.demo.model.Item;
import del.ac.id.demo.repository.ItemRepository;


@RestController
public class ItemController {
	@Autowired ItemRepository itemRepository;
	
	@RequestMapping("/item")
	public ModelAndView item() {
		List<Item> items = itemRepository.findAll();
		ModelAndView mv = new ModelAndView("item");
		mv.addObject("items", items);
		return mv;
	}
	
	@GetMapping("/test")
	public String test() {
		return "coba";
	}
	
	@GetMapping("/update/{id}")
	public RedirectView update(@PathVariable (value="id") String id) {
		Optional<Item> item = itemRepository.findById(id);
//		List<Item> lsItem = itemRepository.findAll();
		
		Item it = item.get();
		it.setSeen(it.getSeen()+1);
		MongoTemplate mtemp = new MongoTemplate();
		mtemp.save(it);
		

//		System.out.println(it.getItem_name());
//		if(lsItem.contains(it)) {
//			System.out.print("Yes");			
//		}
//		else {
//			System.out.println(lsItem.size());
//			System.out.print("No");
//		}

//		System.out.println(lsItem.indexOf(it));
//		lsItem.get(lsItem.indexOf(it)-1).setSeen(it.getSeen()+1);;
//		itemRepository.deleteAll();
//		itemRepository.saveAll(lsItem);


		return new RedirectView("/item");
	}
	
	
}
