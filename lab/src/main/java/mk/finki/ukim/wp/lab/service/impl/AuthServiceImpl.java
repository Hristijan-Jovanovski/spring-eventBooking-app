package mk.finki.ukim.wp.lab.service.impl;
import mk.finki.ukim.wp.lab.model.User;
import mk.finki.ukim.wp.lab.model.enumerations.Role;
import mk.finki.ukim.wp.lab.repository.jpa.UserRepository;
import mk.finki.ukim.wp.lab.service.AuthService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User login(String username, String password) {
        // Check if the username and password are not null or empty
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            throw new RuntimeException("Invalid Arguments");
        }
        return userRepository.findByUsernameAndPassword(username, password)
                .orElseThrow(RuntimeException::new);
    }

    @Override
    public User register(String username, String password, String repeatPassword, String name, String surname,Role role) {
        if (username == null || username.isEmpty() || password == null || password.isEmpty() || repeatPassword == null || repeatPassword.isEmpty() || name == null || name.isEmpty()||surname==null||surname.isEmpty() ) {
            throw new RuntimeException();

        }
        if(!password.equals(repeatPassword)){
            throw new RuntimeException();
        }
        return userRepository.save(new User(username,password,name,surname,role));
    }
}
