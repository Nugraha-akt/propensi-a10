package apap.propensi.mantra.service;

import apap.propensi.mantra.model.RequestModel;

import java.util.List;

public interface RequestService {
    List<RequestModel> getListRequestMulai();
//    RequestModel getRequestByDriverUuid(String uuid);
    RequestModel getRequestById(Long id);
    RequestModel updateRequest(RequestModel request);
}
