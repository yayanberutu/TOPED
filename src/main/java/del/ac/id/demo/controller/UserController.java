package del.ac.id.demo.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import del.ac.id.demo.model.User;
import del.ac.id.demo.repository.*;

@RestController
public class UserController {
	@Autowired UserRepository userRepository;
	@Autowired MongoTemplate mongoTemplate;
	
	@GetMapping("/registration")
	public ModelAndView Registration() {
		ModelAndView mv = new ModelAndView("registration");
		mv.addObject("user", new User());
		return mv;
	}
	
	@GetMapping("/Admin")
	public ModelAndView admin() {
		ModelAndView mv = new ModelAndView("admin/index");
		return mv;
	}
	
	@GetMapping("/login")
	public ModelAndView Login() {
		ModelAndView mv = new ModelAndView("login");
		mv.addObject("user", new User());
		return mv;
	}
	
	@GetMapping("/logout")
	public ModelAndView Logout() {
		return this.Login();
	}
	
	@RequestMapping(value="/registration", method = RequestMethod.POST)
	public RedirectView submitRegistration(@ModelAttribute User user, BindingResult bindingResult, HttpSession session) {
		if(bindingResult.hasErrors()) {
			return new RedirectView("/registration");
		}
		
		session.setAttribute("username", user.getUsername());
		session.setAttribute("role", user.getRole());
		mongoTemplate.insert(user, "users");
		
		if(user.getRole() == 0) {
			return new RedirectView("/admin");
		}
	
		return new RedirectView("/item");
	}
	


}
