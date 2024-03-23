package com.sungyeh.domain;

import java.time.LocalDate;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


@Entity
@Table(name="exchange_rate")
@Data
public class ExchangeRate {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(name="id_")
    private String id;

    @Column(name="date_")
    private LocalDate date;

    @Column(name="currency_")
    private String currency;

    @Column(name="rate_")
    private Double rate;
}
