package tech.getarrays.employeemanager.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.getarrays.employeemanager.model.Review;

import java.util.Optional;

@Repository
public interface ReviewRepo extends JpaRepository<Review, Long> {
    short deleteReviewById(Long id);

    Optional<Review> findReviewById(Long id);
}
