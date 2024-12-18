package mk.finki.ukim.wp.lab.service.impl;

import mk.finki.ukim.wp.lab.model.User;
import mk.finki.ukim.wp.lab.model.enumerations.Role;
import mk.finki.ukim.wp.lab.repository.jpa.UserRepository;
import mk.finki.ukim.wp.lab.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
    public class UserServiceImpl implements UserService {

        private final UserRepository userRepository;
        private final PasswordEncoder passwordEncoder;

        public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
            this.userRepository = userRepository;
            this.passwordEncoder = passwordEncoder;
        }

        @Override
        public User register(String username, String password, String repeatPassword, String name, String surname, Role role) {
            if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
                throw new RuntimeException("Invalid Arguments");
            }

            if (!password.equals(repeatPassword)) {
                throw new RuntimeException("Passwords Do Not Match");
            }

            if (this.userRepository.findByUsername(username).isPresent()) {
                throw new RuntimeException("Username Already Exists");
            }

            User user = new User(username, passwordEncoder.encode(password), name, surname, role);

            return userRepository.save(user);
        }


        public User loadUserByUsername(String username) throws UsernameNotFoundException {
            return userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("User Not Found"));
        }
    }


