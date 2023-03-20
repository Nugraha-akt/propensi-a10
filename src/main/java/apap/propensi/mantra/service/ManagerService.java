package apap.propensi.mantra.service;

import apap.propensi.mantra.model.ManagerModel;

import java.util.List;

public interface ManagerService {
    ManagerModel addManager(ManagerModel user);
    ManagerModel updateManager(ManagerModel user);
    void deleteManager(ManagerModel user);
    String encrypt(String password);
    ManagerModel getManagerByUuid(String uuid);
    List<ManagerModel> getListManager();
}
