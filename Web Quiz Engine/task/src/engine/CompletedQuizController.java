package engine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Id;

@RestController
public class CompletedQuizController {
    @Autowired
    CompletedQuizRepository completedQuizRepository;

    @GetMapping(path="/api/quizzes/completed")
    public ResponseEntity<Page<CompletedQuizEntity>> getCompletedQuizzes(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer number,
            @RequestParam(defaultValue = "completedAt") String sortBy
            ) {
        Pageable paging = PageRequest.of(page, number, Sort.by(sortBy).descending());
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Page<CompletedQuizEntity> pageResult = completedQuizRepository.findAllByUserId(paging, user.getId());
        return new ResponseEntity<Page<CompletedQuizEntity>>(pageResult, new HttpHeaders(), HttpStatus.OK);
    }

}
