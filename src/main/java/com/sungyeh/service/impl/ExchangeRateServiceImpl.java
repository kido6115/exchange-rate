package com.sungyeh.service.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.sungyeh.domain.ExchangeRate;
import com.sungyeh.model.QueryContext;
import com.sungyeh.repository.ExchangeRateRepository;
import com.sungyeh.service.ExchangeRateService;

import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Service
@Transactional
public class ExchangeRateServiceImpl implements ExchangeRateService{

    @Resource
    private ExchangeRateRepository repository;

    @Resource
    private EntityManager manager;

    @Override
    public ExchangeRate save(ExchangeRate object) {
       return  repository.save(object);
    }

    @Override
    public ExchangeRate update(ExchangeRate object) {
    
        ExchangeRate  target=findOne(object);

        if(target!=null){
           target.setCurrency(object.getCurrency());
           target.setRate(object.getRate());
           target.setDate(object.getDate());
        }
        return target;
    }

    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }

    @Override
    public Page<ExchangeRate> findAllByCondition(QueryContext queryContext) {

        CriteriaBuilder cb = manager.getCriteriaBuilder();
        CriteriaQuery<Long> countCq = cb.createQuery(Long.class);
        Root<ExchangeRate> countRoot = countCq.from(ExchangeRate.class);
        countCq.select(cb.count(countRoot));
        countCq.where(cb.and(conditionPredicate(queryContext, countRoot).toArray(new Predicate[0])));
        long total=manager.createQuery(countCq).getSingleResult();

        Pageable pageable = queryContext.asPageable();
        int firstResult = pageable.getPageNumber() * pageable.getPageSize();
        int maxResults = pageable.getPageSize();
        CriteriaQuery<ExchangeRate> cq = cb.createQuery(ExchangeRate.class);
        Root<ExchangeRate> root = cq.from(ExchangeRate.class);        
        cq.where(cb.and(conditionPredicate(queryContext, root).toArray(new Predicate[0])));
        TypedQuery<ExchangeRate> query = manager.createQuery(cq);
        query.setFirstResult(firstResult).setMaxResults(maxResults);

        return  new PageImpl<>(query.getResultList(), pageable, total);
    }

    @Override
    public ExchangeRate findOne(ExchangeRate object) {
       return findOne(object.getId());
    }

    @Override
    public ExchangeRate findOne(String id) {
        Optional<ExchangeRate> target=  repository.findById(id);
        if(target.isPresent()){
            return target.get();
        }else{
            return null;
        }
    }


    @Value("${sungyeh.url}")
    private String url;

    @Override
    public void sync() {
        RestTemplate restTemplate=new RestTemplate();
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();        
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();

        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));        
        messageConverters.add(converter);  
        restTemplate.setMessageConverters(messageConverters); 
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("accept", "application/json");
                   List<Map<String,String>> responseEntity = restTemplate.exchange(
               url,
                HttpMethod.GET,
                new HttpEntity<Object>(headers),
                List.class
        ).getBody();
        List<ExchangeRate> datas= new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        responseEntity.forEach(m->{
            LocalDate localDate = LocalDate.parse(m.get("Date"), formatter);
            m.forEach((k,v)->{
                if(!"Date".equals(k)){
                    ExchangeRate rate=new ExchangeRate();
                    rate.setCurrency(k);
                    rate.setRate(Double.parseDouble(v));
                    rate.setDate(localDate);
                    datas.add(rate);
                }
            
            });
        });
        repository.saveAll(datas);
    }

    private List<Predicate> conditionPredicate(QueryContext queryContext,Root root){
        CriteriaBuilder cb = manager.getCriteriaBuilder();
        Map<String,String> condition=queryContext.getCondition();
        List<Predicate> predicates=new ArrayList<>();
        if(StringUtils.hasText(condition.get("currency"))){
            predicates.add(cb.equal(root.get("currency"), condition.get("currency")));
        }
        if(StringUtils.hasText(condition.get("date"))){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(condition.get("date"), formatter);

            predicates.add(cb.equal(root.get("date"), localDate));
        }
        return predicates;
    }
}
