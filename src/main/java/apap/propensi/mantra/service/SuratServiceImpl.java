package apap.propensi.mantra.service;

import apap.propensi.mantra.model.PairUnitDriverModel;
import apap.propensi.mantra.model.RequestModel;
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
    @Override
    public List<SuratModel> getListSurat() {
        return suratDb.findAll();
    }

    @Override
    public SuratModel getSurat(long id){
        ///terdapat perubahan, method ini mengikuti method getSuratById yang ada diatas
        Optional<SuratModel> surat = suratDb.findById(id);
        if (surat.isPresent()) {
            return surat.get();
        } else return null;
    }

    @Override
    public void deleteSurat(SuratModel surat) {suratDb.delete(surat);}

    @Override
    public void generateSurat(RequestModel request){
            for(PairUnitDriverModel pair : request.getListPairRequest()){
                SuratModel surat = new SuratModel();
                surat.setStatus(1);
                surat.setDriver(pair.getDriver());
                surat.setRequest(request);
                surat.setId(suratDb.count()+1);
                surat.setNoSurat(surat.getId().toString());
                suratDb.save(surat);
        }
    }
}
