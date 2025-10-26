package it.sara.demo.web.assembler;

import it.sara.demo.service.user.criteria.CriteriaGetUsers;
import it.sara.demo.web.user.request.GetUsersRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetUsersAssembler {

    CriteriaGetUsers toCriteria(GetUsersRequest request);

}
