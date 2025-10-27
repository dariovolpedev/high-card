package it.sara.demo.web.assembler;

import it.sara.demo.service.user.criteria.CriteriaGetUsers;
import it.sara.demo.web.user.request.GetUsersRequest;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface GetUsersAssembler {

    @Mapping(target = "order",
            expression = "java(it.sara.demo.service.user.criteria.CriteriaGetUsers.OrderType.of(request.sortBy(), request.sortDirection()))")
    CriteriaGetUsers toCriteria(GetUsersRequest request);

}
