package apap.propensi.mantra.service;

import apap.propensi.mantra.model.CustomerModel;

import java.util.List;

public interface CustomerService {
    CustomerModel getCustomerByUsername(String username);
    CustomerModel getCustomerByUuid(String uuid);
    CustomerModel addCustomer(CustomerModel user);
    CustomerModel updateCustomer(CustomerModel user);
    void deleteCustomer(CustomerModel user);
    String encrypt(String password);
    List<CustomerModel> getListCustomer();

}
