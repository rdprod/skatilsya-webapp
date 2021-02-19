package com.rdprod.springboot.spring_rdprod_webapp.validation;

import com.rdprod.springboot.spring_rdprod_webapp.entity.User;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordsEqualValidator implements ConstraintValidator<PasswordsEqualConstraint, Object> {

    @Override
    public void initialize(PasswordsEqualConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object candidate, ConstraintValidatorContext constraintValidatorContext) {
        User user = (User) candidate;
        return user.getPassword().equals(user.getConfirmPassword());
    }
}
