import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-login-text-only',
  templateUrl: './login-text-only.component.html',
  styleUrls: ['./login-text-only.component.css']
})
export class LoginTextOnlyComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

  showPassword1L() {
    console.log("JS loaded!");
      var x = (<HTMLInputElement>document.getElementById("Password-Login"));
      if (x.type === "password") {
        x.type = "text";
        } else {
          x.type = "password";
          }
  } 
}
