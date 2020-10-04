package engine;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UserAlreadyRegistered extends Exception {
    private static final String MESSAGE = "USER ALREADY REGISTERED";

    public UserAlreadyRegistered(String message) {
        super(message);
    }
}
