package tn.esprit.services.userServices;

import tn.esprit.entities.User.RoleName;
import tn.esprit.entities.User.User;



public class AuthResponseDTO extends User {

    public AuthResponseDTO(int id, RoleName role) {
        super(id, role);
    }
}

