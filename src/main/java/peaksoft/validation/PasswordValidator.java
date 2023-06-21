package peaksoft.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<PasswordValid,String> {
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext){
        return password.length()>4 && password.length()<12;
    }

}
