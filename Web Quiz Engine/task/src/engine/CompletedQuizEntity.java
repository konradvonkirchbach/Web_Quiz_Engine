package engine;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class CompletedQuizEntity {
    @OneToOne(cascade = CascadeType.REMOVE)
    @NotBlank
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private User user;
    @NotBlank
    private Integer id;
    @NotBlank
    @Id
    @UniqueElements
    private LocalDateTime completedAt;

    public CompletedQuizEntity() {
    }

    public CompletedQuizEntity(@NotBlank User user, @NotBlank Quiz quiz) {
        this.user = user;
        this.id = quiz.getId();
        this.completedAt = LocalDateTime.now();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setQuiz(Integer id) {
        this.id = id;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompletedQuizEntity that = (CompletedQuizEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(user, that.user) &&
                Objects.equals(completedAt, that.completedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, completedAt);
    }
}
