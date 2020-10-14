package del.ac.id.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

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
	
	
}
