package apap.propensi.mantra.service;

import apap.propensi.mantra.model.AdminModel;

import java.util.List;

public interface AdminService {
    AdminModel addAdmin(AdminModel user);
    AdminModel updateAdmin(AdminModel user);
    void deleteAdmin(AdminModel user);
    List<AdminModel> getListAdmin();
    AdminModel getAdminByUuid(String uuid);
    String encrypt(String password);
}
