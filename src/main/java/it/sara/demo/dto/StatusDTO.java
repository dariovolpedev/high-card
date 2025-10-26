package it.sara.demo.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatusDTO {
    private int code;
    private String message;
    private String traceId;
}
