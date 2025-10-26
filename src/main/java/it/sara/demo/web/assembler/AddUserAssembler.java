package it.sara.demo.web.assembler;

import it.sara.demo.service.user.criteria.CriteriaAddUser;
import it.sara.demo.web.user.request.AddUserRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddUserAssembler {

    CriteriaAddUser toCriteria(AddUserRequest addUserRequest);

}
