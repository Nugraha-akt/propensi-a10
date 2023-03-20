package apap.propensi.mantra.service;

import apap.propensi.mantra.model.DriverModel;

import java.util.List;

public interface DriverService {
    List<DriverModel> getListDriverOrderByStatus();
    DriverModel getDriverByUuid(String uuid);
    DriverModel getDriverByUsername(String username);
    DriverModel updateDriver(DriverModel driver);
    Integer getSumSedangDitugaskan();
    Integer getSumBelumDitugaskan();
    Integer getSumSudahDitugaskan();
    Integer getSumTidakAktif();
}
