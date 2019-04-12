<!doctype html>
<html>
<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <title>After Update</title>

</head>
<body>
<h2> The entry has been updated in the database and the new values are: </h2>

<?php
include("DBLogin.php");
$db_connection = new mysqli($host, $user, $password, $database);

$name = trim($_POST['oldName']);
$email = trim($_POST['oldEmail']);
$gpa = trim($_POST['oldGPA']);
$year = trim($_POST['oldYear']);
$gender = trim($_POST['oldGender']);
$password = trim($_POST['oldPassword']);
$newPassword = crypt($password, CRYPT_STD_DES);



if ($db_connection->connect_error) {
		die($db_connection->connect_error);
	}else{
		echo "Connection to database establishied<br>";
	}

$query = "update $table set name='$name',gpa='$gpa',gender='$gender',year='$year',password='$newPassword' where email='$email'";

$result = $db_connection->query($query);
	if (!$result) {
		die("Update failed in afterUpdate: ".$db_connection->error);
			$result->close();
	}

	
	
	
	$newQuery = "select * from $table where email='$email' and password='$newPassword'";

	$newResult = $db_connection->query($newQuery);

	if(!$newResult){
		die("Update failed in afterUpdate: " . $db_connection->error);
	}

	$numberOfRows = mysqli_num_rows($newResult);
 	if ($numberOfRows == 0) {
		die("No entry exists in the database for the specified email and password".$db_connection->error);
	}

	$recordArray = mysqli_fetch_array($newResult, MYSQLI_ASSOC);
	$newName = $recordArray['name'];
	$newEmail = $recordArray['email'];
	$newGender = $recordArray['gender'];
	$newGPA = $recordArray['gpa'];
	$newYear = $recordArray['year'];

	
	$db_connection->close();

$body = "";
$body .= "<strong> Name:</strong> {$newName}<br>";
$body .= "<strong> Email:</strong> {$newEmail} <br>";
$body .= "<strong> GPA:</strong> {$newGPA}<br>";
$body .= "<strong> Year:</strong> {$newYear}<br>";
$body .= "<strong> Gender:</strong> {$newGender}<br>";
echo $body;
?>

<button type="submit" onclick="location.href ='main.html';">Return to main menu</button>


</body>
</html>
