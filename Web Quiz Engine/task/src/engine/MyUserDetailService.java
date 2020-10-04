package engine;

import org.hibernate.validator.internal.util.ConcurrentReferenceHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailService implements UserDetailsService {
    @Autowired
    private UserInterface userInterface;

    @java.lang.Override
    public UserDetails loadUserByUsername(java.lang.String email) throws UsernameNotFoundException {
        User user = userInterface.findByEmail(email);
        if (!(user == null)) {
            return user;
        } else {
            throw new UsernameNotFoundException("Not found: " + email);
        }
    }
}
