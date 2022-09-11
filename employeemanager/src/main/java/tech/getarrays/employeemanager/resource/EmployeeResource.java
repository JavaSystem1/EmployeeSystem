package tech.getarrays.employeemanager.resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.getarrays.employeemanager.model.Employee;
import tech.getarrays.employeemanager.model.Review;
import tech.getarrays.employeemanager.service.EmployeeService;
import tech.getarrays.employeemanager.service.ReviewService;

import java.time.LocalDate;
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
        //calculate avgRate
        List<Review> reviewTmp = reviewService.findAllReviews();
        double avgRate = 0.0;
        double iterator = 0;
        //find employee reviews and calculate avgRate
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
        //calculate avgRate from last 3 months
        LocalDate today = LocalDate.now();
        double avg3MonthsRate = 0.0;
        double tmp = 0;
        for (int i = 0; i < employees.size(); i++) {
            tmp = 0;
            avg3MonthsRate = 0.0;
            for (int j = 0; j < reviewTmp.size(); j++) {
                if(employees.get(i).getEmployeeCode().equals(reviewTmp.get(j).getEmployeeCode())) {
                    LocalDate rateDate = LocalDate.parse(reviewTmp.get(j).getDate());
                    if(today.isAfter(rateDate.minusMonths(3))) //check for 3 months delay
                    {
                        avg3MonthsRate += reviewTmp.get(j).getRate();
                        tmp+=1;
                    }
                }
            }
            avg3MonthsRate /= tmp;
            avg3MonthsRate = Math.round(avg3MonthsRate * 100.0) / 100.0;
            employees.get(i).setLast3MonthsAvgRate(avg3MonthsRate);
        }
        //check for bonus
        for (int i = 0; i < employees.size(); i++) {
            LocalDate threeMonthsInterval = employees.get(i).getLastBonusDate().plusMonths(3);
            if(today.isAfter(threeMonthsInterval))
            {
                employees.get(i).setShouldHaveBonus(true);
                //calculate bonus
                if(employees.get(i).getLast3MonthsAvgRate() > 9.0f)
                {
                    employees.get(i).setBonus(employees.get(i).getSalary() * 0.2);
                } else if (employees.get(i).getLast3MonthsAvgRate() > 8.0f) {
                    employees.get(i).setBonus(employees.get(i).getSalary() * 0.1);
                } else if (employees.get(i).getLast3MonthsAvgRate() > 7.0f) {
                    employees.get(i).setBonus(employees.get(i).getSalary() * 0.05);
                } else {
                    employees.get(i).setShouldHaveBonus(false);
                    employees.get(i).setBonus(0.0);
                }
            } else {
                employees.get(i).setShouldHaveBonus(false);
                employees.get(i).setBonus(0.0);
            }
        }
        //check for updating job seniority


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

    @PutMapping("/bonus")
    public ResponseEntity<Employee> bonusProcedure(@RequestBody Employee employee) {
        Employee employee1 = employeeService.bonus(employee);
        return new ResponseEntity<>(employee1,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable("id") Long id) {
        employeeService.deleteEmployee(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
