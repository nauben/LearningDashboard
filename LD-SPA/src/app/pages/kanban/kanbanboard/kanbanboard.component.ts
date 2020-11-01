import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-kanbanboard',
  templateUrl: './kanbanboard.component.html',
  styleUrls: ['./kanbanboard.component.css']
})
export class KanbanboardComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
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
