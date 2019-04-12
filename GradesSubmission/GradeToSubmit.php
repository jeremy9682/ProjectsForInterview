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


<strong>Grades Submission Form</strong>
<br>

<form action="GradeToSubmit.php" method="post">


<?php 
	session_start();
	require("studentObject.php");

	if(!isset($_POST['submitToServer'])){

	$students = array();
	$students = $_SESSION['array'];
	$body = "";
	$body.= '<table>';
	$body .= '<tr><td><strong>Name</strong> </td> 
			<td><strong>Grade</strong> </td></tr><br>';

		foreach ($students as $name) {
			if(!isset($_POST[$name])){
				$body .=  "<tr><td>".$name."</td> <td>NG</td></tr><br>";

			}else{$body .=  "<tr><td>".$name."</td> <td>".$_POST[$name]."</td></tr><br>";}
	}

	$body .= "</table>";
	echo $body;
}else{
	$_SESSION['submit_Time'] = $_SESSION['submit_Time'] + 1;
	header("Location: submitToServer.php");
}


if(isset($_POST['Back'])){
	header("Location: Grade_Form.php");
}



	?>

<input type = "submit" name = "submitToServer" value="Submit Grades">
<br>
<input type = "submit" name = "Back" value = "Back">


</form>


</strong>
</html>