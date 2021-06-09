package com.retailapp.proto.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import org.springframework.data.domain.Page;

public class PagedListDTO<T> {
    
    private List<T> entries;
    private int totalPages;

    @JsonInclude(Include.NON_NULL)
    private Integer nextPage;
    private boolean hasNext;

    @JsonInclude(Include.NON_NULL)
    private Integer previousPage;
    private boolean hasPrevious;

    public static <P> PagedListDTO<P> create(Page<P> page) {
        PagedListDTO<P> dto = new PagedListDTO<>();
        dto.entries = page.toList();
        dto.totalPages = page.getTotalPages();
        if (page.hasNext()) {
            dto.hasNext = true;
            dto.nextPage = page.getNumber() + 2; // 1起点のpage numberにする
        }

        if (page.hasPrevious()) {
            dto.hasPrevious = true;
            dto.previousPage = page.getNumber() + 2; // 1起点のpage numberにする
        }
        return dto;
    }

    public List<T> getEntries() {
        return this.entries;
    }

    public int getTotalPages() {
        return this.totalPages;
    }

    public Integer getNextPage() {
        return this.nextPage;
    }

    public boolean getHasNext() {
        return this.hasNext;
    }

    public Integer getPreviousPage() {
        return this.previousPage;
    }

    public boolean getHasPrevious() {
        return this.hasPrevious;
    }

}
