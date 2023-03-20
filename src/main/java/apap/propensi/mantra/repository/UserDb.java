package apap.propensi.mantra.repository;

import apap.propensi.mantra.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDb extends JpaRepository<UserModel, Long>{
    UserModel findByUsername(String username);
    UserModel findByEmail(String email);
    UserModel findByUuid(String uuid);
    @Query("SELECT c FROM UserModel c ORDER BY c.role ASC")
    List<UserModel> findAllOrderByRoleAsc();

    @Query("SELECT c.username FROM UserModel c")
    List<String> getAllUsername();
    @Query("SELECT c.email FROM UserModel c")
    List<String> getAllEmail();
}
