import { Component, OnInit } from '@angular/core';
import {FormControl} from '@angular/forms';
import {MatDatepickerInputEvent} from '@angular/material/datepicker';
import {Observable} from 'rxjs';
import {map, startWith} from 'rxjs/operators';
import { first } from 'rxjs/operators';
import {ScheduleService} from '../../_services/schedule.service';
import {Schedule, Lecture} from '../../_models';
import { ValueTransformer } from '@angular/compiler/src/util';

@Component({
  selector: 'app-schedule',
  templateUrl: './schedule.component.html',
  styleUrls: ['./schedule.component.css']
})
export class ScheduleComponent implements OnInit {
  myControl = new FormControl();
  myDateStartControl = new FormControl();
  myDateEndControl = new FormControl();
  options: string[] = [];// = ['WI17A', 'WI17B', 'WI17C', 'WI17D', 'WI18A', 'WI18B', 'WI18C', 'WI18D', 'WI19A', 'WI19B', 'WI19C', 'WI20A', 'WI20B', 'WI20C'];
  filteredOptions: Observable<string[]>;
  entries:any[];

  constructor(
      private scheduleService:ScheduleService
    ) { }

  ngOnInit(): void {
    this.loadCourses()
    this.loadSelectedCourse();
    this.myDateStartControl.setValue(new Date(Date.now()));
    this.myDateEndControl.setValue(new Date(Date.now()+1000*3600*24*3));
    this.loadSchedule();
  }

  private updateFilterOptions(){
    this.filteredOptions = this.myControl.valueChanges.pipe(
      startWith(''),
      map(value =>
        this._filter(value)
       )
    );
  }

  private _filter(value: string): string[] {
    const filterValue = value.toLowerCase();
    this.onCourseSelect(value);
    return this.options.filter(option => option.toLowerCase().indexOf(filterValue) === 0);
  }

  addEvent(type: string, event: MatDatepickerInputEvent<Date>) {
    //this.events.push(`${type}: ${event.value}`);
    console.log(event);
  }

  loadCourses(){
    this.scheduleService.getAllCourses().pipe(first())
    .subscribe(
        data => {
           this.options = data.courses;
           console.log(data)
           this.updateFilterOptions()
        },
        error => {
            console.log(error);
        });
  }

  loadSelectedCourse(){
    this.scheduleService.getSavedCourse().pipe(first())
    .subscribe(
        data => {
           console.log(data)
           this.myControl.setValue(data.course);
        },
        error => {
            console.log(error);
        });
  }

  onCourseSelect(course:string){
    if(this.options.indexOf(course) >= 0){
      console.log("save course: "+course)
      this.saveSelectedCourse(course);
    }
    

  }

  saveSelectedCourse(course:string){
    this.scheduleService.setCourse(course);
  }

  loadSchedule(){
    this.scheduleService.getSchedule().pipe(first())
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
    var dateStart:Date = this.myDateStartControl.value;
    var dateEnd:Date = this.myDateEndControl.value;
    var result:string = "";
    var dates:Date[] = [];
    schedule.lectures.forEach((lecture)=>{
      if(dates.indexOf(new Date(lecture.start[0],lecture.start[1],lecture.start[2])) == -1)
        dates.push(new Date(lecture.start[0],lecture.start[1],lecture.start[2]));
    });

    dates.reverse().filter((date)=>{
      console.log(date, (dateStart.valueOf() <= date.valueOf()) && (dateEnd.valueOf() >= date.valueOf()), dateStart.valueOf(), date.valueOf(), dateEnd.valueOf())
      return dateStart.valueOf() <= date.valueOf() && dateEnd.valueOf() >= date.valueOf()
      //console.log(dateStart.toISOString(), date, dateEnd.toISOString(), date.localeCompare(dateStart.toISOString()) >= 0 &&  date.localeCompare(dateEnd.toISOString()) <= 0)
      //return date.value
    }).forEach((date) =>{
      result += this.getDisplayForSingleDay(schedule.lectures.filter((lecture) =>{
        return date.valueOf() === new Date(lecture.start[0],lecture.start[1],lecture.start[2]).valueOf();
      }),
      date
      );
      
    });
    $("#scheduleHolder").html("");
    $("#scheduleHolder").append($(result));
  }


  getDisplayForSingleDay(lectures:Lecture[], date:Date){
    console.log("test")
    var singles:string = "";
    var dateVar:Date = date;
    
    lectures.forEach((lecture)=>{
      singles += this.getDisplayForSingleLecture(lecture);
    });
    return '<li class="list-group-item"><div class="card mb-2"><div class="card-header">'+
    this.getWeekdayBy(dateVar) +", "+dateVar.getDate()+"."+dateVar.getMonth()+"."+dateVar.getFullYear()+
    '</div><ul class="list-group list-group-flush">'+
     singles+  
    '</ul></div></li>';
  }
  getWeekdayBy(date:Date){
    var days:string[] = ["Montag", "Dienstag", "Mittwoch", "Donnerstag", "Freitag", "Samstag", "Sonntag"];
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
        lecture.start[3]+":"+minute1+" bis "+lecture.end[3]+":"+minute2+" Uhr"+
      '</li>';
  }



}
