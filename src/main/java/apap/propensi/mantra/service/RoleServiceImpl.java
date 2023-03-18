package apap.propensi.mantra.service;

import apap.propensi.mantra.model.RoleModel;
import apap.propensi.mantra.repository.RoleDb;
import apap.propensi.mantra.repository.UserDb;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.OneToMany;
import javax.persistence.OptimisticLockException;
import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDb roleDb;

    @Override
    public List<RoleModel> findAll(){
        return roleDb.findAll();
    }

    @Override
    public RoleModel getById(Long id){
        Optional<RoleModel> role = roleDb.findById(id);
        if(role.isPresent()){
            return role.get();
        }else {
            return null;
        }
    }

}