package apap.propensi.mantra.service;

import apap.propensi.mantra.model.KomplainModel;

import java.util.List;

public interface KomplainService {

    KomplainModel getKomplainById(Long id);
    KomplainModel updateKomplain(KomplainModel komplain);
    KomplainModel addKomplain(KomplainModel komplain);
    List<KomplainModel> getListAllKomplain();
    List<KomplainModel> getListKomplainOrderedByStatus();
    List<KomplainModel> getListKomplainNew();
    List<KomplainModel> getListKomplainClosed();
    Integer getSumNew();
    Integer getSumClosed();
    Integer getSumLayanan();
    Integer getSumUnit();
    Integer getSumDriver();
    Integer getSumWaktu();
    Integer getSumLainnya();
}
