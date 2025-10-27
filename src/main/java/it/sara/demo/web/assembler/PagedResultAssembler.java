package it.sara.demo.web.assembler;

import it.sara.demo.dto.UserDTO;
import it.sara.demo.service.result.PagedResult;
import it.sara.demo.web.user.response.GetUsersResponse;
import org.mapstruct.AfterMapping;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

/*
    la combo builder lombok + mapstruct + tipi generici da problemi
    disabilito l'utilizzo del builder per questo mapper
 */
@Mapper(componentModel = "spring",  builder = @Builder(disableBuilder = true))
public interface PagedResultAssembler {

    GetUsersResponse toGetUsersResponse(PagedResult<UserDTO> result);

    @AfterMapping
    default void setMessage(PagedResult<UserDTO> result, @MappingTarget GetUsersResponse response) {
        String message = (result.totalElements() > 0)
                ? "Users found correctly"
                : "No users found";
        response.setMessage(message);
    }

}
