package it.sara.demo.service.user.result;

import it.sara.demo.service.result.GenericResult;
import lombok.*;

public record AddUserResult(String userId) implements GenericResult {
}
