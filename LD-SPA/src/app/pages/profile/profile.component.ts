import { Component, OnInit } from '@angular/core';
import { AccountService} from '../../_services/account.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  constructor(
	private accountService: AccountService
  ) { }

  ngOnInit(): void {
    //Password Register validation

	var myInput2 = (<HTMLInputElement>document.getElementById("Password-New"));
	var letter = (<HTMLInputElement>document.getElementById("letter"));
	var capital = (<HTMLInputElement>document.getElementById("capital"));
	var number = (<HTMLInputElement>document.getElementById("number"));
	var length = (<HTMLInputElement>document.getElementById("length"));
	
	// When the user clicks on the password field, show the message box
	myInput2.onfocus = function() {
	  document.getElementById("message").style.display = "block";
	}
	
	// When the user clicks outside of the password field, hide the message box
	myInput2.onblur = function() {
	  document.getElementById("message").style.display = "none";
	}
	
	// When the user starts to type something inside the password field
	myInput2.onkeyup = function() {
	  // Validate lowercase letters
	  var lowerCaseLetters = /[a-z]/g;
	  if(myInput2.value.match(lowerCaseLetters)) {
		letter.classList.remove("invalid-psw");
		letter.classList.add("valid-psw");
	  } else {
		letter.classList.remove("valid-psw");
		letter.classList.add("invalid-psw");
	}
	
	  // Validate capital letters
	  var upperCaseLetters = /[A-Z]/g;
	  if(myInput2.value.match(upperCaseLetters)) {
		capital.classList.remove("invalid-psw");
		capital.classList.add("valid-psw");
	  } else {
		capital.classList.remove("valid-psw");
		capital.classList.add("invalid-psw");
	  }
	
	  // Validate numbers
	  var numbers = /[0-9]/g;
	  if(myInput2.value.match(numbers)) {
		number.classList.remove("invalid-psw");
		number.classList.add("valid-psw");
	  } else {
		number.classList.remove("valid-psw");
		number.classList.add("invalid-psw");
	  }
	
	  // Validate length
	  if(myInput2.value.length >= 8) {
		length.classList.remove("invalid-psw");
		length.classList.add("valid-psw");
	  } else {
		length.classList.remove("valid-psw");
		length.classList.add("invalid-psw");
	  }
	}
	}
  
  showPassword2EP() {
    var x = (<HTMLInputElement>document.getElementById("Password-current"));
    if (x.type === "password") {
      x.type = "text";
      } else {
        x.type = "password";
        }
  var y = (<HTMLInputElement>document.getElementById("Password-New"));
    if (y.type === "password") {
      y.type = "text";
      } else {
        y.type = "password";
        }
  }

  onLogout(){
    console.log('Bye');
    this.accountService.logout();
  }

}
