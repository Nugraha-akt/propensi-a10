package apap.propensi.mantra.service;

import apap.propensi.mantra.model.SuratModel;
import apap.propensi.mantra.repository.SuratDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SuratServiceImpl implements SuratService{

    @Autowired
    private SuratDb suratDb;

    @Override
    public SuratModel getSuratById(Long id){
        Optional<SuratModel> surat = suratDb.findById(id);
        if (surat.isPresent()) {
            return surat.get();
        } else return null;
    }

    @Override
    public SuratModel updateSurat(SuratModel surat) {
        suratDb.save(surat);
        return surat;
    }

    @Override
    public List<SuratModel> getListSuratOrderByStatus(){
        return suratDb.viewSuratOrderByStatus();
    }

    @Override
    public Integer getSumGenerated() {
        return suratDb.sumGenerated();
    }

    @Override
    public Integer getSumSubmitted() {
        return suratDb.sumSubmitted();
    }

    @Override
    public Integer getSumVerified() {
        return suratDb.sumVerified();
    }

    @Override
    public Integer getSumRejected() {
        return suratDb.sumRejected();
    }
}
