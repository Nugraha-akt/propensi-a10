package apap.propensi.mantra.service;

import apap.propensi.mantra.model.UserModel;

public interface UserService {
    UserModel addUser(UserModel user);
    public String encrypt(String password);
}
