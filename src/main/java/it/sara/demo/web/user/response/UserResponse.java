package it.sara.demo.web.user.response;

public record UserResponse(
        String id,
        String firstName,
        String lastName,
        String email,
        String phoneNumber
) {
}
