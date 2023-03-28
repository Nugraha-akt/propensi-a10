package apap.propensi.mantra.service;

import apap.propensi.mantra.model.CustomerModel;
import apap.propensi.mantra.model.Role;
import apap.propensi.mantra.repository.CustomerDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService{
    @Autowired
    private CustomerDb customerDb;

    @Override
    public CustomerModel getCustomerByUsername(String username) {
        return customerDb.findByUsername(username);
    }

    @Override
    public CustomerModel getCustomerByUuid (String uuid) {
        return customerDb.findByUuid(uuid);
    }

    @Override
    public CustomerModel addCustomer(CustomerModel user) {
        CustomerModel newCustomer = new CustomerModel();

        newCustomer.setUsername(user.getUsername());
        newCustomer.setPassword(encrypt(user.getPassword()));
        newCustomer.setUuid(user.getUuid());
        newCustomer.setNoTelepon(user.getNoTelepon());
        newCustomer.setNama(user.getNama());
        newCustomer.setRole(Role.CUSTOMER);
        newCustomer.setAsalInstansi(user.getAsalInstansi());
        newCustomer.setListRequest(new ArrayList<>());
        newCustomer.setEmail(user.getEmail());

        return customerDb.save(newCustomer);
    }

    @Override
    public CustomerModel updateCustomer(CustomerModel user) {
        user.setPassword(encrypt(user.getPassword()));

        return customerDb.save(user);
    }

    @Override
    public void deleteCustomer(CustomerModel user) {
        customerDb.delete(user);
    }

    @Override
    public String encrypt(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        return hashedPassword;
    }

    @Override
    public List<CustomerModel> getListCustomer() {
        return customerDb.findAll();
    }

}
