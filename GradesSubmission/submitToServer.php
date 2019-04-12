<!doctype html>
<html>
    <head> 
        <meta charset="utf-8"/> 
		<title>Grade To Submit</title>	

		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">

		
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">


  		<script src="bootstrap/jquery-3.2.1.min.js"></script>
   		<script src="bootstrap/js/bootstrap.min.js"></script>


	</head>

	<body>
		<h1>Grades Submitted</h1>
		<br>
		<h1>This is submission#</h1>
<?php
		if(!isset($_POST['Submit'])){
		
		session_start();
		echo "<h1>".$_SESSION['submit_Time']."</h1>";
		
	}else{
	header("Location: Input_Section.php");
}
?>
		<form action="submitToServer.php" method="post">

			<input type="submit" name = "Submit" value = "Enter Grades Anoter Section" />
		</form>

	</body>

	</html>