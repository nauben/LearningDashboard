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
    this.submitted = true;

    this.loading = true;
    this.accountService.login(
      (<HTMLInputElement>document.getElementById('Email-Login')).value, (<HTMLInputElement>document.getElementById('Password-Login')).value
    )
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
