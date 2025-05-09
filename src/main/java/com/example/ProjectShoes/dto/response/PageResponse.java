package com.example.ProjectShoes.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
public  class PageResponse<T> {
    private int pageNumber;
    private int pageSize;
    private int totalPages;
    private long totalElements;
    private List<T> data;

//    public PageResponse(Page<T> page) {
//        this.pageNumber = page.getNumber();
//        this.pageSize = page.getSize();
//        this.totalPages = page.getTotalPages();
//        this.totalElements = page.getTotalElements();
//        this.data = page.getContent();
//    }
}
