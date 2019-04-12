<!doctype html>
<html>
<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <title>Update Process</title>

</head>
<body>


<?php

require_once("DBLogin.php");
$db = connectToDB($host,$user,$password,$database);



	$email = $_POST['email'];
	$password = $_POST['password'];
	$newPassword = crypt($password, CRYPT_STD_DES);
	

	$sqlQuery = sprintf("select * from %s where email='%s' and password='%s'", $table, $email, $newPassword);

	$result = mysqli_query($db, $sqlQuery);
	
	if ($result) {
	
		$numberOfRows = mysqli_num_rows($result);

    	if ($numberOfRows == 0) {
      	echo "<h2>No entry exists in the database for the specified email and password.</h2>";
    
    	} else {

      		$recordArray = mysqli_fetch_array($result, MYSQLI_ASSOC);

      		$body = "<form action = 'afterUpdate.php' method = 'post'>";


        	$name = $recordArray['name'];
        	$body .= "<strong> Name:</strong> <input type='text' name='oldName' value= {$name} required> <br>";
        	
        	$email = $recordArray['email'];
        	$body.= "<strong> Email:</strong> <input type='email' name='oldEmail' value= {$email} required> <br>";
         	
        	$gpa = $recordArray['gpa'];
        	 $body .= "<strong> GPA:</strong> <input type='number' name='oldGPA' value= {$gpa} required> <br>";

        	$year = $recordArray['year'];
         	$body .= "<strong> Year:</strong>";


         	if($year == 10){
         		$body.= "
         		<input type='radio' value='10' name='oldYear' checked = 'checked'>10 
        		
        		<input type='radio' value='11' name='oldYear'>11 
        
       		 	<input type='radio' value='11' name='oldYear'>12

         		";
         	}else if ($year == 11){
         		 $body .= "
         		<input type='radio' value='10' name='oldYear'>10 
        		
        		<input type='radio' value='11' name='oldYear' checked = 'checked'>11 
        
       		 	<input type='radio' value='11' name='oldYear'>12

         		";
         	}else if($year == 12) {
         		 $body .= "
         		<input type='radio' value='10' name='oldYear'>10 
        		
        		<input type='radio' value='11' name='oldYear' >11 
        
       		 	<input type='radio' value='11' name='oldYear' checked = 'checked'>12
         		";
         	}else{
         		echo"year option has isssue in updateProcess.php";
         	}

        

        
        	$gender = $recordArray['gender'];

        	if($gender == "M"){
        	$body .= "<br> <strong>Gender</strong>
            <input type='radio' value='M' name='oldGender' checked = 'checked'>M
        	<input type='radio' value='F' name='oldGender'>F ";
        	}else if($gender == "F"){
        	 $body .= "<br> <strong>Gender</strong>
             <input type='radio' value='M' name='oldGender'>M
        	<input type='radio' value='F' name='oldGender' checked = 'checked'>F <br>";
        	}else{
        		echo "gender option has issue in updateProcess.php";
        	}

        	$body .= "<br><strong> Password:</strong> <input type='password' name='oldPassword' value= {$password} required> <br>";
        	}
            echo $body;
	} else { 				   
		echo "Inserting records failed.".mysqli_error($db);
	}
		
	
	
    //echo "<strong> Verify Password:</strong> <input type='password' name='oldVerifyfPassword' value= {$password} required> <br>";


	 mysqli_free_result($result);
	mysqli_close($db);

	

function connectToDB($host, $user, $password, $database) {
	$db = mysqli_connect($host, $user, $password, $database);
	if (mysqli_connect_errno()) {
		echo "Connect failed.\n".mysqli_connect_error();
		exit();
	}
	return $db;
}

?>




		<input type="submit" value="Submit Data" name="submit5">
 	   	<br>
       	 <button type="submit" onclick="location.href ='main.html';">Return to main menu</button>
        	














</body> 
 </html> 	