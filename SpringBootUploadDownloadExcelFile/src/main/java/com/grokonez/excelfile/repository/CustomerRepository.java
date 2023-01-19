package com.grokonez.excelfile.repository;

import org.springframework.data.repository.CrudRepository;

import com.grokonez.excelfile.model.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long>{
}
