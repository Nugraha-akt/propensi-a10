package apap.propensi.mantra.repository;

import apap.propensi.mantra.model.RequestModel;
import org.apache.coyote.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Optional;

public interface RequestDb extends JpaRepository<RequestModel, String> {

    @Query("SELECT r FROM RequestModel r WHERE r.status='MULAI'")
    List<RequestModel> listRequestMulai();

    // TODO: Search for requests that are linked to the user
    @Query("SELECT r FROM RequestModel r WHERE r.customer.username = :username")
    List<RequestModel> listRequestUserSpecific(String username);

    // TODO: handle status
    @Query("SELECT r FROM RequestModel r WHERE r.status=''")
    List<RequestModel> listRequestAktif();

//    RequestModel findByDriverUuid(String uuid);

    RequestModel findById(Long id);

    long countByStatus(String status);
    long countByStatusAndCustomerUuid(String status, String customerUuid);
    long countByStatusAndListPairRequest_DriverUuid(String status, String driverUuid);
    @Query("SELECT COUNT(r) FROM RequestModel r WHERE MONTH(r.departDate) = :month")
    long countByDepartDateMonth(@Param("month") int month);

    @Query("SELECT COUNT(r) FROM RequestModel r WHERE MONTH(r.createdAt) = :month")
    long countByCreatedAtMonth(@Param("month") int month);
    long count();
}
