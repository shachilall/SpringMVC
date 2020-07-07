package com.spring.dashboard.validator;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.spring.dashboard.model.Login;


@Component
public class LoginValidator implements Validator {
	@Autowired
	@Qualifier("emailValidator")
	EmailValidator emailValidator;

	@Override
	public boolean supports(Class<?> clazz) {
		return Login.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Login login = (Login) target;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "emailAddress",
				"NotEmpty.userForm.email");

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password",
				"NotEmpty.userForm.password");

		if (login.getEmailAddress() != null
				&& !login.getEmailAddress().isEmpty()
				&& !emailValidator.valid(login.getEmailAddress())) {
			errors.rejectValue("emailAddress", "Pattern.userForm.email");
		}

	}

}
