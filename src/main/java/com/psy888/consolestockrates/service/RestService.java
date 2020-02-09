package com.psy888.consolestockrates.service;

import com.psy888.consolestockrates.model.Company;

import java.util.List;

public interface RestService<T> {
    T getData(String Service);
}
