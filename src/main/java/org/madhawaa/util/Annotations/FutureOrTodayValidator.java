package org.madhawaa.util.Annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class FutureOrTodayValidator implements ConstraintValidator<FutureOrToday, LocalDateTime> {

    @Override
    public boolean isValid(LocalDateTime date, ConstraintValidatorContext context) {
        if (date == null) {
            return true; // let @NotNull handle null check
        }

        // Only allow today or future (ignore time)
        LocalDate today = LocalDate.now();
        return !date.toLocalDate().isBefore(today);
    }
}
