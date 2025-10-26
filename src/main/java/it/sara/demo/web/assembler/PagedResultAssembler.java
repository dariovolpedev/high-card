package it.sara.demo.web.assembler;

import it.sara.demo.dto.UserDTO;
import it.sara.demo.service.result.PagedResult;
import it.sara.demo.web.user.response.GetUsersResponse;
import it.sara.demo.web.user.response.UserResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PagedResultAssembler {

    GetUsersResponse toGetUsersResponse(PagedResult<UserDTO> result);

}
