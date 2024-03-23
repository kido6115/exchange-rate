package com.sungyeh.service;

import java.util.Map;

import org.springframework.data.domain.Page;

import com.sungyeh.domain.ExchangeRate;
import com.sungyeh.model.QueryContext;

public interface ExchangeRateService {

    public ExchangeRate save(ExchangeRate object);

    public ExchangeRate update(ExchangeRate object);

    public void delete(String id);

    public Page<ExchangeRate> findAllByCondition(QueryContext QueryContext);
    
    public ExchangeRate findOne(ExchangeRate object);

    public ExchangeRate findOne(String id);
    
    public void sync();
}
