package Xanadu.Validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Method;

@Slf4j
public class UniqueValidator implements ConstraintValidator<Unique, Object> {

    @Autowired
    private ApplicationContext applicationContext;
    private Class<?> repository;
    private String methodCheck;

    @Override
    public void initialize(Unique constraintAnnotation) {
        repository = constraintAnnotation.repository();
        methodCheck = constraintAnnotation.methodCheck();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        try {
            Object bean = applicationContext.getBean(repository);
            Method method = repository.getMethod(methodCheck, value.getClass());
            Object object = method.invoke(bean, value);
            return object == null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
