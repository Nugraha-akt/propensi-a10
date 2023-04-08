package apap.propensi.mantra.service;

import apap.propensi.mantra.model.AdminModel;
import apap.propensi.mantra.model.Role;
import apap.propensi.mantra.repository.AdminDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminDb adminDb;
    @Override
    public AdminModel addAdmin(AdminModel user) {
        AdminModel newAdmin = new AdminModel();

        newAdmin.setUsername(user.getUsername());
        newAdmin.setPassword(encrypt(user.getPassword()));
        newAdmin.setUuid(user.getUuid());
        newAdmin.setNoTelepon(user.getNoTelepon());
        newAdmin.setNama(user.getNama());
        newAdmin.setEmail(user.getEmail());
        newAdmin.setRole(Role.ADMIN);

        return adminDb.save(newAdmin);
    }

    @Override
    public AdminModel updateAdmin(AdminModel user) {
        user.setPassword(encrypt(user.getPassword()));
        return adminDb.save(user);
    }

    @Override
    public void deleteAdmin(AdminModel user) {
        adminDb.delete(user);
    }

    @Override
    public List<AdminModel> getListAdmin() {
        return adminDb.findAll();
    }

    @Override
    public AdminModel getAdminByUuid(String uuid) {
        return adminDb.findByUuid(uuid);
    }

    @Override
    public String encrypt(String password){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        return hashedPassword;
    }
}
