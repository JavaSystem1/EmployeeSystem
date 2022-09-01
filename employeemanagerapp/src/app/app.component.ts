import { Component, OnInit } from '@angular/core';
import { Employee } from './admin/employee';
import { Review } from './admin/review';
import { EmployeeService } from './admin/employee.service';
import { HttpErrorResponse } from '@angular/common/http';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  constructor(){}

  ngOnInit() {}

}