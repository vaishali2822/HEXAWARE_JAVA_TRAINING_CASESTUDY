package com.hexaware.dao;

import com.hexaware.model.Customer;


public interface ICustomerService {
	Customer getCustomerByID(int customerID);
    Customer getCustomerByUsername(String username);
    void registerCustomer(Customer customerData);
    void updateCustomer(Customer customerData);
    void deleteCustomer(int customerID);
}