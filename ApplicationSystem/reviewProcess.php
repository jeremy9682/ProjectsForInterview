<!doctype html>
<html>
<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <title>reviewPorcess</title>

</head>
<body>
<h1>
	Application found in the database with the following values:
</h1>	
	<?php
    $Email = trim($_POST['newEmail']);
    $password = trim($_POST["newPassword"]);
    ?>



<?php 

    require_once("DBLogin.php");

$db = connectToDB($host,$user,$password,$database);

    $newPassword = crypt($password, CRYPT_STD_DES);

  $table = "applicants";
    

    $sqlQuery = "select * from $table where email='$Email' AND password='$newPassword'";

     $result = mysqli_query($db, $sqlQuery);
    
     if ($result) {
    
    $numberOfRows = mysqli_num_rows($result);

        if ($numberOfRows == 0) {
        echo "<h2>No entry exists in the database for the specified email and password.</h2>";
        $recordArray = mysqli_fetch_array($result, MYSQLI_ASSOC);
        echo $recordArray;
        } else {
            while ($recordArray = mysqli_fetch_array($result, MYSQLI_ASSOC)) {
            $name = $recordArray['name'];
            echo "<strong>Name:</strong> {$name}<br>";

            $email = $recordArray['email'];
            echo "<strong>Email:</strong> {$email}<br>";

            $gpa = $recordArray['gpa'];
            echo "<strong>Gpa:</strong> {$gpa}<br>";

            $year = $recordArray['year'];
            echo "<strong>Year:</strong> {$year}<br>";
        
            $gender = $recordArray['gender'];
            echo "<strong>Gender:</strong> {$gender}<br>";
         }
        }
        mysqli_free_result($result);

}else{
    echo "<h2>Cannot connect to database in reviewPorcess</h2>".mysqli_error($db);
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








	
<button type="submit" onclick="location.href ='main.html';">Return to main menu</button>

</body>
</html>

