package com.sungyeh.repository;

import static org.mockito.ArgumentMatchers.eq;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import com.sungyeh.domain.ExchangeRate;

import jakarta.annotation.Resource;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class ExchangeRateRepositoryTest {

    @Resource
    private ExchangeRateRepository repository;

    @Test
    public void crudTest(){
        ExchangeRate expect=new ExchangeRate();
        expect.setCurrency("USD");
        expect.setDate(LocalDate.now());
        expect.setRate(5.000);

        ExchangeRate rate=new ExchangeRate();
        rate.setCurrency(expect.getCurrency());
        rate.setDate(expect.getDate());
        rate.setRate(expect.getRate());
        repository.save(rate);
        Assertions.assertNotNull(rate.getId());
        ExchangeRate target=repository.findById(rate.getId()).get();
        Assertions.assertNotNull(target);
        Assertions.assertEquals(expect.getRate(), target.getRate());
        Assertions.assertEquals(expect.getCurrency(), target.getCurrency());
        Assertions.assertEquals(expect.getDate(), target.getDate());
        expect.setRate(6.0);
        rate.setRate(expect.getRate());
        repository.save(rate);
        ExchangeRate update=repository.findById(rate.getId()).get();
        Assertions.assertEquals(update.getRate(), expect.getRate());
        repository.deleteById(rate.getId());
        Assertions.assertFalse(repository.findById(rate.getId()).isPresent());;
    }


}
