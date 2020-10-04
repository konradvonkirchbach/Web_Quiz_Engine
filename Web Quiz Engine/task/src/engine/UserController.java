package engine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    private UserInterface userInterface;

    @Autowired
    PasswordEncoder passwordEncoder;

    public UserController() {
    }

    @PostMapping(path="/api/register", consumes="application/json")
    public User RegisterUser(@Valid @RequestBody User user) throws UserAlreadyRegistered{
        User optionalUser = userInterface.findByEmail(user.getUsername());
        if (optionalUser == null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userInterface.save(user);
        } else {
            throw new UserAlreadyRegistered("User already registered");
        }

        return user;
    }
}
