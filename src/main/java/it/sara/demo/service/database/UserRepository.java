package it.sara.demo.service.database;

import it.sara.demo.service.database.model.User;
import it.sara.demo.service.result.PagedResult;
import it.sara.demo.service.user.criteria.CriteriaGetUsers;
import it.sara.demo.service.util.StringUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Log4j2
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

    public PagedResult<User> findUsersByFilters(CriteriaGetUsers c) {
        // 1) filtro
        List<User> filtered = FakeDatabase.TABLE_USER.stream()
                .filter(u -> StringUtil.isNullOrEmpty(c.firstName()) || StringUtil.containsIgnoreCase(u.getFirstName(), c.firstName()))
                .filter(u -> StringUtil.isNullOrEmpty(c.lastName()) || StringUtil.containsIgnoreCase(u.getLastName(), c.lastName()))
                .filter(u -> StringUtil.isNullOrEmpty(c.email()) || StringUtil.equalsIgnoreCase(u.getEmail(), c.email()))
                .filter(u -> StringUtil.isNullOrEmpty(c.phoneNumber()) || Objects.equals(u.getPhoneNumber(), c.phoneNumber()))
                .toList();


        // 3) paginazione
        int requestedPage = Math.max(1, c.page());
        int size = Math.max(1, c.size());

        // 2) ordinamento
        Comparator<User> comparator = getUserComparator(c);
        List<User> sorted = new ArrayList<>(filtered);
        sorted.sort(comparator);

        long totalElements = sorted.size();
        int totalPages = (totalElements == 0) ? 0 : (int) ((totalElements + size - 1) / size); // ceil

        int zeroBasedPage = requestedPage - 1;

        long fromL = Math.min((long) zeroBasedPage * size, totalElements);
        long toL = Math.min(fromL + size, totalElements);
        int from = Math.toIntExact(fromL);
        int to = Math.toIntExact(toL);
        List<User> items = sorted.subList(from, to);

        // 5) log di una pseudo-query
        logQuery(c);

        // 6) ritorno arricchito
        return new PagedResult<>(items, requestedPage, size, totalElements, totalPages);
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
