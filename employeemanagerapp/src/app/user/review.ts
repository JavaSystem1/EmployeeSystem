import * as internal from "events";

export interface Review {
  id: number;
  employeeCode: string;
  rate: number;
  date: string;
}