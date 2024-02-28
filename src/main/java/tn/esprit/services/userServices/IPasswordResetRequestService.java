package tn.esprit.services.userServices;

import tn.esprit.entities.User.PasswordResetRequest;
import tn.esprit.entities.User.User;

public interface IPasswordResetRequestService {

    PasswordResetRequest getById(int id);

    int Add(PasswordResetRequest passwordResetRequest);

    PasswordResetRequest getByUser(User user);

    void delete(User user);

    void delete(int id);




}
