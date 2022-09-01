package tech.getarrays.employeemanager.resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.getarrays.employeemanager.model.Employee;
import tech.getarrays.employeemanager.model.Review;
import tech.getarrays.employeemanager.service.EmployeeService;
import tech.getarrays.employeemanager.service.ReviewService;

import java.util.List;

@RestController
@RequestMapping("/review")
public class ReviewResource {
    private final ReviewService reviewService;
    private final EmployeeService employeeService;

    public ReviewResource(ReviewService reviewService, EmployeeService employeeService) {
        this.employeeService = employeeService;
        this.reviewService = reviewService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Review>> getAllReviews () {
        List<Review> review = reviewService.findAllReviews();
        return new ResponseEntity<>(review, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Review> getReviewById (@PathVariable("id") Long id) {
        Review review1 = reviewService.findReviewById(id);
        return new ResponseEntity<>(review1, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Review> addReview(@RequestBody Review review) {
        Review review1 = reviewService.addReview(review);
        List<Employee> employees = employeeService.findAllEmployees();
        List<Review> reviewTmp = reviewService.findAllReviews();
        double avgRate = 0.0;
        int iterator = 0;
        for (int i = 0; i < employees.size(); i++) {
            iterator = 0;
            avgRate = 0.0;
            for (int j = 0; j < reviewTmp.size(); j++) {
                if(true) {
                    avgRate += reviewTmp.get(j).getRate();
                    iterator++;
                }
            }
            avgRate /= iterator;
            employees.get(i).setAvgRate(9.5);
        }
        return new ResponseEntity<>(review1, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Review> updateReview(@RequestBody Review review) {
        Review review1 = reviewService.addReview(review);
        return new ResponseEntity<>(review1,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable("id") Long id) {
        reviewService.deleteReview(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
