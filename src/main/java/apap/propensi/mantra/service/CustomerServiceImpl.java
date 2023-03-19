package apap.propensi.mantra.service;

import apap.propensi.mantra.model.CustomerModel;
import apap.propensi.mantra.repository.CustomerDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService{
    @Autowired
    private CustomerDb customerDb;

    @Override
    public CustomerModel getCustomerByUsername(String username) {
        return customerDb.findByUsername(username);
    }
}
