package mk.finki.ukim.wp.lab.service;

import mk.finki.ukim.wp.lab.model.User;
import mk.finki.ukim.wp.lab.model.enumerations.Role;

public interface AuthService {
    User login(String username, String password);
    User register(String username, String password, String repeatePassword, String name, String surname, Role role);
}
