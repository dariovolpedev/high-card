package it.sara.demo.service.user.criteria;

import it.sara.demo.service.criteria.GenericCriteria;

public record CriteriaAddUser(
        String firstName,
        String lastName,
        String email,
        String phoneNumber)
        implements GenericCriteria {
}
