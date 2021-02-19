package com.rdprod.springboot.spring_rdprod_webapp.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordsEqualValidator.class)
public @interface PasswordsEqualConstraint {

    public String message() default "Пароли не совпадают";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};
}
