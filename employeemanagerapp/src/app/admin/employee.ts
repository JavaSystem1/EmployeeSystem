import * as internal from "events";

export interface Employee {
  id: number;
  name: string;
  email: string;
  jobTitle: string;
  phone: string;
  salary: number;
  jobSeniority: number;
  imageUrl: string;
  employeeCode: string;
  avgRate: number;
}