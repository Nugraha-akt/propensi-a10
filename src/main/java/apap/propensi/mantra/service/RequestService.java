package apap.propensi.mantra.service;

import apap.propensi.mantra.helper.CustomUnitPair;
import apap.propensi.mantra.model.RequestModel;
import apap.propensi.mantra.model.UserModel;

import java.util.List;

public interface RequestService {
    RequestModel saveRequest(RequestModel requestModel, List<CustomUnitPair> selectedUnit, UserModel user);
    List<RequestModel> getListRequestMulai();
    List<RequestModel> getListAllRequest();
    List<RequestModel> getListRequest(String username);
    List<RequestModel> getListRequestAktif();
//    RequestModel getRequestByDriverUuid(String uuid);
    RequestModel getRequestById(Long id);
    RequestModel updateRequest(RequestModel request);
}
