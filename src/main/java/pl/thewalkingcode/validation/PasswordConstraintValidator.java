package pl.thewalkingcode.validation;


import pl.thewalkingcode.model.dto.UserRegistrationDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, Object> {

    @Override
    public void initialize(ValidPassword constraintAnnotation) {

    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        UserRegistrationDTO userRegistrationDTO = (UserRegistrationDTO) obj;
        return userRegistrationDTO.getPassword().equals(userRegistrationDTO.getMachingPassword());
    }

}
