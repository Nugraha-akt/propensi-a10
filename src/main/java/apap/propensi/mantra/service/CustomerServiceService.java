package apap.propensi.mantra.service;

import apap.propensi.mantra.model.CustomerServiceModel;

import java.util.List;

public interface CustomerServiceService {
    CustomerServiceModel addCustomerService(CustomerServiceModel user);
    CustomerServiceModel updateCustomerService(CustomerServiceModel user);
    void deleteCustomerService(CustomerServiceModel user);
    String encrypt(String password);
    CustomerServiceModel getCustomerServiceByUuid(String uuid);
    CustomerServiceModel getCustomerServiceByUsername(String username);
    List<CustomerServiceModel> getListCustomerService();
}
