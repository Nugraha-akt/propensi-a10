package apap.propensi.mantra.service;

import apap.propensi.mantra.helper.CustomUnitPair;
import apap.propensi.mantra.model.UnitModel;
import apap.propensi.mantra.repository.UnitDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class UnitServiceImpl implements  UnitService {
    @Autowired
    private UnitDb unitDb;

    @Override
    public void addUnit(UnitModel unit){
        unitDb.save(unit);
    }

    @Override
    public List<UnitModel> getListUnit(){
        return unitDb.findAll();
    }

    @Override
    public UnitModel getUnitByPlat(String plat){
        return  unitDb.findByPlatNomor(plat);
    }

    @Override
    public UnitModel getUnitById(long id){
        return unitDb.findById(id);
    }
    @Override
    public void deleteUnit(UnitModel unit){
        unitDb.delete(unit);
    }

    @Override
    public List<CustomUnitPair> getUnitAvailable(LocalDateTime departDate, LocalDateTime returnDate) {
        return unitDb.findAvailableUnitByDate(departDate, returnDate);
    }

    @Override
    public List<UnitModel> getUnitByJenisandJumlah(String jenis, Long jumlah, LocalDateTime departDate, LocalDateTime returnDate) {
        return unitDb.findAvailableUnitByJenisandJumlah(jenis, jumlah, departDate, returnDate);
    }
}
