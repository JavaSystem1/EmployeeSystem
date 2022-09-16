package tech.getarrays.employeemanager.resource;

import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.getarrays.employeemanager.model.Employee;
import tech.getarrays.employeemanager.model.Review;
import tech.getarrays.employeemanager.model.Singleton;
import tech.getarrays.employeemanager.service.EmployeeService;
import tech.getarrays.employeemanager.service.ReviewService;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
    public ResponseEntity<List<Employee>> getAllEmployees () throws IOException, ParseException {
        List<Employee> employees = employeeService.findAllEmployees();
        List<Review> reviewTmp = reviewService.findAllReviews();
        avgRate(employees,reviewTmp);//calculate avgRate
        avgRate3Months(employees,reviewTmp);//calculate avgRate from last 3 months
        bonusCheck(employees,reviewTmp);//check for bonus
        updateSeniority(employees);//check for updating job seniority

        //sorting employees by highest avg rate
        Collections.sort(employees, new Comparator<Employee>() {
            @Override
            public int compare(Employee e1, Employee e2) {
                return Double.compare(e1.getAvgRate(), e2.getAvgRate());
            }
        });
        Collections.reverse(employees);

        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/currency")
    public ResponseEntity<String> getCurrenciesInfo () throws IOException {
        Singleton s1 = Singleton.getInstance();
        String apiresponse = s1.getCurrenciesJson();
        return new ResponseEntity<>(apiresponse, HttpStatus.OK);
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

    private void avgRate(List<Employee> employees,List<Review> reviewTmp) {
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
    }

    private void avgRate3Months(List<Employee> employees,List<Review> reviewTmp) {
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
    }

    private void bonusCheck(List<Employee> employees,List<Review> reviewTmp) {
        LocalDate today = LocalDate.now();
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
    }

    private void updateSeniority(List<Employee> employees) {
        LocalDate today = LocalDate.now();
        long monthsDifference = 0;
        for (int i = 0; i < employees.size(); i++) {
            monthsDifference = ChronoUnit.MONTHS.between(
                    today.withDayOfMonth(1),
                    employees.get(i).getDateOfCreation().withDayOfMonth(1));
            System.out.println(monthsDifference);
            employees.get(i).setCurrentJobSeniority(Math.abs(monthsDifference)+employees.get(i).getJobSeniority());
        }
    }
}