package apap.propensi.mantra.service;

import apap.propensi.mantra.model.RequestModel;
import apap.propensi.mantra.repository.RequestDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class RequestServiceImpl implements RequestService{

    @Autowired
    private RequestDb requestDb;

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
