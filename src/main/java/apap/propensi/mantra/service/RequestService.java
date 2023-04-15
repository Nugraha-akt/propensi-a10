package apap.propensi.mantra.service;

import apap.propensi.mantra.model.RequestModel;

import java.util.List;

public interface RequestService {
    List<RequestModel> getListRequestMulai();
    List<RequestModel> getListAllRequest();
    List<RequestModel> getListRequest(String username);
    List<RequestModel> getListRequestAktif();
//    RequestModel getRequestByDriverUuid(String uuid);
    RequestModel getRequestById(Long id);
    RequestModel updateRequest(RequestModel request);
}
