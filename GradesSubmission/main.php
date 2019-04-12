




<!doctype html>
<html>
    <head> 
        <meta charset="utf-8"/> 
		<title>Grades Submission System</title>	

		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">

		
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">


  		<script src="bootstrap/jquery-3.2.1.min.js"></script>
   		<script src="bootstrap/js/bootstrap.min.js"></script>


	</head>

	<body>
	<div class="container-fluid"> 
		<form action="main.php" method="post">
			<strong><h1>Grades Submission System</h1></strong>
			<div class="form-group"> 
				<label>LoginId: </label>
				<input type="text" name="login"/>
			</div>

			<div class="form-group"> 
				<strong>Password: </strong> 
				<input type="password"  name = "password"/>
			</div>

			<div>
				<input type="submit" name="Submit" value = "Submit"/>
			</div>
	</form>
<?php




	session_start();
	$_SESSION['submit_Time'] = 0;
	if(!isset($_COOKIE['checked'])){


		if(isset($_POST["Submit"])){

	$loginId = trim($_POST["login"]);
	$password = trim($_POST["password"]);


			if ($loginId !== "testudo" ){
				echo "Invalid login information provided";
			}else if ($password !== "terps"){
				print("Invalid login information provided");
			}else{
				if($_COOKIE['checked']==null){
					$name = "checked";
					$value = "user";
					$path = "/";
					$domain = "";
					$expiration =0;
					$domain ="";
					setcookie($name,$value,$expiration,$path,$domain,0);
				}
				header("Location: Input_Section.php");
			}

			
	}


}else{
	header("Location: Input_Section.php");
}






?>






</body>
</html>



