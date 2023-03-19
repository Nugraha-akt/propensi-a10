package apap.propensi.mantra.repository;

import apap.propensi.mantra.model.CustomerModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerDb extends JpaRepository<CustomerModel, Long> {
        CustomerModel findByUsername(String username);
}
