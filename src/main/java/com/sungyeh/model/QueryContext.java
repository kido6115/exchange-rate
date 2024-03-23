package com.sungyeh.model;


import java.util.HashMap;
import java.util.Map;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import lombok.Data;

@Data
public class QueryContext {

   public QueryContext(){
        condition=new HashMap<>();
        page=0;
        size=15;
    }

    private Map<String,String> condition;

    private int toltal;

    private int page;

    private int size;
    public Pageable asPageable() {

        return PageRequest.of(page, size, Sort.unsorted());
    }
}
