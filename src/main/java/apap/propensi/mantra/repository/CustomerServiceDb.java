package apap.propensi.mantra.repository;

import apap.propensi.mantra.model.CustomerServiceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerServiceDb extends JpaRepository<CustomerServiceModel, Long> {
    CustomerServiceModel findByUuid(String uuid);
    CustomerServiceModel findByUsername(String username);
}
