<!doctype html>
<html>
    <head> 
        <meta charset="utf-8"/> 
		<title>Grade Submission Form</title>	

		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">

		
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">


  		<script src="bootstrap/jquery-3.2.1.min.js"></script>
   		<script src="bootstrap/js/bootstrap.min.js"></script>


	</head>

	<body>


<strong>Grades Submission Form</strong>
<form action="GradeToSubmit.php" method="post">
<br>
<br>

	<strong>Course:
		<?php 
		session_start();
		echo $_SESSION['coursename'];
	 	?>
	 </strong>

	 <strong>Section:
		<?php
		echo $_SESSION['section'];
		?>
	 </strong>

	<?php 

	

	require("studentObject.php");
	$students = array();
	$students = $_SESSION['array'];
	$body = "";
	$body.= '<table>';

	foreach ($students as $name) {
		$body .= '<tr><td>'.$name.'</td>';
		$body .=  '<td> A <input type = "radio" name ='.$name.' value = "A"/> </td>';
		$body .=  '<td> B <input type = "radio" name ='.$name.' value = "B"/> </td>';
		$body .=  '<td> C <input type = "radio" name ='.$name.' value = "C"/> </td>';
		$body .=  '<td> D <input type = "radio" name ='.$name.' value = "D"/> </td>';
		$body .=  '<td> F <input type = "radio" name ='.$name.' value = "F"/> </td></tr>'
		;
	}

	$body .= "</table>";
	$body .= '<input type = "submit" name = "submit_Grade" value = "Continue">';
	echo $body;
	
	
	?>



<br>




</form>

</body>

</html>




