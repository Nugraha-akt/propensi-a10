package apap.propensi.mantra.repository;

import apap.propensi.mantra.model.ManagerModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagerDb extends JpaRepository<ManagerModel, Long> {
    ManagerModel findByUuid(String uuid);
}
