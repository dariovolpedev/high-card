package it.sara.demo.service.user.impl;

import it.sara.demo.dto.UserDTO;
import it.sara.demo.exception.DatabaseOperationException;
import it.sara.demo.exception.GenericException;
import it.sara.demo.service.assembler.CriteriaAddUserAssembler;
import it.sara.demo.service.database.UserRepository;
import it.sara.demo.service.database.model.User;
import it.sara.demo.service.result.PagedResult;
import it.sara.demo.service.user.UserService;
import it.sara.demo.service.user.criteria.CriteriaAddUser;
import it.sara.demo.service.user.criteria.CriteriaGetUsers;
import it.sara.demo.service.user.result.AddUserResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final CriteriaAddUserAssembler criteriaAddUserAssembler;

    @Override
    public AddUserResult addUser(CriteriaAddUser criteria) throws Exception {

        AddUserResult returnValue;
        User user;

        try {
            user = criteriaAddUserAssembler.toUser(criteria);
            /*
            simulo in parte una chiamata al metodo save di JPA
            che ritorna l'oggetto salvato
            in questo caso, mi basta lo user id
             */
            String userId = userRepository.save(user);
            if (userId == null) {
                throw new DatabaseOperationException("Error saving user");
            }
            returnValue = new AddUserResult(userId);
        } catch (DatabaseOperationException e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage(), e);
            }
            throw new DatabaseOperationException();
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage(), e);
            }
            throw new GenericException();
        }
        return returnValue;
    }

    @Override
    public PagedResult<UserDTO> getUsers(CriteriaGetUsers criteriaGetUsers) throws Exception {
        return null;
    }
}
