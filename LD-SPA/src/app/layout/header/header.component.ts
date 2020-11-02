import { Component, OnInit } from '@angular/core';
import {AccountService} from '../../_services/account.service'

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  greeting:string;
  name:string;

  constructor(private accountService:AccountService) { }

  ngOnInit(): void {
    $(document).ready(function () {
      $('#sidebarCollapse').on('click', function () {
          $('#sidebar').toggleClass('active');
      });
    });

    var d:Date = new Date();
    console.log(d.getHours())
    if(d.getHours() < 11)
      this.greeting = "Guten Morgen";
    else if(d.getHours() < 17)
      this.greeting = "Guten Tag";
    else this.greeting = "GutenAbend";
    const user = this.accountService.userValue;
    if(user && user.firstname && user.lastname)
      this.name = ", "+user.firstname+""+user.lastname;
  }

  

}
