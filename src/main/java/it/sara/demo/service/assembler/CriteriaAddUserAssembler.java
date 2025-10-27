package it.sara.demo.service.assembler;

import it.sara.demo.dto.UserDTO;
import it.sara.demo.service.database.model.User;
import it.sara.demo.service.result.PagedResult;
import it.sara.demo.service.user.criteria.CriteriaAddUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = UserAssembler.class)
public interface CriteriaAddUserAssembler {

    User toUser(CriteriaAddUser criteria);

    @Mapping(target = "items", source = "items")
    PagedResult<UserDTO> toPagedUserDTO(PagedResult<User> pagedUsers);

}
