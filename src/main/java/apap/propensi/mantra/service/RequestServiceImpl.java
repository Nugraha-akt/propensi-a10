package apap.propensi.mantra.service;

import apap.propensi.mantra.helper.CustomUnitPair;
import apap.propensi.mantra.model.*;
import apap.propensi.mantra.repository.RequestDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class RequestServiceImpl implements RequestService{

    @Autowired
    private RequestDb requestDb;

    @Autowired
    private UnitService unitService;

    @Override
    public RequestModel saveRequest(RequestModel requestModel, List<CustomUnitPair> selectedUnit, UserModel user) {
        requestModel.setCreatedAt(LocalDateTime.now());
        requestModel.setStatus("Created");
        requestModel.setStatusPerjalanan("");
        requestModel.setListPairRequest(new ArrayList<>());
        requestModel.setCustomer((CustomerModel) user);
        for (CustomUnitPair customUnitPair: selectedUnit) {
            System.out.println(customUnitPair.getJenis());
            List<UnitModel> listUnit= unitService.getUnitByJenisandJumlah(customUnitPair.getJenis(), customUnitPair.getJumlah(), requestModel.getDepartDate(), requestModel.getReturnDate());
            for (UnitModel unit: listUnit) {
                System.out.println(unit.getPlatNomor()); //Debugging Hardcode
                requestModel.getListPairRequest().add(new PairUnitDriverModel(unit, requestModel));
            }
        }
        return requestDb.save(requestModel);
    }

    @Override
    public List<RequestModel> getListRequestMulai() {
        return requestDb.listRequestMulai();
    }

    @Override
    public List<RequestModel> getListAllRequest() { return requestDb.findAll(); }

    @Override
    public List<RequestModel> getListRequest(String username) { return requestDb.listRequestUserSpecific(username); }

    @Override
    public List<RequestModel> getListRequestAktif() { return requestDb.listRequestAktif(); }

//    @Override
//    public RequestModel getRequestByDriverUuid(String uuid) {
//        return requestDb.findByDriverUuid(uuid);
//    }

    @Override
    public RequestModel getRequestById(Long id) {
        return requestDb.findById(id);
    }

    @Override
    public RequestModel updateRequest(RequestModel request) {
        requestDb.save(request);
        return request;
    }
}
