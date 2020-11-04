import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { environment } from '../../environments/environment';
import { Schedule, CourseList } from '../_models';

@Injectable({ providedIn: 'root' })
export class ScheduleService {

    constructor(
        private router: Router,
        private http: HttpClient
    ) {
        
    }

    getSchedule(){
        return this.http.get<Schedule>(`${environment.apiUrl}/dhbw-schedule`);
    }

    getScheduleForNextDays(days?:number){
        if(days)
            return this.http.get<Schedule>(`${environment.apiUrl}/dhbw-schedule/upcomingdays`+days);
        else
            return this.http.get<Schedule>(`${environment.apiUrl}/dhbw-schedule/upcomingdays`);
    }

    getAllCourses(){
        return this.http.get<CourseList>(`${environment.apiUrl}/dhbw-schedule/courses`);
    }

    getSavedCourse(){
        return this.http.get<Schedule>(`${environment.apiUrl}/dhbw-schedule/courses/selected`);
    }

    setCourse(course:string){
        console.log(course);
        this.http.put(`${environment.apiUrl}/dhbw-schedule/courses/`+course, null).subscribe({
            next: data => {
                console.log(data);
            },
            error: error => {
                console.error('There was an error!', error);
            }
        })
    }

}