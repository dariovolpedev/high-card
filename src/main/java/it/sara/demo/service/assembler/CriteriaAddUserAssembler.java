package it.sara.demo.service.assembler;

import it.sara.demo.dto.UserDTO;
import it.sara.demo.service.database.model.User;
import it.sara.demo.service.result.PagedResult;
import it.sara.demo.service.user.criteria.CriteriaAddUser;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CriteriaAddUserAssembler {

    User toUser(CriteriaAddUser criteria);
    PagedResult<UserDTO> toPadegUserDTO(PagedResult<User> pagedUsers);

}
