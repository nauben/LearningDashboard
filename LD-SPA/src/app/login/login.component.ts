import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { AccountService} from '../_services/account.service';
import { first } from 'rxjs/operators';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loading = false;
    submitted = false;
    returnUrl: string;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private accountService: AccountService,
  ) { }

  ngOnInit(): void {
     // get return url from route parameters or default to '/'
     this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
  }

  onSubmit() {
    var email = (<HTMLInputElement>document.getElementById('Email-Login')).value;
    var password = (<HTMLInputElement>document.getElementById('Password-Login')).value;
    var emailFormat = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/;
    
    if(email == "" && password == ""){
      alert("Bitte E-Mail-Adresse und Passwort eingeben");
      return
    }
    if(email == ""){
      alert("Bitte E-Mail-Adresse eingeben");
      return
    }
    if(!email.match(emailFormat)){
      alert("Bitte gib eine E-Mail-Adresse ein");
      return
    }
    if(password == ""){
      alert("Bitte Passwort eingeben");
      return
    }

    if(password.length < 8){
      alert("Passwort falsch");
      return
    }

    this.submitted = true;

    this.loading = true;
    this.accountService.login(email, password)
        .pipe(first())
        .subscribe(
            data => {
                this.router.navigate([this.returnUrl]);
            },
            error => {
                this.loading = false;
            });
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
