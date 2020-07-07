package com.spring.dashboard.validator;


import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.spring.dashboard.model.User;

//http://docs.spring.io/spring/docs/current/spring-framework-reference/html/validation.html#validation-mvc-configuring
@Component
public class UserFormValidator implements Validator {

	@Autowired
	@Qualifier("emailValidator")
	EmailValidator emailValidator;

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		User user = (User) target;

		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "familyName",
				"NotEmpty.userForm.familyname");
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "givenName",
				"NotEmpty.userForm.givenname");
		

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "emailAddress",
				"NotEmpty.userForm.email");
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password",
				"NotEmpty.userForm.password");

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "repeatPassword",
				"NotEmpty.userForm.repeatPassword");

		if (user.getEmailAddress() != null && !user.getEmailAddress().isEmpty()
				&& !emailValidator.valid(user.getEmailAddress())) {
			errors.rejectValue("emailAddress", "Pattern.userForm.email");
		}

		

		Pattern alphaPattern = Pattern.compile("^[a-zA-Z]+$");
		if (!user.getFamilyName().isEmpty()
				&& !alphaPattern.matcher(user.getFamilyName()).matches()) {
			errors.rejectValue("familyName", "Format.userForm.familyName");
		}
		
		

		if (!user.getGivenName().isEmpty()
				&& !alphaPattern.matcher(user.getGivenName()).matches()) {
			errors.rejectValue("givenName", "Format.userForm.givenName");
		}

		if (user.getPassword() != null && !user.getPassword().isEmpty()
				&& user.getRepeatPassword() !=null && !user.getRepeatPassword().isEmpty()
				&& !user.getPassword().equals(user.getRepeatPassword())) {
			errors.rejectValue("repeatPassword", "RepeatPassword.userForm.notEqual");
		}

	}

}