/*eslint-env es6*/
/*function showPassword1L() {
	console.log("JS loaded!");
		var x = document.getElementById("Password-Login");
		if (x.type === "password") {
			x.type = "text";
			} else {
				x.type = "password";
				}
} */

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

//####################
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
//####################

//####################
//Enable drag and drop for Kanban-Board 

	const drag = (event) => {
	event.dataTransfer.setData("text/plain", event.target.id);
	}

	const allowDrop = (ev) => {
	ev.preventDefault();
	if (hasClass(ev.target,"dropzone")) {
		addClass(ev.target,"droppable");
	}
	}

	const clearDrop = (ev) => {
		removeClass(ev.target,"droppable");
	}

	const drop = (event) => {
	event.preventDefault();
	const data = event.dataTransfer.getData("text/plain");
	const element = document.querySelector(`#${data}`);
	try {
		// remove the spacer content from dropzone
		event.target.removeChild(event.target.firstChild);
		// add the draggable content
		event.target.appendChild(element);
		// remove the dropzone parent
		unwrap(event.target);
	} catch (error) {
	return null;
	}
	updateDropzones();
	}

	const updateDropzones = () => { 
		/* after dropping, refresh the drop target areas
		  so there is a dropzone after each item and after the heading
		  using jQuery here for simplicity */

		var dz = '<div class="dropzone rounded" ondrop="drop(event)" ondragover="allowDrop(event)" ondragleave="clearDrop(event)"> &nbsp; </div>';

		// delete old dropzones
		$('.dropzone').remove();
		
		/*// insert new dropzone after swimlane name (custom)
		dz.insertAfter('.card-subtitle.draggable'); */
		
		// insert new dropzone after each item (deprecated)
		//dz.insertAfter('.card.draggable');
	
		
		$('.items').prepend(dz);
		$('.card.draggable').after(dz);
		
		// insert new dropzone in any empty swimlanes (deprecated)
		//$(".items:not(:has(.card.draggable))").append(dz);
		
		
	};

	// helpers
	function hasClass(target, className) {
		return new RegExp('(\\s|^)' + className + '(\\s|$)').test(target.className);
	}

	function addClass(ele,cls) {
	if (!hasClass(ele,cls)) ele.className += " "+cls;
	}

	function removeClass(ele,cls) {
	if (hasClass(ele,cls)) {
		var reg = new RegExp('(\\s|^)'+cls+'(\\s|$)');
		ele.className=ele.className.replace(reg,' ');
	}
	}

	function unwrap(node) {
		node.replaceWith(...node.childNodes);
	}
//####################
function chooseSwimlane(){
var sl = $("#swimlane").val();
var todo = $("#todo")[0];
var inarbeit = $("#inarbeit")[0];
var fertig = $("#fertig")[0];

	
	if	(sl === "In Arbeit"){
		todo.setAttribute('style', 'display:none !important');
		inarbeit.setAttribute('style', 'display:block !important');
		fertig.setAttribute('style', 'display:none !important');
	}else if(sl === "Fertig"){ 
		todo.setAttribute('style', 'display:none !important');
		inarbeit.setAttribute('style', 'display:none !important');
		fertig.setAttribute('style', 'display:block !important');
	}else {
		todo.setAttribute('style', 'display:block !important');
		inarbeit.setAttribute('style', 'display:none !important');
		fertig.setAttribute('style', 'display:none !important');
	}$('#swimlane').val("Swimlane auswählen");
} 

/*
 //notice the particular screen size
function sizeKan(){
	let envs = ['xs', 'sm', 'md', 'lg', 'xl'];

    let el = document.createElement('div');
    document.body.appendChild(el);

    let curEnv = envs.shift();

    for (let env of envs.reverse()) {
        el.classList.add(`d-${env}-none`);

        if (window.getComputedStyle(el).display === 'none') {
            curEnv = env;
            break;
        }
    }

    document.body.removeChild(el);
    resizeKan(curEnv);
	
} 

//if scren size is xl or lg, then show all three swimlanes of the Kanban-Board
function resizeKan(curEnv){
	var todo = $("#todo")[0];
	var inarbeit = $("#inarbeit")[0];
	var fertig = $("#fertig")[0];
	var size = curEnv;

	if(size === "lg" || size === "xl"){
		todo.style.display = "block";
		inarbeit.style.display = "block";
		fertig.style.display = "block";	
	}
	
	/*if(size === "xs" || size === "sm" || size === "md"){
		todo.style.display = "block";
		inarbeit.style.display = "none";
		fertig.style.display = "none";	
	} */
	/*	
	
} */


/*
//hide/show sidebar
$(document).ready(function () {
            $('#sidebarCollapse').on('click', function () {
                $('#sidebar').toggleClass('active');
            });
        }); */