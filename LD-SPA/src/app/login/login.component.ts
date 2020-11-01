import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }
  showPassword1L() {
      var x = (<HTMLInputElement>document.getElementById("Password-Login"));
      if (x.type === "password") {
        x.type = "text";
        } else {
          x.type = "password";
          }
  }
}
