package apap.propensi.mantra.service;

import apap.propensi.mantra.model.DriverModel;
import apap.propensi.mantra.model.Role;
import apap.propensi.mantra.repository.DriverDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DriverServiceImpl implements DriverService{
    @Autowired
    private DriverDb driverDb;

    @Override
    public List<DriverModel> getListDriverOrderByStatus() {
        return driverDb.viewDriverOrderByStatus();
    }

    @Override
    public List<DriverModel> getListDriver() {
        return driverDb.findAll();
    }

    @Override
    public List<DriverModel> getListAvailableDriver() {
        return driverDb.findAvailableDriver();
    }

    @Override
    public DriverModel addDriver(DriverModel user) {
        DriverModel newDriver = new DriverModel();

        newDriver.setUsername(user.getUsername());
        newDriver.setPassword(encrypt(user.getPassword()));
        newDriver.setUuid(user.getUuid());
        newDriver.setNoTelepon(user.getNoTelepon());
        newDriver.setNama(user.getNama());
        newDriver.setEmail(user.getEmail());
        newDriver.setRole(Role.DRIVER);
        newDriver.setSim(user.getSim());
        newDriver.setStatus(1);
        newDriver.setListPairRequest(new ArrayList<>());

        return driverDb.save(newDriver);
    }

    @Override
    public void deleteDriver(DriverModel user) {
        driverDb.delete(user);
    }

    @Override
    public String encrypt(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        return hashedPassword;
    }

    @Override
    public DriverModel getDriverByUuid(String uuid) {
        Optional<DriverModel> driver = driverDb.findByUuid(uuid);
        if (driver.isPresent()) {
            return driver.get();
        } else return null;
    }

    @Override
    public DriverModel getDriverByUsername(String username) {
        Optional<DriverModel> driver = driverDb.findByUsername(username);
        if (driver.isPresent()) {
            return driver.get();
        } else return null;
    }

    @Override
    public DriverModel updateDriver(DriverModel driver) {
        driverDb.save(driver);
        return driver;
    }

    @Override
    public Integer getSumAvailable() {
        return driverDb.sumAvailable();
    }

    @Override
    public Integer getSumUnavailable() {
        return driverDb.sumUnavailable();
    }

    @Override
    public Integer getSumInactive() {
        return driverDb.sumInactive();
    }
}
