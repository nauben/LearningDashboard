import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { environment } from '../../environments/environment';
import { User } from '../_models';

@Injectable({ providedIn: 'root' })
export class AccountService {

    constructor(
        private router: Router,
        private http: HttpClient
    ) {
        
    }

    getSchedule(){

    }

    getScheduleForNextDays(days:number){

    }

    getAllCourses(){
        return this.http.get<string[]>(`${environment.apiUrl}/dhbw-schedule/courses`, null);
    }

    setCourse(course:string){
        console.log(course);
        return this.http.post(`${environment.apiUrl}/dhbw-schedule/courses/${course}`, null);
    }

}