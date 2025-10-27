package it.sara.demo.web.user.response;

import it.sara.demo.web.response.PagedResponse;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class GetUsersResponse extends PagedResponse<UserResponse> implements Serializable {
}
