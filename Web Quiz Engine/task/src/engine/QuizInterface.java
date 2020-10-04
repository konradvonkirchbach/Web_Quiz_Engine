package engine;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizInterface extends PagingAndSortingRepository<Quiz, Integer> {
}
