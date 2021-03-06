import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { first } from 'rxjs/operators';
import { AccountService} from '../../_services/account.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
    loading = false;
    submitted = false;

  constructor(
    private route: ActivatedRoute,
        private router: Router,
        private accountService: AccountService,
  ) { }

  ngOnInit(): void {
  //Password Register validation
    var myInput = (<HTMLInputElement>document.getElementById("Password-Register"));
    var letter = (<HTMLInputElement>document.getElementById("letter"));
    var capital = (<HTMLInputElement>document.getElementById("capital"));
    var number = (<HTMLInputElement>document.getElementById("number"));
    var length = (<HTMLInputElement>document.getElementById("length"));
    
    // When the user clicks on the password field, show the message box
    myInput.onfocus = function() {
      document.getElementById("message").style.display = "block";
    }
    
    // When the user clicks outside of the password field, hide the message box
    myInput.onblur = function() {
      document.getElementById("message").style.display = "none";
    }
    
    // When the user starts to type something inside the password field
    myInput.onkeyup = function() {
      // Validate lowercase letters
      var lowerCaseLetters = /[a-z]/g;
      if(myInput.value.match(lowerCaseLetters)) {
        letter.classList.remove("invalid-psw");
        letter.classList.add("valid-psw");
      } else {
        letter.classList.remove("valid-psw");
        letter.classList.add("invalid-psw");
    }
    
      // Validate capital letters
      var upperCaseLetters = /[A-Z]/g;
      if(myInput.value.match(upperCaseLetters)) {
        capital.classList.remove("invalid-psw");
        capital.classList.add("valid-psw");
      } else {
        capital.classList.remove("valid-psw");
        capital.classList.add("invalid-psw");
      }
    
      // Validate numbers
      var numbers = /[0-9]/g;
      if(myInput.value.match(numbers)) {
        number.classList.remove("invalid-psw");
        number.classList.add("valid-psw");
      } else {
        number.classList.remove("valid-psw");
        number.classList.add("invalid-psw");
      }
    
      // Validate length
      if(myInput.value.length >= 8) {
        length.classList.remove("invalid-psw");
        length.classList.add("valid-psw");
      } else {
        length.classList.remove("valid-psw");
        length.classList.add("invalid-psw");
      }
    }
    }

    showPassword2L() {
      var x = (<HTMLInputElement>document.getElementById("Password-Register"));
      if (x.type === "password") {
        x.type = "text";
        } else {
          x.type = "password";
          }
    var y = (<HTMLInputElement>document.getElementById("Password-Confirmation"));
      if (y.type === "password") {
        y.type = "text";
        } else {
          y.type = "password";
          }
  }

  onSubmit() {
    var email = (<HTMLInputElement>document.getElementById('Email-Login')).value;
    var password = (<HTMLInputElement>document.getElementById('Password-Register')).value;
    var password2 = (<HTMLInputElement>document.getElementById('Password-Confirmation')).value;
    var emailFormat = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/;
    
    if(email == "" && password == "" && password2 == ""){
      alert("Bitte alle Felder ausfüllen");
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
    if(password == "" || password2 == ""){
      alert("Bitte Passwort zweimal eingeben");
      return
    }

    if(password != password2){
      alert("Die eingegebenen Passwörter stimmen nicht überein");
      return
    }

    this.submitted = true;
    this.loading = true;
    this.accountService.register(email,password)
        .pipe(first())
        .subscribe(
            data => {
                this.router.navigate(['../login'], { relativeTo: this.route });
            },
            error => {
                this.loading = false;
                console.log(error)
            });
}

}
