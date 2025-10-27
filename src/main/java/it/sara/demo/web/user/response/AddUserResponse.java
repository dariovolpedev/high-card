package it.sara.demo.web.user.response;

import it.sara.demo.web.response.GenericResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class AddUserResponse extends GenericResponse {

    private String userId;

    public static AddUserResponse success(String userId) {
        return AddUserResponse.builder()
                .userId(userId)
                .message("User successfully added")
                .build();
    }
}
