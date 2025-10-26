package it.sara.demo.service.database;

import it.sara.demo.service.database.model.User;
import it.sara.demo.service.user.criteria.CriteriaGetUsers;
import it.sara.demo.service.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Repository
public class UserRepository {

    public String save(User user) {
        String userId = UUID.randomUUID().toString();
        user.setGuid(userId);
        FakeDatabase.TABLE_USER.add(user);
        return userId;
    }

    public Optional<User> getByGuid(String guid) {
        return FakeDatabase.TABLE_USER.stream().filter(u -> u.getGuid().equals(guid)).findFirst();
    }

    public List<User> getAll() {
        return FakeDatabase.TABLE_USER;
    }

    public List<User> findUsersByFilters(CriteriaGetUsers c) {
        // filtro
        List<User> filtered = FakeDatabase.TABLE_USER.stream()
                .filter(u -> StringUtil.isNullOrEmpty(c.firstName()) || StringUtil.containsIgnoreCase(u.getFirstName(), c.firstName()))
                .filter(u -> StringUtil.isNullOrEmpty(c.lastName()) || StringUtil.containsIgnoreCase(u.getLastName(), c.lastName()))
                .filter(u -> StringUtil.isNullOrEmpty(c.email()) || StringUtil.equalsIgnoreCase(u.getEmail(), c.email()))
                .filter(u -> StringUtil.isNullOrEmpty(c.phoneNumber()) || Objects.equals(u.getPhoneNumber(), c.phoneNumber()))
                .toList();

        // paginazione
        int page = Math.max(0, c.page());
        int size = Math.max(1, c.size());
        int from = Math.min(page * size, filtered.size());
        int to = Math.min(from + size, filtered.size());
        List<User> result = filtered.subList(from, to);

        // ordinamento
        Comparator<User> comparator = getUserComparator(c);
        result.sort(comparator);

        //log di una pseudo-query
        logQuery(c);

        return result;
    }

    private Comparator<User> getUserComparator(CriteriaGetUsers c) {
        Comparator<User> comparator = switch (c.order().getField()) {
            case "guid" -> Comparator.comparing(User::getGuid, Comparator.nullsLast(String::compareToIgnoreCase));
            case "firstName" ->
                    Comparator.comparing(User::getFirstName, Comparator.nullsLast(String::compareToIgnoreCase));
            case "lastName" ->
                    Comparator.comparing(User::getLastName, Comparator.nullsLast(String::compareToIgnoreCase));
            case "email" -> Comparator.comparing(User::getEmail, Comparator.nullsLast(String::compareToIgnoreCase));
            case "phoneNumber" ->
                    Comparator.comparing(User::getPhoneNumber, Comparator.nullsLast(String::compareToIgnoreCase));
            default -> Comparator.comparing(User::getFirstName, Comparator.nullsLast(String::compareToIgnoreCase));
        };

        if (c.order().isDescending()) {
            comparator = comparator.reversed();
        }
        return comparator;
    }

    private void logQuery(CriteriaGetUsers c) {
        String where = Stream.of(
                        StringUtil.isNullOrEmpty(c.firstName()) ? null : "firstName ILIKE '%" + c.firstName() + "%'",
                        StringUtil.isNullOrEmpty(c.lastName()) ? null : "lastName  ILIKE '%" + c.lastName() + "%'",
                        StringUtil.isNullOrEmpty(c.email()) ? null : "LOWER(email) = '" + c.email().toLowerCase() + "'",
                        StringUtil.isNullOrEmpty(c.phoneNumber()) ? null : "phoneNumber = '" + c.phoneNumber() + "'"
                )
                .filter(Objects::nonNull)
                .collect(Collectors.joining(" AND "));

        String order = (c.order() == null)
                ? ""
                : " ORDER BY " + c.order().getField() + " " + (c.order().isDescending() ? "DESC" : "ASC");

        int offset = c.page() * c.size();
        int limit = c.size();

        String query = "SELECT * FROM users"
                + (where.isEmpty() ? "" : " WHERE " + where)
                + order
                + " OFFSET " + offset + " LIMIT " + limit;

        log.info(query);
    }

}
