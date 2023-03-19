package apap.propensi.mantra.service;

import apap.propensi.mantra.model.Role;
import apap.propensi.mantra.model.UserModel;
import apap.propensi.mantra.repository.UserDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserDb userDb;

    @Override
    public UserModel addUser(UserModel user) {
        UserModel newUser = new UserModel();

        newUser.setUsername(user.getUsername());
        newUser.setPassword(encrypt(user.getPassword()));
        newUser.setUuid(user.getUuid());
        newUser.setNoTelepon(user.getNoTelepon());
        newUser.setNama(user.getNama());
        newUser.setEmail(user.getEmail());
        newUser.setRole(user.getRole());

        return userDb.save(newUser);
    }

    @Override
    public UserModel updateUser(UserModel oldUser){
        UserModel updatedUser = new UserModel();

        updatedUser.setUsername(oldUser.getUsername());
        updatedUser.setPassword(oldUser.getPassword());
        updatedUser.setUuid(oldUser.getUuid());
        updatedUser.setNoTelepon(oldUser.getNoTelepon());
        updatedUser.setNama(oldUser.getNama());
        updatedUser.setRole(oldUser.getRole());
        updatedUser.setEmail(oldUser.getEmail());

        return userDb.save(updatedUser);
    }

    @Override
    public void deleteUser(UserModel user){
        userDb.delete(user);
    }

    @Override
    public List<UserModel> getListUser() {
        return userDb.findAll();
    }

    @Override
    public UserModel getUserByUsername(String username) {
        return userDb.findByUsername(username);
    }

    @Override
    public UserModel getUserByUuid(String uuid) {
        return userDb.findByUuid(uuid);
    }

    @Override
    public String encrypt(String password){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        return hashedPassword;
    }
}
