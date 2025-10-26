package it.sara.demo.web.user;

import it.sara.demo.dto.UserDTO;
import it.sara.demo.service.result.PagedResult;
import it.sara.demo.service.user.UserService;
import it.sara.demo.service.user.criteria.CriteriaAddUser;
import it.sara.demo.service.user.criteria.CriteriaGetUsers;
import it.sara.demo.service.user.result.AddUserResult;
import it.sara.demo.web.assembler.AddUserAssembler;
import it.sara.demo.web.assembler.GetUsersAssembler;
import it.sara.demo.web.assembler.PagedResultAssembler;
import it.sara.demo.web.user.request.AddUserRequest;
import it.sara.demo.web.user.request.GetUsersRequest;
import it.sara.demo.web.user.response.AddUserResponse;
import it.sara.demo.web.user.response.GetUsersResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AddUserAssembler addUserAssembler;
    private final GetUsersAssembler getUsersAssembler;
    private final PagedResultAssembler pagedResultAssembler;

    @PutMapping("/v1/users")
    public ResponseEntity<AddUserResponse> addUser(@RequestBody @Valid AddUserRequest request) throws Exception {
        CriteriaAddUser addUserCriteria = addUserAssembler.toCriteria(request);
        AddUserResult result = userService.addUser(addUserCriteria);
        return ResponseEntity.ok(AddUserResponse.success(result.userId()));
    }

    @PostMapping("/v1/user")
    public ResponseEntity<GetUsersResponse> getUsers(@RequestBody @Valid GetUsersRequest request) throws Exception {
        CriteriaGetUsers getUsersCriteria = getUsersAssembler.toCriteria(request);
        PagedResult<UserDTO> result = userService.getUsers(getUsersCriteria);
        GetUsersResponse response = pagedResultAssembler.toGetUsersResponse(result);
        return ResponseEntity.ok(response);
    }

}
