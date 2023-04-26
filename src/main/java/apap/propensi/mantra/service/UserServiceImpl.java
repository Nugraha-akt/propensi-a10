package apap.propensi.mantra.service;

import apap.propensi.mantra.model.UserModel;
import apap.propensi.mantra.repository.UserDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
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
        return userDb.save(oldUser);
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
    public List<UserModel> getListUserSortedByRole() {
        return userDb.findAllOrderByRoleAsc();
    }

    @Override
    public List<String> getAllUsername() {
        return userDb.getAllUsername();
    }

    @Override
    public List<String> getAllEmail() {
        return userDb.getAllEmail();
    }

    @Override
    public UserModel getUserByUsername(String username) {
        return userDb.findByUsername(username);
    }

    @Override
    public UserModel getUserByEmail(String email) { return userDb.findByEmail(email); }

    @Override
    public UserModel getUserByUuid(String uuid) {
        return userDb.findByUuid(uuid);
    }

    @Override
    public String isUniqueUpdate(UserModel user) {
        if (getUserByUsername(user.getUsername()) != null) {
            if (!getUserByUsername(user.getUsername()).getUuid().equals(user.getUuid())) {
                return "duplicate-username";
            } else if (!getUserByEmail(user.getEmail()).getUuid().equals(user.getUuid())) {
                return "duplicate-email";
            }
        }
        return "unique";
    }

    @Override
    public UserModel updatePassword(UserModel userModel) {
        userModel.setPassword(encrypt(userModel.getPassword()));
        return userDb.save(userModel);
    }

    @Override
    public String encrypt(String password){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        return hashedPassword;
    }

}
