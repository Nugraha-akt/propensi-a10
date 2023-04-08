package apap.propensi.mantra.repository;

import apap.propensi.mantra.model.AdminModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminDb extends JpaRepository<AdminModel, Long> {
    AdminModel findByUuid(String uuid);
}
