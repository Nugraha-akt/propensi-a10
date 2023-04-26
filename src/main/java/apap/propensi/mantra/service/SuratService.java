package apap.propensi.mantra.service;

import apap.propensi.mantra.model.RequestModel;
import apap.propensi.mantra.model.SuratModel;

import java.util.List;

public interface SuratService {
    SuratModel getSuratById(Long id);
    SuratModel updateSurat(SuratModel surat);
    List<SuratModel> getListSuratOrderByStatus();
    Integer getSumGenerated();
    Integer getSumSubmitted();
    Integer getSumVerified();
    Integer getSumRejected();
    List<SuratModel> getListSurat();
    SuratModel getSurat(long id);
    void deleteSurat(SuratModel surat);

    void generateSurat(RequestModel request);
}
