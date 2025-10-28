package it.sara.demo.web.component;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NoSqlInjectionValidator.class)
@Documented
public @interface NoSqlInjection {
    String message() default "SQL injection pattern detected";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}