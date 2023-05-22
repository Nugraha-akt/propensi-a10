package apap.propensi.mantra.service;

import apap.propensi.mantra.model.UserModel;
import java.util.List;

public interface UserService {
    UserModel addUser(UserModel user);
    UserModel updateUser(UserModel user);
    void deleteUser(UserModel user);
    List<UserModel> getListUser();
    List<UserModel> getListUserSortedByRole();
    List<String> getAllUsername();
    List<String> getAllEmail();
    UserModel getUserByUsername(String username);
    UserModel getUserByEmail(String email);
    UserModel getUserByUuid(String uuid);
    String isUniqueUpdate(UserModel user);
    String encrypt(String password);
    UserModel updatePassword(UserModel userModel);
    UserModel updateProfile(UserModel oldUser);
}
