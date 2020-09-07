package dmg.stock_screener.entities;

import org.junit.jupiter.api.Assertions;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class AbstractValidationTest {
    private static final Validator validator;

    static {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.usingContext().getValidator();
    }

    protected <T> void validationWithException(T t) {
        Set<ConstraintViolation<T>> violations = validator.validate(t);
        Assertions.assertTrue(violations.size() > 0);
        System.out.println(t.toString());
        violations.stream().map(v -> v.getMessage())
                .forEach(System.out::println);
    }

    protected <T> void validation(T t) {
        Set<ConstraintViolation<T>> violations = validator.validate(t);
        Assertions.assertEquals(0, violations.size());
        System.out.println(t.toString());
    }
}
