package apap.propensi.mantra.repository;

import apap.propensi.mantra.model.KomplainModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface KomplainDb extends JpaRepository<KomplainModel, String> {
    KomplainModel findById(Long id);

    @Query("SELECT k FROM KomplainModel k ORDER BY  k.status ASC")
    List<KomplainModel> viewKomplainOrderByStatus();

    @Query("SELECT k FROM KomplainModel k WHERE k.status=1 ORDER BY k.respondedAt DESC")
    List<KomplainModel> viewKomplainClosed();

    @Query("SELECT k FROM KomplainModel k WHERE k.status=0 ORDER BY k.createdAt DESC")
    List<KomplainModel> viewKomplainNew();

    @Query("SELECT COUNT(k) FROM KomplainModel k WHERE k.status=0")
    Integer sumNew();

    @Query("SELECT COUNT(k) FROM KomplainModel k WHERE k.status=1")
    Integer sumClosed();

    @Query("SELECT COUNT(k) FROM KomplainModel k WHERE k.kategoriKomplain='SERVICE'")
    Integer sumLayanan();

    @Query("SELECT COUNT(k) FROM KomplainModel k WHERE k.kategoriKomplain='UNIT'")
    Integer sumUnit();

    @Query("SELECT COUNT(k) FROM KomplainModel k WHERE k.kategoriKomplain='DRIVER'")
    Integer sumDriver();

    @Query("SELECT COUNT(k) FROM KomplainModel k WHERE k.kategoriKomplain='TIME'")
    Integer sumWaktu();

    @Query("SELECT COUNT(k) FROM KomplainModel k WHERE k.kategoriKomplain='OTHER'")
    Integer sumLainnya();

    long count();
}
