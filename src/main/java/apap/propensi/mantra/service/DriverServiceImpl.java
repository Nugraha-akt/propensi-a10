package apap.propensi.mantra.service;

import apap.propensi.mantra.model.DriverModel;
import apap.propensi.mantra.repository.DriverDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
    public DriverModel getDriverByUuid(String uuid) {
        Optional<DriverModel> driver = driverDb.findByUuid(uuid);
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
    public Integer getSumSedangDitugaskan() {
        return driverDb.sumSedangDitugaskan();
    }

    @Override
    public Integer getSumBelumDitugaskan() {
        return driverDb.sumBelumDitugaskan();
    }

    @Override
    public Integer getSumSudahDitugaskan() {
        return driverDb.sumSudahDitugaskan();
    }

    @Override
    public Integer getSumTidakAktif() {
        return driverDb.sumTidakAktif();
    }
}
