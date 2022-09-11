import { Component, OnInit } from '@angular/core';
import { Employee } from './employee';
import { Review } from './review';
import { EmployeeService } from './employee.service';
import { ReviewService } from './review.service';
import { HttpErrorResponse } from '@angular/common/http';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {
  public employees: Employee[];
  public editEmployee: Employee;
  public deleteEmployee: Employee;

  public reviews: Review[];
  public editReview: Review;
  public deleteReview: Review;

  public date;

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
        this.reviews = response;
        console.log(this.reviews);
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  public onAddEmloyee(addForm: NgForm): void {
    document.getElementById('add-employee-form').click();
    this.employeeService.addEmployee(addForm.value).subscribe(
      (response: Employee) => {
        console.log(response);
        this.getEmployees();
        addForm.reset();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
        addForm.reset();
      }
    );
  }

  public onAddReview(addRate: NgForm): void {
    document.getElementById('add-review-form').click();
    this.reviewService.addReview(addRate.value).subscribe(
      (response: Review) => {
        console.log(response);
        this.getReviews();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
    window.location.reload();
  }

  public onUpdateEmloyee(employee: Employee): void {
    this.employeeService.updateEmployee(employee).subscribe(
      (response: Employee) => {
        console.log(response);
        this.getEmployees();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  public onBonusEmloyee(employee: Employee): void {
    this.employeeService.bonus(employee).subscribe(
      (response: Employee) => {
        console.log(response);
        this.getEmployees();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  public onDeleteEmloyee(employeeId: number): void {
    this.employeeService.deleteEmployee(employeeId).subscribe(
      (response: void) => {
        console.log(response);
        this.getEmployees();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  public searchEmployees(key: string): void {
    console.log(key);
    const results: Employee[] = [];
    for (const employee of this.employees) {
      if (employee.name.toLowerCase().indexOf(key.toLowerCase()) !== -1
      || employee.email.toLowerCase().indexOf(key.toLowerCase()) !== -1
      || employee.phone.toLowerCase().indexOf(key.toLowerCase()) !== -1
      || employee.jobTitle.toLowerCase().indexOf(key.toLowerCase()) !== -1) {
        results.push(employee);
      }
    }
    this.employees = results;
    if (results.length === 0 || !key) {
      this.getEmployees();
    }
  }

  public onOpenModal(employee: Employee, mode: string): void {
    const container = document.getElementById('main-container');
    const button = document.createElement('button');
    button.type = 'button';
    button.style.display = 'none';
    button.setAttribute('data-toggle', 'modal');
    if (mode === 'add') {
      button.setAttribute('data-target', '#addEmployeeModal');
    }
    if (mode === 'edit') {
      this.editEmployee = employee;
      button.setAttribute('data-target', '#updateEmployeeModal');
    }
    if (mode === 'delete') {
      this.deleteEmployee = employee;
      button.setAttribute('data-target', '#deleteEmployeeModal');
    }
    if (mode === 'bonus') {
      this.editEmployee = employee;
      if(this.editEmployee.shouldHaveBonus === false){
        button.setAttribute('data-target', '#bonusFalseEmployeeModal');
      } else if(this.editEmployee.shouldHaveBonus === true) {
        this.editEmployee.shouldHaveBonus = false;
        this.editEmployee.lastBonusDate = new Date().toISOString().slice(0, 10);
        this.editEmployee.bonus = 0;
        this.employeeService.updateEmployee(this.editEmployee);
        button.setAttribute('data-target', '#bonusEmployeeModal');
      }
    }
    if (mode === 'rate') {
      this.editEmployee = employee;
      let today = new Date().toISOString().slice(0, 10);
      this.date = today;
      button.setAttribute('data-target', '#rateEmployeeModal');
    }
    container.appendChild(button);
    button.click();
  }
}
