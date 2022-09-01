package tech.getarrays.employeemanager.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.getarrays.employeemanager.model.Review;

import java.util.Optional;

public interface ReviewRepo extends JpaRepository<Review, Long> {
    void deleteReviewById(Long id);

    Optional<Review> findReviewById(Long id);
}
