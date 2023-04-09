package apap.propensi.mantra.repository;

import apap.propensi.mantra.model.RequestModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RequestDb extends JpaRepository<RequestModel, String> {

    @Query("SELECT r FROM RequestModel r WHERE r.status='MULAI'")
    List<RequestModel> listRequestMulai();

//    RequestModel findByDriverUuid(String uuid);

    RequestModel findById(Long id);
}
