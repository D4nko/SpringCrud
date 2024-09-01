package pl.kurs.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import pl.kurs.inheritance.model.command.CreatePersonCommand;
import pl.kurs.validation.annotation.RequiredAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequiredAttributesValidator implements ConstraintValidator<RequiredAttributes, CreatePersonCommand> {
    private static final Map<String, List<String>> REQUIRED_FIELDS = new HashMap<>();

    static {
        REQUIRED_FIELDS.put("employee", List.of("name", "age", "position", "salary"));
        REQUIRED_FIELDS.put("student", List.of("name", "age", "scholarship", "group"));
    }

    @Override
    public boolean isValid(CreatePersonCommand command, ConstraintValidatorContext context) {
        if (command == null || command.getClassType() == null) {
            return false;
        }

        List<String> requiredFields = REQUIRED_FIELDS.get(command.getClassType().toLowerCase());
        if (requiredFields == null) {
            return true;
        }

        for (String field : requiredFields) {
            boolean isFieldPresent = command.getParameters().stream()
                    .anyMatch(param -> param.getName().equals(field) && param.getValue() != null);
            if (!isFieldPresent) {
                return false;
            }
        }

            return true;
    }
}
