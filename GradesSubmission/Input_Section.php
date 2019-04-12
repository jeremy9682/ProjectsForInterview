




<!doctype html>
<html>
    <head> 
        <meta charset="utf-8"/> 
		<title>Section Information</title>	

		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">

		
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">


  		<script src="bootstrap/jquery-3.2.1.min.js"></script>
   		<script src="bootstrap/js/bootstrap.min.js"></script>


	</head>

	<body>
	<div class="container-fluid"> 
		<form action="Input_Section.php" method="post">
			<strong><h1>Section Information</h1></strong>
			<div class="form-group"> 
				<label>Course Name: </label>
				<input type="text" name="coursename" placeholder="cmsc389N" />
			</div>

			<div class="form-group"> 
				<lable>Section: </lable> 
				<input type="text"  name = "section" placeholder="0101" />
			</div>

			<div>
				<input type="submit" name="Submit_section" value = "Submit"/>
			</div>
</form>
</div>



</body>
</html>
<?php
	require("studentObject.php");
	if(isset($_POST["Submit_section"])){
		
		session_start();
		$file = $_POST["coursename"].$_POST["section"].".txt";
		$_SESSION['coursename'] = $_POST["coursename"];
		$_SESSION['section']= $_POST["section"];

		if(file_exists($file)){
		
			$filePointer = @fopen($file, "r");
			$Students = array();
			$number = 0;

			$line = trim(fgets($filePointer));
			while(!feof($filePointer)){
				$Students[$number] = $line;
				$line = trim(fgets($filePointer));
				$number = $number + 1;

			}
			$Students[$number] = $line;

			fclose($filePointer);

			$_SESSION['array'] = $Students;

			header('Location: Grade_Form.php');


		}else{
			echo "<strong>Invalid Course Information</strong>";
			
		}
	}

?>
