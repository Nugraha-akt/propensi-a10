package apap.propensi.mantra.service;

import apap.propensi.mantra.model.UserModel;
import java.util.List;

public interface UserService {
    UserModel addUser(UserModel user);
    UserModel updateUser(UserModel user);
    void deleteUser(UserModel user);
    List<UserModel> getListUser();
    UserModel getUserByUsername(String username);
    String encrypt(String password);
}
