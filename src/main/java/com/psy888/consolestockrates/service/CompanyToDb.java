package com.psy888.consolestockrates.service;

import com.psy888.consolestockrates.model.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CompanyToDb {

    @Autowired
    CompanyRepository companyRepository;

    public void saveToDb(List<Company> companies) {
//        companies.forEach(company -> {
//            if (companyRepository.findCompanyBySymbol(company.getSymbol()).isEmpty()) {
                companies.forEach(companyRepository::save);
//            }
//        });
    }

}
