var randomNumber;
var guessCount;

var startMenu;
var resultMenu;
var firstResult;
var secondResult;

var guessSubmit;
var guessField;

var winMenu;
var tries;
var loseMenu;

function ready() {
	startMenu = document.getElementById("start_menu");
	resultMenu = document.getElementById("result");
	firstResult = document.getElementById("first_result");
	secondResult = document.getElementById("second_result");	

	guessSubmit = document.getElementById("guessSubmit");
	guessField = document.getElementById("guessField");

	winMenu = document.getElementById("win");
	tries = document.getElementById("triesField");
	loseMenu = document.getElementById("lose");
}



function start() {
	startMenu.style.display = 'block';
	resetGame();
}

function checkGuess() {
	var userGuess = Number(guessField.value);

	if (userGuess === randomNumber) {
		alert("Congrats: you guessed it!");
		guessSubmit.disabled = true;

		resultMenu.style.backgroundColor = "#aaeeaa";
		resultMenu.style.borderColor = " #86ca86"; 
		firstResult.textContent = 'You are a winner! We like winners, this is  why we want to keep your score!';
		secondResult.textContent = 'Kindly write your name down here and submit to add you to the list of winners!';
		resultMenu.style.display = 'block';
		tries.value = guessCount;
		winMenu.style.display = 'block';
	} else if (guessCount === 3) {
		alert("Sorry, you lost! You have tried all your 3 chances.");
		guessSubmit.disabled = true;
		
		resultMenu.style.backgroundColor = "#ee7777";
		resultMenu.style.borderColor = "#cb8686";
		firstResult.textContent = 'Unfotunately you didn\'t win!:-(';
		secondResult.textContent = 'The correct number was ' + randomNumber + '.';
		resultMenu.style.display = 'block';
		loseMenu.style.display = 'block';
	} else {
		if (userGuess < randomNumber) {
			alert("The number you entered is lower than the correct answer!");
		} else if (userGuess > randomNumber) {
			alert("The number you entered is higher than the correct answer!");
		}
	}

	guessCount++;
	
}

function resetGame() {
	randomNumber = Math.floor(Math.random() * 20) + 1;
	guessCount = 1;

	guessField.value = "";
	winMenu.style.display = 'none';
	loseMenu.style.display = 'none';
	resultMenu.style.display = 'none';
	guessSubmit.disabled = false;
}


