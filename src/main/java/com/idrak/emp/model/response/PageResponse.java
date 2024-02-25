package com.idrak.emp.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageResponse<T> {

    private long itemsCount;

    private int currentPage;

    private long pageCount;

    private int pageSize;

    private List<T> items;

}