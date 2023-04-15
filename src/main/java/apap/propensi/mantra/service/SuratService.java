package apap.propensi.mantra.service;

import apap.propensi.mantra.model.SuratModel;

import java.util.List;

public interface SuratService {
    SuratModel getSuratById(Long id);
    SuratModel updateSurat(SuratModel surat);
    List<SuratModel> getListSuratOrderByStatus();
}
