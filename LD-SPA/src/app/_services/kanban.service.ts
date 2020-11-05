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

    createNewTask(title:string, swimlane:number){
        return this.http.post(`${environment.apiUrl}/tasks`, {title, swimlane})
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

    deleteTask(id:string){
        return this.http.delete(`${environment.apiUrl}/tasks/`+id);
    }


}