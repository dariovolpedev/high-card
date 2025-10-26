package it.sara.demo.web.user.request;

import it.sara.demo.web.request.GenericRequest;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record AddUserRequest(
        @NotBlank(message = "First name is required")
        String firstName,
        @NotBlank(message = "Last name is required")
        String lastName,
        @NotBlank(message = "Email is required")
        @Email(message = "Email is invalid")
        String email,
        @NotBlank(message = "Phone is required")
        @Pattern(
                regexp = "^(?:\\+39)?\\s?(?:3\\d{8,9}|0\\d{6,10})$",
                message = "Phone number must be a valid Italian number"
        )
        String phoneNumber
) implements GenericRequest {
}
