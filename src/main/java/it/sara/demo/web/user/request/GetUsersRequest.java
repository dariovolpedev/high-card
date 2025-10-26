package it.sara.demo.web.user.request;

import it.sara.demo.web.request.GenericRequest;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record GetUsersRequest(
        String firstName,
        String lastName,
        @Email(message = "Email is invalid")
        String email,
        @Pattern(
                regexp = "^(?:\\+39)?\\s?(?:3\\d{8,9}|0\\d{6,10})$",
                message = "Phone number must be a valid Italian number"
        )
        String phoneNumber,
        @NotNull(message = "Page number is required")
        Integer page,
        @NotNull(message = "Page size is required")
        Integer size,
        @NotNull(message = "SortBy is required")
        String sortBy,
        String sortDirection
) implements GenericRequest {

}
