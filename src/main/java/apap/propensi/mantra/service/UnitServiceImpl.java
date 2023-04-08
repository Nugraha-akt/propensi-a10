package apap.propensi.mantra.service;

import apap.propensi.mantra.model.UnitModel;
import apap.propensi.mantra.repository.UnitDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
