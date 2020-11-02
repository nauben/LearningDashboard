import { Component, OnInit, ViewChild } from '@angular/core';
import {Task, TaskList} from '../../../_models';
import {KanbanService} from '../../../_services/kanban.service';
import { first } from 'rxjs/operators';
import { TaskeditComponent } from '../taskedit/taskedit.component';

@Component({
  selector: 'app-kanbanboard',
  templateUrl: './kanbanboard.component.html',
  styleUrls: ['./kanbanboard.component.css']
})
export class KanbanboardComponent implements OnInit {

  taskList: TaskList
  tasks:Task[]
  toDo:Task[]
  wip:Task[]
  done:Task[]
  @ViewChild(TaskeditComponent) editModal:TaskeditComponent;

  constructor(
    private kanbanService: KanbanService
  ) {}

  ngOnInit(): void {
    
    this.refreshBoard();
  }



  refreshBoard(){
    this.kanbanService.getAllTasks().pipe(first())
    .subscribe(
        data => {
          console.log("")
          this.taskList = data;
          this.tasks = this.taskList.tasks;
          this.toDo = this.tasks.filter(task => { return task.swimlane === 0});
          this.wip = this.tasks.filter(task => { return task.swimlane === 1});
          this.done = this.tasks.filter(task => { return task.swimlane === 2});
          if(!this.tasks) return
          this.tasks.forEach(task => {
            console.log($('.items'))
            $(".items").append(this.getKanbanTaskElement(task.id, task.title, new Date(task.dueDate[0], task.dueDate[1], task.dueDate[2], task.dueDate[3], task.dueDate[4])));
          });
        },
        error => {
            console.log(error);
        });
    

  }

  onClick(event){
    var target = event.target || event.srcElement || event.currentTarget;
    console.log($(target).find(".idTask")[0].innerHTML);
    this.editModal.setTask($(target).find(".idTask")[0].innerHTML);

  }



  getKanbanTaskElement(id, title, date){
    return '<div class="card shadow m-2 draggable" id="cd3" draggable="true" ondragstart="drag(event)">'+
    '<div class="card-body p-2" data-toggle="modal" data-target="#editTask">'+
        '<div class="card-title">'+
            '<span class="font-italic text-info">'+
            id+'&nbsp;&nbsp;&nbsp;'+
            '</span>'+
            '<div class="btn-group dropright">'+
              '<button type="button" class="btn btn-sm btn-outline-info dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">'+
                '<svg width="1.2em" height="1.2em" viewBox="0 0 16 16" class="bi bi-list-ul" fill="currentColor" xmlns="http://www.w3.org/2000/svg">'+
                  '<path fill-rule="evenodd" d="M5 11.5a.5.5 0 0 1 .5-.5h9a.5.5 0 0 1 0 1h-9a.5.5 0 0 1-.5-.5zm0-4a.5.5 0 0 1 .5-.5h9a.5.5 0 0 1 0 1h-9a.5.5 0 0 1-.5-.5zm0-4a.5.5 0 0 1 .5-.5h9a.5.5 0 0 1 0 1h-9a.5.5 0 0 1-.5-.5zm-3 1a1 1 0 1 0 0-2 1 1 0 0 0 0 2zm0 4a1 1 0 1 0 0-2 1 1 0 0 0 0 2zm0 4a1 1 0 1 0 0-2 1 1 0 0 0 0 2z"/>'+
                '</svg>'+
              '</button>'+
              '<div class="dropdown-menu">'+
                '<button type="button" class="dropdown-item" data-toggle="modal" data-target="#editTask">'+
                 ' Aufgabe bearbeiten'+
                 '</button>'+
                '<a class="dropdown-item" href="#"><span class="text-danger">Aufgabe löschen</span></a>'+
              '</div>'+
            '</div>'+
            '<svg width="1.2em" height="1.2em" viewBox="0 0 16 16" class="bi bi-person-square float-right" fill="black" xmlns="http://www.w3.org/2000/svg">'+
                '<path fill-rule="evenodd" d="M14 1H2a1 1 0 0 0-1 1v12a1 1 0 0 0 1 1h12a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1zM2 0a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2H2z"/>'+
                '<path fill-rule="evenodd" d="M2 15v-1c0-1 1-4 6-4s6 3 6 4v1H2zm6-6a3 3 0 1 0 0-6 3 3 0 0 0 0 6z"/>'+
            '</svg>'+
        '</div>'+
        title+
        '<p class ="card-text">'+date+'</p>'+
    '</div>'+					
'</div>"';
  }

   //notice the particular screen size
   sizeKan(){
    let envs = ['xs', 'sm', 'md', 'lg', 'xl'];
  
      let el = document.createElement('div');
      document.body.appendChild(el);
  
      let curEnv = envs.shift();
  
      for (let env of envs.reverse()) {
          el.classList.add(`d-${env}-none`);
  
          if (window.getComputedStyle(el).display === 'none') {
              curEnv = env;
              break;
          }
      }
  
      document.body.removeChild(el);
      this.resizeKan(curEnv);
    
  } 
      //if scren size is xl or lg, then show all three swimlanes of the Kanban-Board
    resizeKan(curEnv){
    var todo = $("#todo")[0];
    var inarbeit = $("#inarbeit")[0];
    var fertig = $("#fertig")[0];
    var size = curEnv;
  
    if(size === "lg" || size === "xl"){
      todo.style.display = "block";
      inarbeit.style.display = "block";
      fertig.style.display = "block";	
    }
  } 

}
