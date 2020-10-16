package del.ac.id.demo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
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
	
	@RequestMapping("/login")
	public ModelAndView loginProcess(@ModelAttribute User user, HttpServletRequest request, HttpServletResponse respone) {
		Query query = new Query(Criteria.where("username").is(user.getUsername()).and("password").is(user.getPassword()));
		User loginUser = mongoTemplate.findOne(query, User.class);
		
		if(loginUser == null) {
			return new ModelAndView("redirect:/login");
		}else {
			HttpSession session = request.getSession();
			session.setAttribute("user", loginUser);
		}

		
		ModelAndView mv = new ModelAndView("redirect:/item");
		return mv;
	}
		
	@GetMapping("/logout")
	public ModelAndView Logout(HttpServletRequest request, HttpServletResponse response) {
		request.getSession().removeAttribute("user");
		return new ModelAndView("redirect:/login");
	}
	
	@RequestMapping(value="/registration", method = RequestMethod.POST)
	public ModelAndView submitRegistration(@ModelAttribute User user, BindingResult bindingResult, HttpServletRequest req) {
		if(bindingResult.hasErrors()) {
			return new ModelAndView("redirect:/registration");
		}
		Query query = new Query(Criteria.where("username").is(user.getUsername()));
		if(mongoTemplate.count(query, User.class) > 0) {
			return new ModelAndView("redirect:/registration");
		}
		mongoTemplate.insert(user, "users");
		req.getSession().setAttribute("user", user);
		
		return new ModelAndView("redirect:/item");
	}
	


}
