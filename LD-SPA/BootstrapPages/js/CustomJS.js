function showPassword1L() {
		var x = document.getElementById("Password-Login");
		if (x.type === "password") {
			x.type = "text";
			} else {
				x.type = "password";
				}
} 

function showPassword2L() {
		var x = document.getElementById("Password-Register");
		if (x.type === "password") {
			x.type = "text";
			} else {
				x.type = "password";
				}
	var y = document.getElementById("Password-Confirmation");
		if (y.type === "password") {
			y.type = "text";
			} else {
				y.type = "password";
				}
}

//Password Register validation
function validatePassword(){
var myInput = document.getElementById("Password-Register");
var letter = document.getElementById("letter");
var capital = document.getElementById("capital");
var number = document.getElementById("number");
var length = document.getElementById("length");

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