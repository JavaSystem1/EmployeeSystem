import { Component, OnInit } from '@angular/core';
import { Employee } from './employee';
import { Review } from './review';
import { EmployeeService } from './employee.service';
import { ReviewService } from './review.service';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {
  public employees: Employee[];
  public employee: Employee;

  public reviews: Review[];

  constructor(private employeeService: EmployeeService, private reviewService: ReviewService){}

  ngOnInit() {
    this.getEmployees();
    this.getReviews();
  }

  public getEmployees(): void {
    this.employeeService.getEmployees().subscribe(
      (response: Employee[]) => {
        this.employees = response;
        console.log(this.employees);
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  public getReviews(): void {
    this.reviewService.getReviews().subscribe(
      (response: Review[]) => {
        this.reviews = response.filter(word => word.employeeCode === this.employees[0].employeeCode);
        console.log(this.reviews);
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }
}