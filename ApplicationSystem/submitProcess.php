
<!doctype html>
<html>
<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <title>AddToDB</title>

</head>
	
<body>




	
<?php  

require_once("DBLogin.php");

$db = connectToDB($host,$user,$password,$database);

	$host = "localhost";
	$user = "dbuser";
	$database = "applicationdb";
	$table = "applicants";	


  	$password = $_POST['password'];
    $newPassword = crypt($password,CRYPT_STD_DES);

    $Name = trim($_POST['name']);
	 $Email = trim($_POST['email']);
	 $Gpa = trim($_POST['gpa']);
	 $Year = trim($_POST['year']);
	 $Gender = trim($_POST['gender']);


	$sqlQuery = "insert into $table values('$Name', '$Email', '$Gpa', '$Year', '$Gender', '$newPassword')";

	$result = mysqli_query($db, $sqlQuery);
	
	if ($result) {
		echo "<h3>The entry has been added to the database</h3>";
	} else { 				   
		echo "Inserting records failed.".mysqli_error($db);
	}
		
	
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

<?php
echo "<strong>Name:</strong> {$Name} <br>";
echo "<strong>Email:</strong>  {$Email} <br>";
echo "<strong>Gpa:</strong>  {$Gpa} <br>";
echo "<strong>Year:</strong>  {$Year} <br>";
echo "<strong>Gender:</strong>  {$Gender} <br>";
?>



	
	




    <button type="submit" onclick="location.href ='main.html';">Return to main menu</button>
</body>
</html>














