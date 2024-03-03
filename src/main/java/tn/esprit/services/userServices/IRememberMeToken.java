package tn.esprit.services.userServices;

import tn.esprit.entities.User.RememberMeToken;
import tn.esprit.entities.User.User;

public interface IRememberMeToken {

    RememberMeToken getById(int id);

    RememberMeToken getByToken(String token);

    RememberMeToken getByUser(User user);

    void add(RememberMeToken token);

    void deleteByUser(User user);

    void deleteById(int id);

}
