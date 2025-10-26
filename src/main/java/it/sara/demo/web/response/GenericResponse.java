package it.sara.demo.web.response;


import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder(builderMethodName = "genericResponseBuilder")
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GenericResponse {

    private String message;

}
