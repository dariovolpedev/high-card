package it.sara.demo.web.response;


import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class PagedResponse<T> extends GenericResponse implements Serializable {

    private List<T> items;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;

}
