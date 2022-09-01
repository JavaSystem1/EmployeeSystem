package tech.getarrays.employeemanager.resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.getarrays.employeemanager.model.Employee;
import tech.getarrays.employeemanager.model.Review;
import tech.getarrays.employeemanager.service.EmployeeService;
import tech.getarrays.employeemanager.service.ReviewService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employee")
public class EmployeeResource {
    private final ReviewService reviewService;
    private final EmployeeService employeeService;

    public EmployeeResource(ReviewService reviewService, EmployeeService employeeService) {
        this.employeeService = employeeService;
        this.reviewService = reviewService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Employee>> getAllEmployees () {
        List<Employee> employees = employeeService.findAllEmployees();
        List<Review> reviewTmp = reviewService.findAllReviews();
        double avgRate = 0.0;
        double iterator = 0;
        for (int i = 0; i < employees.size(); i++) {
            iterator = 0;
            avgRate = 0.0;
            for (int j = 0; j < reviewTmp.size(); j++) {
                if(employees.get(i).getEmployeeCode().equals(reviewTmp.get(j).getEmployeeCode())) {
                    avgRate += reviewTmp.get(j).getRate();
                    iterator+=1;
                }
            }
            avgRate /= iterator;
            avgRate = Math.round(avgRate * 100.0) / 100.0;
            employees.get(i).setAvgRate(avgRate);
        }
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Employee> getEmployeeById (@PathVariable("id") Long id) {
        Employee employee1 = employeeService.findEmployeeById(id);
        return new ResponseEntity<>(employee1, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
        Employee employee1 = employeeService.addEmployee(employee);
        return new ResponseEntity<>(employee1, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee) {
        Employee employee1 = employeeService.addEmployee(employee);
        return new ResponseEntity<>(employee1,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable("id") Long id) {
        employeeService.deleteEmployee(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
