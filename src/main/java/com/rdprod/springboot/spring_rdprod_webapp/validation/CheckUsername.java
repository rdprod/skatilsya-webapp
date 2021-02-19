package com.rdprod.springboot.spring_rdprod_webapp.validation;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UsernameValidator.class)
public @interface CheckUsername {

    public String message() default "Имя пользователя занято";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};
}
