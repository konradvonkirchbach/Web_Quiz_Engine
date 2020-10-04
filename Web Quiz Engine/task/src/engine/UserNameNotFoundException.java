package engine;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UserNameNotFoundException extends Exception {
    private static final String MESSAGE = "USER NOT FOUND: ";

    public UserNameNotFoundException(String message) {
        super(message);
    }
}
