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
import com.spring.dashboard.validator.UserFormValidator;

@Controller
public class RegisterController {
	
	@Autowired
	UserFormValidator userFormValidator;

	//Set a form validator
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(userFormValidator);
	}

	@Autowired
	UserDao dao;


	// show add user form
	@RequestMapping(value = "/createAccount", method = RequestMethod.GET)
	public String createAccount(Model model) {
		System.out.println("createAccount....");
		User user = new User();
		model.addAttribute("userForm", user);
		return "register";
	}

	// submit user form
	@RequestMapping(value = "/registerAccount", method = RequestMethod.POST)
	public String registerAccount(
			@ModelAttribute("userForm") @Validated User user,
			BindingResult result, Model model,HttpServletRequest request,
			final RedirectAttributes redirectAttributes) {

		System.out.println("registerAccount....");
		String message;
		if (result.hasErrors()) {
			System.out.println("registerAccount....has errors");
			return "register";
		} else {
			if(dao.getUserByEmail(user.getEmailAddress())!=null){
				message = "This Email Id already exists in the system.";
				request.setAttribute("message", message);
				return "register";
			}
			User newUser = dao.addUser(user);
			
			if (newUser != null) {
				message = "Application submitted successfully !!";
			} else {
				message = "There was some error in application submission. Please try again.";
			}
			model.addAttribute("message", message);
			return "login";

		}

	}
}
