package tn.esprit.services.userServices;

import tn.esprit.entities.User.User;

import java.sql.SQLException;
import java.util.List;

public interface IUserService<T> {

    User getById(int id);

    User getByEmail(String email);

    void add(T t) throws SQLException;

    void update(T t) throws SQLException;

    void delete(int id);

    List<T> getAll();

}
