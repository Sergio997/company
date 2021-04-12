package com.company.dao.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class PageResponse<T> {

    @EqualsAndHashCode.Include
    @ToString.Include
    private Long totalPages;

    @EqualsAndHashCode.Include
    @ToString.Include
    private Long pageSize;

    @EqualsAndHashCode.Include
    @ToString.Include
    private Long totalElements;

    private List<T> content = new ArrayList<>();

}