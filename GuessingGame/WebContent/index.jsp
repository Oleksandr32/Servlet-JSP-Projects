<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>The Guessing Game</title>	
		<link rel="shortcut icon" href="img/guessing.ico" type="image/x-icon">
		<link rel="stylesheet" href="css/style.css">
		<script type="text/javascript" src="js/script.js"></script>
	</head>
	<body onload="ready()">
		<div class="container">
			<h1 class="center">Welcome to the Guessing Game</h1>
			<p class="instruction">
				In this game, you will have to guess a number between 1 and 20. Y have 3 chances to
				get it right. Everytime you give a guess that is not correct, you will be given a hint
				whether the correct number is lower or higher than your guess.
			</p>
			<p class="instruction">
				Try now and get into our <a href="winners">list of winners!</a>	
			</p>
			<div class="center">
				<button type="submit" onclick="start()">Start New Game</button>
			</div>
			<div class="input center hide" id="start_menu">
				<p>
					What is your guess? (number between 1 and 20)
				</p>
				<input type="text" id="guessField">
				<button type="submit" id="guessSubmit" onclick="checkGuess()">OK!</button>
			</div>
			<div id="result" class="center hide">
				<p id="first_result"></p>
				<p id="second_result"></p>
				<div id="lose" class="hide">
					<p><a href="index.jsp">Try again</a></p>	
				</div>
				<div id="win" class="hide">
					<form action="winners" method="post">
						<input type="text" name="name" required>
						<p>Won with <input type="text" id="triesField" name="numOfTries" readonly>tries!</p>	
						<button type="submit">Go!</button>
					</form>
				</div>
			</div>
		</div>
	</body>
</html>