package com.psy888.consolestockrates.service;

import com.psy888.consolestockrates.model.Rate;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RateRepository extends CrudRepository<Rate, Long> {

//    List<Rate> findTop5ByLatestPrice();

    //    List<Rate> findTopByLatestPriceOrderByLatestPrice();
    List<Rate> findAll();

//    @Query("select r,c from Rate r,Company c join fetch r.company where r.symbol=c.symbol")
//    @Query("select r from Rate r order by r.latestPrice desc limit 5")

    List<Rate> findTop5ByOrderByLatestPriceDesc();
    //todo min value??
    List<Rate> findTop5ByOrderByChangePercentDesc();

//    List<Rate> findTop5OrderByLatestPrice();

//    List<Rate> findTop5OrderByChangePercent();
//    List<Rate> findTop5ByChangePercent();

    Rate findBySymbol(String symbol);
//    @Query("update Employees e set e.firstName = ?1 where e.employeeId = ?2")
//    @Query("select r, c from Rate r, Company c fetch all properties company where r.symbol = c.symbol ")
//    select c from Child c join fetch c.parent where c.id = :id


}
