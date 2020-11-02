import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map } from 'rxjs/operators';

import { environment } from '../../environments/environment';
import { User, TaskList, Task } from '../_models';

@Injectable({ providedIn: 'root' })
export class KanbanService {
    

    constructor(
        private http: HttpClient
    ) {
        
    }

    getAllTasks(){
        console.log(`${environment.apiUrl}/tasks`)
        return this.http.get<TaskList>(`${environment.apiUrl}/tasks`)
    }

    getTaskById(id:string){
        return this.http.get<Task>(`${environment.apiUrl}/tasks/`+id, null).pipe(map(task => {
            return task;
        }));;
    }

    updateTask(task:Task){
        return this.http.put(`${environment.apiUrl}/tasks`, task).pipe(map(task => {
            return task;
        }));;
    }

    getDeleteTask(id:string){
        return this.http.delete(`${environment.apiUrl}/tasks/`+id);
    }


}