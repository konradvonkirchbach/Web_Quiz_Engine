package engine;

import io.micrometer.core.ipc.http.HttpSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.*;
import java.util.Optional;

@RestController
public class QuizController {
    @Autowired
    private QuizInterface quizInterface;

    @Autowired
    private UserInterface userInterface;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CompletedQuizRepository completedQuizRepository;

    public QuizController() {
    }

    @PostMapping(path = "/api/quizzes", consumes = "application/json")
    public Quiz addQuiz(@Valid @RequestBody Quiz quiz) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        quiz.setUser(user);
        quizInterface.save(quiz);
        return quiz;
    }

    @DeleteMapping(path="/api/quizzes/{id}")
    public ResponseEntity<String> deleteQuiz(@PathVariable("id") Integer quizId) {
        Optional<Quiz> optionalQuiz = quizInterface.findById(quizId);
        if (!optionalQuiz.isPresent()) {
            return ResponseEntity.status(404).build();
        }

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (optionalQuiz.get().getUser().getId() != user.getId()) {
            return ResponseEntity.status(403).build();
        }

        quizInterface.deleteById(quizId);
        return ResponseEntity.status(204).build();
    }


    @PostMapping(path="/api/quizzes/{id}/solve", consumes = "application/json")
    public Answer addQuiz(@PathVariable("id") Integer id, @RequestBody Options answer) throws ResourceNotFoundException {
        Quiz quiz = quizInterface.findById(id).orElseThrow(() -> new ResourceNotFoundException("Quiz not found"));
        int[] quizAnswer = quiz.getAnswer();
        int[] optionsAnswer = answer.getAnswer();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (quizAnswer == null && (optionsAnswer == null || optionsAnswer.length == 0)) {
            CompletedQuizEntity completedQuiz = new CompletedQuizEntity(user, quiz);
            completedQuizRepository.save(completedQuiz);
            return new Answer(true, "Congratulations, you're right!");
        }
        if (Arrays.equals(optionsAnswer, quizAnswer)) {
            CompletedQuizEntity completedQuiz = new CompletedQuizEntity(user, quiz);
            completedQuizRepository.save(completedQuiz);
            return new Answer(true, "Congratulations, you're right!");
        } else {
            return new Answer(false, "Wrong answer! Please try again.");
        }
    }

    @GetMapping(path = "/api/quizzes")
    public ResponseEntity<Page<Quiz>> getAllQuizzes(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        Pageable paging = PageRequest.of(page, pageSize, Sort.by(sortBy));
        Page<Quiz> pageResult = quizInterface.findAll(paging);
        return new ResponseEntity<Page<Quiz>>(pageResult, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping(path="/api/quizzes/{id}")
    public Quiz getQuiz(@PathVariable("id") Integer id) throws ResourceNotFoundException {
        Quiz quiz = quizInterface.findById(id).orElseThrow(() -> new ResourceNotFoundException("Quiz not found"));
        if (quiz != null) {
            return quiz;
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Quiz not found"
            );
        }
    }
}
