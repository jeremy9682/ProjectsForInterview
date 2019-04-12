<!doctype html>
<html>
<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <title>Submit Application</title>

</head>
<body>
<form action = "submitProcess.php" method = "post">
	<strong>Name:</strong>
	<input type="text" name="name" required/>
	<br>
	<strong>Email:</strong>
	<input type="email" name="email" required/>	
	<br>	
	<strong>GPA:</strong>
	<input type="number" name="gpa" required/>
	<br>
	<strong>Year:</strong>
     <input type='radio' name="year" value='10'>10
     <input type='radio' name="year" value='11'>11
     <input type='radio' name="year" value='12'>12
     <br>
   
	 <strong>Gender:</strong>
     <input type='radio' name="gender" value='M'>M
     <input type='radio' name="gender" value='F'>F
     <br>
 	 <strong>Password</strong>
 	 <input type="password" name="password" required>
 	 <br>
 	 <strong>Verify Password:</strong> 
 	 <input type='password' name="verifyPassword" required>
 	 <br>
 	  <input type="submit" value="Submit Data" name="submitData">
 	  <button type="submit" onclick="location.href ='main.html';">Return to main menu</button>



</form>



 </body> 
 </html> 	