package tn.esprit.services.userServices;

import tn.esprit.entities.User.*;

import java.sql.SQLException;
import java.util.List;

public interface IUserService<T> {

    User getById(int id);

    User getByEmail(String email);

    void add(T t) throws SQLException;

    void update(T t) throws SQLException;

    void delete(int id);

    List<T> getAll();

    List<T> getByRole(RoleName role);

    void blockUser(int id);

    void unblockUser(int id);

    void toClubRH(int id);

    void toStudent(int id);

    void adminUpdateEtudiant(Etudiant etudiant);

    void adminUpdateEnseignant(Enseignant enseignant);

    void adminUpdateEntreprise(Entreprise entreprise);

    void changeMotDePasse(String password,User user);

    void updateEtudiant(Etudiant etudiant);

    void updateEnseignant(Enseignant enseignant);
}
