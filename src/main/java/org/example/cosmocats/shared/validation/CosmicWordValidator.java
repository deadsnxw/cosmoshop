package org.example.cosmocats.shared.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CosmicWordValidator implements ConstraintValidator<CosmicWordCheck, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return false;
        }
        String lowerCaseValue = value.toLowerCase();
        return lowerCaseValue.contains("star") || lowerCaseValue.contains("galaxy") || lowerCaseValue.contains("comet");
    }
}
