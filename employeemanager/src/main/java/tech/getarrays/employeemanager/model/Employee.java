package tech.getarrays.employeemanager.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Entity
public class Employee implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false,updatable = false)
    private Long id;
    private String name;
    private String email;
    private String jobTitle;
    private String phone;
    private Long salary;
    private Long jobSeniority;
    private String imageUrl;
    @Column(nullable = false,updatable = false)
    private String employeeCode;
    private Double avgRate;
    private Boolean shouldHaveBonus;
    private Double bonus;
    private LocalDate lastBonusDate;
    private Double last3MonthsAvgRate;
    private LocalDate dateOfCreation;

    public Employee() {}

    public Employee(String name, String email, String jobTitle, String phone, Long salary, Long jobSeniority, String imageUrl, String employeeCode, Double avgRate, Boolean shouldHaveBonus, Double bonus, LocalDate lastBonusDate, Double last3MonthsAvgRate, LocalDate dateOfCreation) {
        this.name = name;
        this.email = email;
        this.jobTitle = jobTitle;
        this.phone = phone;
        this.salary = salary;
        this.jobSeniority = jobSeniority;
        this.imageUrl = imageUrl;
        this.employeeCode = employeeCode;
        this.avgRate = avgRate;
        this.shouldHaveBonus = shouldHaveBonus;
        this.bonus = bonus;
        this.lastBonusDate = lastBonusDate;
        this.last3MonthsAvgRate = last3MonthsAvgRate;
        this.dateOfCreation = dateOfCreation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getSalary() {
        return salary;
    }

    public void setSalary(Long salary) {
        this.salary = salary;
    }

    public Long getJobSeniority() {
        return jobSeniority;
    }

    public void setJobSeniority(Long JobSeniority) {
        this.jobSeniority = JobSeniority;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public Double getAvgRate() {
        return avgRate;
    }

    public void setAvgRate(Double avgRate) {
        this.avgRate = avgRate;
    }

    public Boolean getShouldHaveBonus() {
        return shouldHaveBonus;
    }

    public void setShouldHaveBonus(Boolean shouldHaveBonus) {
        this.shouldHaveBonus = shouldHaveBonus;
    }

    public Double getBonus() {
        return bonus;
    }

    public void setBonus(Double bonus) {
        this.bonus = bonus;
    }

    public LocalDate getLastBonusDate() {
        return lastBonusDate;
    }

    public void setLastBonusDate(LocalDate lastBonusDate) {
        this.lastBonusDate = lastBonusDate;
    }

    public Double getLast3MonthsAvgRate() {
        return last3MonthsAvgRate;
    }

    public void setLast3MonthsAvgRate(Double last3MonthsAvgRate) {
        this.last3MonthsAvgRate = last3MonthsAvgRate;
    }

    public LocalDate getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(LocalDate dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    @Override
    public String toString() {
        return "Employee(" +
                "id=" + id +
                ", name ='" + name + '\'' +
                ", email ='" + email + '\'' +
                ", jobTitle ='" + jobTitle + '\'' +
                ", phone ='" + phone + '\'' +
                ", salary ='" + salary + '\'' +
                ", job Seniority ='" + jobSeniority + '\'' +
                ", imageUrl ='" + imageUrl + '\'' +
                '}';
    }
}
