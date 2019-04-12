window.onsubmit=validateForm;
function validateForm() {

    var firstThree = document.getElementById("phonefirstpart").value;
    var secondThree = document.getElementById("phonesecondpart").value;
    var lastFour = document.getElementById("phonethirdpart").value;
    var message = " ";

    if(!checkNumberValidation(firstThree) || !checkNumberValidation(secondThree)){
    	message += "Invalid phone number\n";
    }
    if(lastFour.length != 4 || isNaN(lastFour)){
    	message +=  "Invalid phone number\n";
    }

    if(checkConditionValition()){
    	message += "Invalid conditions selection\n";
    }

    if(checkNoCondtionsSelected()){
    	message += "No conditions selected\n";
    }

    if(!checkTimePeriod()){
    	message += "No time period selected\n";
    }

    if(!checkID()){
    	message += "Invalid study id";
    }


	if(message == " "){
		var noError = "Do you want to submit the following payment?\n";
		if(window.confirm(noError)){
			return true;
		}else{
			return false;
		}
	} else{
		alert(message);
		return false;

	}   


}







function checkNumberValidation(num){
	if(num.length == 3 && !isNaN(num)){
		return true;
	}else{
		return false;
	}

}

function checkConditionValition(){
	if(document.getElementById("none").checked){
		if ( document.getElementById("highbloodpressure").checked ||
		 document.getElementById("diabetes").checked ||
		 document.getElementById("glaucoma").checked ||
		 document.getElementById("asthma").checked){
			return true;
		}
	return false;
  }
}

function checkNoCondtionsSelected(){
	if(!document.getElementById("none").checked &&
		!document.getElementById("highbloodpressure").checked &&
		!document.getElementById("diabetes").checked &&
		!document.getElementById("glaucoma").checked &&
		!document.getElementById("asthma").checked){
		return true;
	}else{
		return false;
	}

}

function checkTimePeriod(){
	if(!document.getElementById("Never").checked &&
		!document.getElementById("Lessthanayear").checked &&
		!document.getElementById("Onetotwoyears").checked &&
		!document.getElementById("Morethantwoyears").checked){
		return false;
	}else{
		return true;
	}
}

function checkID(){

	var firstfour = document.getElementById("idfirstfour").value;
	var lastfour = document.getElementById("idlastfour").value;

	if(firstfour.charAt(0) == "A" && lastfour.charAt(0) == "B"){
		var firstThreeDigits = firstfour.substr(1,3); 
		var lastThreeDigits = lastfour.substr(1,3);
		if(!isNaN(firstThreeDigits) && !isNaN(lastThreeDigits)&&
			firstThreeDigits.length == 3 && lastThreeDigits.length == 3){
		return true;
		}
	}
	return false;
}