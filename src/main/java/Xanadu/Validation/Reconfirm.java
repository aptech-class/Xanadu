package Xanadu.Validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Constraint(validatedBy = PasswordConfirmValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface Reconfirm {
    String message() default "confirm value not matched!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String confirmWith ();
    String confirm();
}
