package apap.propensi.mantra.repository;

import apap.propensi.mantra.model.SuratModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SuratDb extends JpaRepository<SuratModel, String> {
    Optional<SuratModel> findById(Long id);

    @Query("SELECT s FROM SuratModel s ORDER BY  s.status ASC")
    List<SuratModel> viewSuratOrderByStatus();

    @Query("SELECT COUNT(s) FROM SuratModel s WHERE s.status=1")
    Integer sumGenerated();

    @Query("SELECT COUNT(s) FROM SuratModel s WHERE s.status=2")
    Integer sumSubmitted();

    @Query("SELECT COUNT(s) FROM SuratModel s WHERE s.status=3")
    Integer sumVerified();

    @Query("SELECT COUNT(s) FROM SuratModel s WHERE s.status=4")
    Integer sumRejected();
}
