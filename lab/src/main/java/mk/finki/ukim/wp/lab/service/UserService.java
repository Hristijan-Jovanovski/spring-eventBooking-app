package mk.finki.ukim.wp.lab.service;

import mk.finki.ukim.wp.lab.model.User;
import mk.finki.ukim.wp.lab.model.enumerations.Role;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService  {
    User register(String username, String password, String repeatPassword, String name, String surname, Role role);
  User loadUserByUsername(String username);
}