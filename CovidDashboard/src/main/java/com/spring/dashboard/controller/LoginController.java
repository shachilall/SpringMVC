package com.spring.dashboard.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.dashboard.dao.UserDao;
import com.spring.dashboard.model.Login;
import com.spring.dashboard.model.User;
import com.spring.dashboard.validator.LoginValidator;
import com.spring.dashboard.validator.UserFormValidator;

@Controller
public class LoginController {
	
	@Autowired
	LoginValidator loginValidator;

	//Set a form validator
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(loginValidator);
	}

	@Autowired
	UserDao dao;

	// show add user form
	@GetMapping(value = "/login")
	public String showLogin(Model model) {
		Login login = new Login();
		model.addAttribute("loginForm", login);
		return "login";
	}
	
	
	// submit user form
		@RequestMapping(value = "/dashboard")
		public String submitLogin(
				@ModelAttribute("loginForm") @Validated Login login,
				BindingResult result, Model model,HttpServletRequest request,
				final RedirectAttributes redirectAttributes) {

						
			String returnPath = "login";
			if(request.getSession().getAttribute("user")!= null){
				returnPath="dashboard";
			}
			else if (!result.hasErrors()) {
				
				User user = dao.loginUser(login.getEmailAddress(), login.getPassword());
				String message;
				if (user != null) {
					returnPath="dashboard";
					request.getSession().setAttribute("user", user);
				} else {
					 login.setMessage("User email id and/or password does not exist.");
					 model.addAttribute("loginForm", login);
				}
								
			}
			return returnPath;

		}
		
		// show add user form
		@GetMapping(value = "/logout")
		public String logout(HttpServletRequest request) {
			request.getSession().invalidate();
			return "login";
		}
	
}
