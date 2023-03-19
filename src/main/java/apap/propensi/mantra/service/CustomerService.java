package apap.propensi.mantra.service;

import apap.propensi.mantra.model.CustomerModel;

public interface CustomerService {
    CustomerModel getCustomerByUsername(String username);
}
