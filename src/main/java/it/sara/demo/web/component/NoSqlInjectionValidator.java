package it.sara.demo.web.component;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NoSqlInjectionValidator implements ConstraintValidator<NoSqlInjection, String> {

    private static final String[] SQL_PATTERNS = {
            "('.+--)|(--)|(%7C)",
            ".*\\b(SELECT|INSERT|UPDATE|DELETE|DROP|UNION|ALTER|CREATE)\\b.*",
            ".*([';]+).*"
    };

    @Override
    public boolean isValid(String value, ConstraintValidatorContext ctx) {
        if (value == null) return true;
        String v = value.toUpperCase();

        for (String p : SQL_PATTERNS) {
            if (v.matches(p)) {
                return false;
            }
        }
        return true;
    }

}