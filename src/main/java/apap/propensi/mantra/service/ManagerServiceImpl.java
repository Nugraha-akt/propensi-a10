package apap.propensi.mantra.service;

import apap.propensi.mantra.model.ManagerModel;
import apap.propensi.mantra.model.Role;
import apap.propensi.mantra.repository.ManagerDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManagerServiceImpl implements ManagerService {
    @Autowired
    ManagerDb managerDb;

    @Override
    public ManagerModel addManager(ManagerModel user) {
        ManagerModel newManager = new ManagerModel();

        newManager.setUsername(user.getUsername());
        newManager.setPassword(encrypt(user.getPassword()));
        newManager.setUuid(user.getUuid());
        newManager.setNoTelepon(user.getNoTelepon());
        newManager.setNama(user.getNama());
        newManager.setEmail(user.getEmail());
        newManager.setRole(Role.MANAGER);

        return managerDb.save(newManager);
    }

    @Override
    public ManagerModel updateManager(ManagerModel user) {
        user.setPassword(user.getPassword());
        return managerDb.save(user);
    }

    @Override
    public void deleteManager(ManagerModel user) {
        managerDb.delete(user);
    }

    @Override
    public String encrypt(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        return hashedPassword;
    }

    @Override
    public ManagerModel getManagerByUuid(String uuid) {
        return managerDb.findByUuid(uuid);
    }

    @Override
    public List<ManagerModel> getListManager() { return managerDb.findAll();}
}
