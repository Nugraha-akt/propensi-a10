package apap.propensi.mantra.service;

import apap.propensi.mantra.model.AdminModel;
import apap.propensi.mantra.model.CustomerServiceModel;
import apap.propensi.mantra.model.Role;
import apap.propensi.mantra.repository.CustomerServiceDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceServiceImpl implements CustomerServiceService {
    @Autowired
    CustomerServiceDb customerServiceDb;

    @Override
    public CustomerServiceModel addCustomerService(CustomerServiceModel user) {
        CustomerServiceModel newCustomerService = new CustomerServiceModel();

        newCustomerService.setUsername(user.getUsername());
        newCustomerService.setPassword(encrypt(user.getPassword()));
        newCustomerService.setUuid(user.getUuid());
        newCustomerService.setNoTelepon(user.getNoTelepon());
        newCustomerService.setNama(user.getNama());
        newCustomerService.setEmail(user.getEmail());
        newCustomerService.setRole(Role.CUSTOMERSERVICE);

        return customerServiceDb.save(newCustomerService);
    }

    @Override
    public CustomerServiceModel updateCustomerService(CustomerServiceModel user) {
        return customerServiceDb.save(user);
    }

    @Override
    public void deleteCustomerService(CustomerServiceModel user) {
        customerServiceDb.delete(user);
    }

    @Override
    public String encrypt(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        return hashedPassword;
    }

    @Override
    public CustomerServiceModel getCustomerServiceByUuid(String uuid) {
        return customerServiceDb.findByUuid(uuid);
    }

    @Override
    public List<CustomerServiceModel> getListCustomerService() {
        return customerServiceDb.findAll();
    }
}
