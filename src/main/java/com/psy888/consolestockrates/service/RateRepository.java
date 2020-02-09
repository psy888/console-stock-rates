package com.psy888.consolestockrates.service;

import com.psy888.consolestockrates.model.Rate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RateRepository extends CrudRepository<Rate, Long> {
    List<Rate> findAllBySymbol(String symbol);
    Rate findTopBySymbolOrderByLatestPriceDesc(String symbol);

}
