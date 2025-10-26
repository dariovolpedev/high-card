package it.sara.demo.web.user.response;

import it.sara.demo.web.response.PagedResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class GetUsersResponse extends PagedResponse<UserResponse> {
}
