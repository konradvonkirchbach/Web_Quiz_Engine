package engine;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.mapping.Array;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(unique=true, name="USER")
    @Pattern(regexp = "\\S*@+\\S*\\.+\\S*")
    private String email;
    @Size(min=5)
    @JsonProperty(value="password", access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    @JsonProperty(value = "quizList", access = JsonProperty.Access.WRITE_ONLY)
    private List<Quiz> quizList = new ArrayList<Quiz>();

    @java.lang.Override
    public java.util.Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority("USER"));
    }

    @java.lang.Override
    public java.lang.String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @java.lang.Override
    public java.lang.String getUsername() {
        return email;
    }

    @java.lang.Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @java.lang.Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @java.lang.Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @java.lang.Override
    public boolean isEnabled() {
        return true;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Quiz> getQuizList() {
        return quizList;
    }

    public void setQuizList(List<Quiz> quizList) {
        this.quizList = quizList;
    }

    public int getId() {
        return id;
    }
}
