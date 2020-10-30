import { Component, OnInit } from '@angular/core';
import {FormControl} from '@angular/forms';
import {Observable} from 'rxjs';
import {map, startWith} from 'rxjs/operators';

@Component({
  selector: 'app-schedule',
  templateUrl: './schedule.component.html',
  styleUrls: ['./schedule.component.css']
})
export class ScheduleComponent implements OnInit {
  myControl = new FormControl();
  options: string[] = ['WI17A', 'WI17B', 'WI17C', 'WI17D', 'WI18A', 'WI18B', 'WI18C', 'WI18D', 'WI19A', 'WI19B', 'WI19C', 'WI20A', 'WI20B', 'WI20C'];
  filteredOptions: Observable<string[]>;


  constructor() { }

  ngOnInit(): void {
    this.filteredOptions = this.myControl.valueChanges.pipe(
      startWith(''),
      map(value => this._filter(value))
    );
  }

  private _filter(value: string): string[] {
    const filterValue = value.toLowerCase();

    return this.options.filter(option => option.toLowerCase().indexOf(filterValue) === 0);
  }

}
