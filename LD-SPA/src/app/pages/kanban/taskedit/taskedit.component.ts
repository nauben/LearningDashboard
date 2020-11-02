import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Task } from '../../../_models/task';
import {KanbanService} from '../../../_services/kanban.service';
import { first } from 'rxjs/operators';

@Component({
  selector: 'app-taskedit',
  templateUrl: './taskedit.component.html',
  styleUrls: ['./taskedit.component.css']
})
export class TaskeditComponent implements OnInit {

public task : Task;
  @Input() taska:Task;
  @Output() saveUpdate = new EventEmitter();

  constructor(
    private kanbanService: KanbanService
  ) {  }

  ngOnInit(): void {
    
  }

saveChanges(){
  console.log("saving...")
  this.task.title =  $("#exampleFormControlTitel").val().toString();
  this.task.description = $("#exampleFormControlDescription").val().toString();
  if($("#customRadioTodo").prop('checked')){
    this.task.swimlane = 0;
  } else if( $("#customRadioWip").prop('checked')){
    this.task.swimlane = 1;
  } else{this.task.swimlane = 2;}
  this.task.label = 0;
  console.log(this.task)
  this.kanbanService.updateTask(this.task);
  this.saveUpdate.emit(this.task);
}

setTask(id:string){
  this.kanbanService.getTaskById(id).pipe(first())
    .subscribe(
        data => {
          console.log(data);
          this.task = data;
          $("#exampleFormControlTitel").val(this.task.title);
          $("#exampleFormControlDescription").val(this.task.description);
          if(this.task.swimlane === 0){
            $("#customRadioTodo").prop('checked', true);
            $("#customRadioWip").prop('checked', false);
            $("#customRadioDone").prop('checked', false);
          } if (this.task.swimlane === 1){
            $("#customRadioTodo").prop('checked', false);
            $("#customRadioWip").prop('checked', true);
            $("#customRadioDone").prop('checked', false);
          }else {
            $("#customRadioTodo").prop('checked', false);
            $("#customRadioWip").prop('checked', false);
            $("#customRadioDone").prop('checked', true);
          }
          
        },
        error => {
            console.log(error);
        });
      
  }
}
