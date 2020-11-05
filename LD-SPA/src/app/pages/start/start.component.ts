import { Component, OnInit } from '@angular/core';
import {ScheduleService} from '../../_services/schedule.service';
import {Schedule, Lecture} from '../../_models';
import { first } from 'rxjs/operators';

@Component({
  selector: 'app-start',
  templateUrl: './start.component.html',
  styleUrls: ['./start.component.css']
})
export class StartComponent implements OnInit {

  constructor(private scheduleService:ScheduleService) { }

  ngOnInit(): void {
    this.loadSchedule()
  }

  loadSchedule(){
    this.scheduleService.getScheduleForNextDays().pipe(first())
    .subscribe(
        data => {
           console.log(data)
           console.log("Test")
           this.refreshScheduleDisplay(data);
        },
        error => {
            console.log(error);
        });;
  }

  refreshScheduleDisplay(schedule:Schedule){
    if(schedule == null) return;
    var result:string = "";
    var dates:Date[] = [];
    var prevDate:string = "";
    schedule.lectures.forEach((lecture)=>{
      if(new Date(lecture.start[0],lecture.start[1]-1,lecture.start[2]).toISOString() !== prevDate){
        dates.push(new Date(lecture.start[0],lecture.start[1]-1,lecture.start[2]));
        prevDate = new Date(lecture.start[0],lecture.start[1]-1,lecture.start[2]).toISOString()
      }
        
    });
    dates.forEach((date) =>{
      result += this.getDisplayForSingleDay(schedule.lectures.filter((lecture) =>{
        //if(date.valueOf() === new Date(lecture.start[0],lecture.start[1],lecture.start[2]).valueOf())
        //console.log(date, new Date(lecture.start[0],lecture.start[1],lecture.start[2]))
        return date.valueOf() === new Date(lecture.start[0],lecture.start[1]-1,lecture.start[2]).valueOf();
        //return true
      }),
      date
      );
      
    });
    $("#startScheduleHolder").html("");
    $("#startScheduleHolder").append($(result));
  }


  getDisplayForSingleDay(lectures:Lecture[], date:Date){
    var singles:string = "";
    var dateVar:Date = date;
    
    lectures.forEach((lecture)=>{
      singles += this.getDisplayForSingleLecture(lecture);
    });
    return '<li class="list-group-item"><div class="card mb-2"><div class="card-header">'+
    this.getWeekdayBy(dateVar) +", "+dateVar.getDate()+"."+(dateVar.getMonth()+1)+"."+dateVar.getFullYear()+
    '</div><ul class="list-group list-group-flush">'+
     singles+  
    '</ul></div></li>';
  }
  getWeekdayBy(date:Date){
    var days:string[] = ["Sonntag", "Montag", "Dienstag", "Mittwoch", "Donnerstag", "Freitag", "Samstag", "Sonntag"];
    return days[date.getDay()];
  }

  getDisplayForSingleLecture(lecture:Lecture){
    var minute1:string;
    var minute2:string;
    if(lecture.start[4] < 10) minute1 = "0"+lecture.start[4];
    else minute1 = lecture.start[4]+"";
    if(lecture.end[4] < 10) minute2 = "0"+lecture.end[4];
    else minute2 = lecture.end[4]+"";
    return '<li class="list-group-item schedule">'+
        lecture.title+'<br>'+
        (lecture.start[3]+1)+":"+minute1+" bis "+(lecture.end[3]+1)+":"+minute2+" Uhr"+
      '</li>';
  }

}
