package apap.propensi.mantra.repository;

import apap.propensi.mantra.model.UnitModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnitDb extends JpaRepository<UnitModel, Long> {
    UnitModel findByPlatNomor(String plat);

    UnitModel findById(long id);
}
