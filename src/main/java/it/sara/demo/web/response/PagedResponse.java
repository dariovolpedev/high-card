package it.sara.demo.web.response;


import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class PagedResponse<T> extends GenericResponse {

    private List<T> items;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;

}
