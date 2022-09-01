package tech.getarrays.employeemanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.getarrays.employeemanager.exception.ReviewNotFoundException;
import tech.getarrays.employeemanager.model.Review;
import tech.getarrays.employeemanager.repo.ReviewRepo;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ReviewService {
    private final ReviewRepo reviewRepo;

    @Autowired
    public ReviewService(ReviewRepo reviewRepo) {
        this.reviewRepo = reviewRepo;
    }

    public Review addReview(Review review) {
        return reviewRepo.save(review);
    }

    public List<Review> findAllReviews() {
        return reviewRepo.findAll();
    }

    public Review updateReview(Review review) {
        return reviewRepo.save(review);
    }

    public Review findReviewById(Long id) {
        return reviewRepo.findReviewById(id)
                .orElseThrow(() -> new ReviewNotFoundException("User by id " + id + " was not found"));
    }

    public void deleteReview(Long id){
        reviewRepo.deleteReviewById(id);
    }
}