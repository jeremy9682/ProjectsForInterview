<!doctype html>
<html>
<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <title>Update Application</title>
</head>

<body>
	<form action="updateProcess.php" method="post">	
		<h1>Email associated with application</h1>
		<input type="email" name="email" required >

		<b>
		<h1>Password associated with application</h1>
		<input type="password" name="password" required>	
		<br>	
		 <input type="submit" value="Submit" name="submit">
		 <br>
		 <button type="submit" onclick="location.href ='main.html';">
		 Return to main menu</button>
	</form>	

</body>
</html>