import { Component, OnInit } from '@angular/core';
import {FormControl} from '@angular/forms';
import {Observable} from 'rxjs';
import {map, startWith} from 'rxjs/operators';
import { first } from 'rxjs/operators';
import {ScheduleService} from '../../_services/schedule.service';

@Component({
  selector: 'app-schedule',
  templateUrl: './schedule.component.html',
  styleUrls: ['./schedule.component.css']
})
export class ScheduleComponent implements OnInit {
  myControl = new FormControl();
  options: string[] = [];// = ['WI17A', 'WI17B', 'WI17C', 'WI17D', 'WI18A', 'WI18B', 'WI18C', 'WI18D', 'WI19A', 'WI19B', 'WI19C', 'WI20A', 'WI20B', 'WI20C'];
  filteredOptions: Observable<string[]>;
  entries:any[];

  constructor(
      private scheduleService:ScheduleService
    ) { }

  ngOnInit(): void {
    this.loadCourses()
    this.loadSelectedCourse();
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

  }


}
