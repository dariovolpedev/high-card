package it.sara.demo.service.user;

import it.sara.demo.dto.UserDTO;
import it.sara.demo.service.result.PagedResult;
import it.sara.demo.service.user.criteria.CriteriaAddUser;
import it.sara.demo.service.user.criteria.CriteriaGetUsers;
import it.sara.demo.service.user.result.AddUserResult;

public interface UserService {

    AddUserResult addUser(CriteriaAddUser addUserRequest) throws Exception;

    PagedResult<UserDTO> getUsers(CriteriaGetUsers criteriaGetUsers) throws Exception;

}
