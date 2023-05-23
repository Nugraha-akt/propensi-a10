package apap.propensi.mantra.service;

import apap.propensi.mantra.model.KomplainModel;
import apap.propensi.mantra.repository.KomplainDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class KomplainServiceImpl implements  KomplainService {
    @Autowired
    KomplainDb komplainDb;

    @Override
    public KomplainModel getKomplainById(Long id) {
        return komplainDb.findById(id);
    }

    @Override
    public KomplainModel updateKomplain(KomplainModel komplain) {
        komplainDb.save(komplain);
        return komplain;
    }

    @Override
    public KomplainModel addKomplain(KomplainModel komplain) {
        komplain.getRequest().setKomplain(komplain);
        komplain.setCreatedAt(LocalDateTime.now());
        komplain.setStatus(0);

        return komplainDb.save(komplain);
    }

    @Override
    public List<KomplainModel> getListAllKomplain() { return komplainDb.findAll(); }

    @Override
    public List<KomplainModel> getListKomplainOrderedByStatus() { return komplainDb.viewKomplainOrderByStatus(); }

    @Override
    public List<KomplainModel> getListKomplainNew() { return komplainDb.viewKomplainNew(); }

    @Override
    public List<KomplainModel> getListKomplainClosed() { return komplainDb.viewKomplainClosed(); }

    @Override
    public Integer getSumNew() {
        return komplainDb.sumNew();
    }

    @Override
    public Integer getSumClosed() {
        return komplainDb.sumClosed();
    }

    @Override
    public Integer getSumLayanan() {
        return komplainDb.sumLayanan();
    }

    @Override
    public Integer getSumUnit() {
        return komplainDb.sumUnit();
    }

    @Override
    public Integer getSumDriver() {
        return komplainDb.sumDriver();
    }

    @Override
    public Integer getSumWaktu() {
        return komplainDb.sumWaktu();
    }

    @Override
    public Integer getSumLainnya() {
        return komplainDb.sumLainnya();
    }

    @Override
    public long getAllCount() { return komplainDb.count(); }
}
