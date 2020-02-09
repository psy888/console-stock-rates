package com.psy888.consolestockrates.service;

import com.psy888.consolestockrates.model.Company;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends CrudRepository<Company, Long> {

    List<Company> findCompanyBySymbol(String symbol);
    List<Company> findAllByIsEnabledContaining(String isEnabled);
    List<Company> findTop50ByIsEnabledContaining(String isEnabled);


}
