package com.pwr.patrykzdral.timebank.database.service;

import com.pwr.patrykzdral.timebank.database.entity.User;

public interface UserService extends CRUDService<User> {
    User findByLogin(String login);
    User findByEmail(String email);
    Boolean checkUserExists(String login, String email);
    User findByLoginAndPassword(String login, String password);
}
