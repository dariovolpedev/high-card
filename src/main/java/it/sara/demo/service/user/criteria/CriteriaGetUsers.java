package it.sara.demo.service.user.criteria;

import it.sara.demo.service.criteria.GenericCriteria;
import lombok.Getter;
import org.springframework.data.domain.Sort;

public record CriteriaGetUsers(
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        int page,
        int size,
        OrderType order
) implements GenericCriteria {

    public CriteriaGetUsers(
            String firstName,
            String lastName,
            String email,
            String phoneNumber,
            int page,
            int size,
            String sortBy,
            String sortDirection
    ) {
        this(
                firstName,
                lastName,
                email,
                phoneNumber,
                page,
                size,
                OrderType.of(sortBy, sortDirection)
        );
    }

    public Sort toSort() {
        return Sort.by(order.isDescending() ? Sort.Direction.DESC : Sort.Direction.ASC);
    }

    @Getter
    public enum OrderType {
        GUID_ASC("guid", false),
        GUID_DESC("guid", true),
        FIRSTNAME_ASC("firstName", false),
        FIRSTNAME_DESC("firstName", true),
        LASTNAME_ASC("lastName", false),
        LASTNAME_DESC("lastName", true),
        EMAIL_ASC("email", false),
        EMAIL_DESC("email", true),
        PHONE_NUMBER_ASC("phoneNumber", false),
        PHONE_NUMBER_DESC("phoneNumber", true);

        private final String field;
        private final boolean descending;

        OrderType(String field, boolean descending) {
            this.field = field;
            this.descending = descending;
        }

        public static OrderType of(String sortBy, String sortDirection) {
            String field = sortBy.toLowerCase();
            boolean desc = "desc".equalsIgnoreCase(sortDirection);

            return switch (field) {
                case "guid" -> desc ? GUID_DESC : GUID_ASC;
                case "lastname" -> desc ? LASTNAME_DESC : LASTNAME_ASC;
                case "email" -> desc ? EMAIL_DESC : EMAIL_ASC;
                case "phonenumber" -> desc ? PHONE_NUMBER_DESC : PHONE_NUMBER_ASC;
                default -> desc ? FIRSTNAME_DESC : FIRSTNAME_ASC;
            };

        }
    }
}
