package apap.propensi.mantra.repository;

import apap.propensi.mantra.helper.CustomUnitPair;
import apap.propensi.mantra.model.UnitModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface UnitDb extends JpaRepository<UnitModel, Long> {
    UnitModel findByPlatNomor(String plat);

    UnitModel findById(long id);

    @Query("SELECT new apap.propensi.mantra.helper.CustomUnitPair(u.jenis, COUNT(u)) " +
            "FROM UnitModel u " +
            "LEFT JOIN u.listPairRequest p " +
            "WHERE u.status = 1 " +
            "AND NOT EXISTS (SELECT 1 FROM PairUnitDriverModel pu WHERE pu.unit = u AND ((pu.request.departDate <= :returnDate AND pu.request.returnDate >= :departDate) OR (pu.request.departDate >= :departDate AND pu.request.departDate <= :returnDate))) " +
            "GROUP BY u.jenis")
    List<CustomUnitPair> findAvailableUnitByDate(LocalDateTime departDate, LocalDateTime returnDate);

    @Query(value = "SELECT * FROM unit " +
            "WHERE jenis = :jenis " +
            "AND status = 1 " +
            "AND id NOT IN (SELECT unit_uuid FROM pair_unit_driver " +
            "JOIN request ON pair_unit_driver.request_uuid = request.id " +
            "WHERE (request.depart_date <= :returnDate AND request.return_date >= :departDate) " +
            "OR (request.depart_date >= :departDate AND request.depart_date <= :returnDate)) " +
            "LIMIT :jumlah", nativeQuery = true)
    List<UnitModel> findAvailableUnitByJenisandJumlah(String jenis, Long jumlah, LocalDateTime departDate, LocalDateTime returnDate);
}
