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
        return this.http.get<Task>(`${environment.apiUrl}/tasks/`+id);
    }

    updateTask(task:Task){
        this.http.put(`${environment.apiUrl}/tasks`, task).subscribe({
            next: data => {
                console.log(data);
            },
            error: error => {
                console.error('There was an error!', error);
            }
        })
    }

    getDeleteTask(id:string){
        return this.http.delete(`${environment.apiUrl}/tasks/`+id);
    }


}