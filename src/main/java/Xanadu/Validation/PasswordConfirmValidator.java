package Xanadu.Validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.reflect.Field;
import java.util.Objects;

public class PasswordConfirmValidator implements ConstraintValidator<Reconfirm, Object> {

    private String confirm;
    private String confirmWith;

    @Override
    public void initialize(Reconfirm reconfirm) {
        this.confirm = reconfirm.confirm();
        this.confirmWith = reconfirm.confirmWith();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        try {
            Class<?> clazz = value.getClass();
            Field[] fields = clazz.getDeclaredFields();
            Field confirmField = clazz.getDeclaredField(confirm);
            Field confirmWithField = clazz.getDeclaredField(confirmWith);
            confirmField.setAccessible(true);
            confirmWithField.setAccessible(true);
            return Objects.equals(confirmField.get(value),confirmWithField.get(value) );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
