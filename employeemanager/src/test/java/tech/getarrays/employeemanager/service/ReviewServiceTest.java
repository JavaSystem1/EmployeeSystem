package tech.getarrays.employeemanager.service;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import tech.getarrays.employeemanager.exception.UserNotFoundException;
import tech.getarrays.employeemanager.model.Review;
import tech.getarrays.employeemanager.repo.ReviewRepo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
class ReviewServiceTest {

    @Autowired
    private ReviewService reviewService;

    @MockBean
    private ReviewRepo reviewRepo;

    @Test
    void addReview() {
        Review review = new Review("11", 5L, "2022-09-11");
        when(reviewRepo.save(review)).thenReturn(review);
        assertEquals(review, reviewService.addReview(review));
    }

    @Test
    void findAllReviews() {
        Review review = new Review("11", 5L, "2022-09-11");
        Review review2 = new Review("12", 10L, "2022-09-12");

        List<Review> list = new ArrayList<>(Arrays.asList(review, review2));
        when(reviewRepo.findAll()).thenReturn(list);
        assertEquals(2, reviewService.findAllReviews().size());
    }

    @Test
    void findReviewById() {
        Review review = new Review("11", 5L, "2022-09-11");

        reviewService.addReview(review);
        when(reviewRepo.save(review)).thenReturn(review);
        assertEquals(review, reviewService.addReview(review));

        when(reviewRepo.findReviewById(review.getId())).thenReturn(Optional.of(review));
        assertEquals(review, reviewService.findReviewById(review.getId()));
    }

    @Test
    void deleteReview() {
        Review review = new Review("11", 5L, "2022-09-11");

        reviewService.addReview(review);
        when(reviewRepo.save(review)).thenReturn(review);
        assertEquals(review, reviewService.addReview(review));

        assertEquals(0, doThrow(new UserNotFoundException("Brak oceny pracownika")).when(reviewRepo).deleteReviewById(review.getId()));
    }
}