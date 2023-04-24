package apap.propensi.mantra.service;

import apap.propensi.mantra.helper.CustomUnitPair;
import apap.propensi.mantra.model.UnitModel;
import org.springframework.data.util.Pair;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface UnitService {
    void addUnit(UnitModel unit);

    List<UnitModel> getListUnit();

    UnitModel getUnitByPlat(String plat);

    UnitModel getUnitById(long id);

    void deleteUnit(UnitModel unit);

    List<CustomUnitPair> getUnitAvailable(LocalDateTime departDate, LocalDateTime returnDate);

    List<UnitModel> getUnitByJenisandJumlah(String jenis, Long jumlah, LocalDateTime departDate, LocalDateTime returnDate);
}
