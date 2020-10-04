package engine;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Quiz {
    private static int ID = 0;
    @Id
    @Column(unique = true)
    Integer id;
    @JsonProperty(value = "answer", access = JsonProperty.Access.WRITE_ONLY)
    private int[] answer;
    @Column(nullable = false)
    @NotBlank(message = "title is mandatory")
    private String title;
    @Column(nullable = false)
    @NotBlank(message = "text is mandatory")
    private String text;
    @Column(nullable = false)
    @NotNull
    @Size(min=2, message = "Parse at least two options")
    private String[] options;
    @ManyToOne
    @JoinColumn(name = "UserID")
    @JsonProperty(value = "user", access = JsonProperty.Access.WRITE_ONLY)
    private User user;

    public Quiz() {
        this.id = ID++;
    }

    public Quiz(int[] answer, String title, String text, String[] options) {
        this.id = ID++;
        this.answer = answer;
        this.title = title;
        this.text = text;
        this.options = options;
    }

    public int[] getAnswer() {
        return this.answer != null ? this.answer.clone() : null;
    }

    public void setAnswer(int[] answer) {
        this.answer = answer.clone();
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String[] getOptions() {
        return this.options;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
