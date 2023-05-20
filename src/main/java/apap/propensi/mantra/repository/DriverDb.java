package apap.propensi.mantra.repository;

import apap.propensi.mantra.model.DriverModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DriverDb extends JpaRepository<DriverModel, String> {
    @Query("SELECT d FROM DriverModel d ORDER BY d.status ASC")
    List<DriverModel> viewDriverOrderByStatus();

    Optional<DriverModel> findByUuid(String uuid);

    Optional<DriverModel> findByUsername(String username);

    @Query("SELECT d from DriverModel d WHERE d.status=1")
    List<DriverModel> findAvailableDriver();

    @Query("SELECT COUNT(d) FROM DriverModel d WHERE d.status=1")
    Integer sumAvailable();

    @Query("SELECT COUNT(d) FROM DriverModel d WHERE d.status=2")
    Integer sumUnavailable();

    @Query("SELECT COUNT(d) FROM DriverModel d WHERE d.status=3")
    Integer sumInactive();
}
