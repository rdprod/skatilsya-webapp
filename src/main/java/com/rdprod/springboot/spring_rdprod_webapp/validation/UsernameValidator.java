package com.rdprod.springboot.spring_rdprod_webapp.validation;

import com.rdprod.springboot.spring_rdprod_webapp.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UsernameValidator implements ConstraintValidator<UsernameExist, String> {

    @Autowired
    UserService userService;

    @Override
    public void initialize(UsernameExist constraintAnnotation) {

    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {

        return userService.findUserByUsername(username) == null;
    }
}
