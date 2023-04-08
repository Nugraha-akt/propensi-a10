package apap.propensi.mantra.service;

import apap.propensi.mantra.model.UnitModel;

import java.util.List;

public interface UnitService {
    void addUnit(UnitModel unit);

    List<UnitModel> getListUnit();

    UnitModel getUnitByPlat(String plat);

    UnitModel getUnitById(long id);

    void deleteUnit(UnitModel unit);
}
