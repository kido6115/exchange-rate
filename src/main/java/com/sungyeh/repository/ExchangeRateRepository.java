package com.sungyeh.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sungyeh.domain.ExchangeRate;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRate,String> {

}
