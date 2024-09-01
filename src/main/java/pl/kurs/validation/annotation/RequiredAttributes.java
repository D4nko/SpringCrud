package pl.kurs.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import pl.kurs.validation.RequiredAttributesValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RequiredAttributesValidator.class)
public @interface RequiredAttributes {
    String message() default "NO_REQUIRED_ATTRIBUTES";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
