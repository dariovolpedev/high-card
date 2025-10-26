package it.sara.demo.service.database.model;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String guid;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
}
