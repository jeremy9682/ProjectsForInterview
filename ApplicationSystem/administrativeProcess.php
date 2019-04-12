<!doctype html>
<html>
<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <title>Administrative Process</title>

</head>
<body>
	<h1>Application</h1>

	<?php
		require_once("DBLogin.php");

		$db = connectToDB($host,$user,$password,$database);

		$fields = $_POST['fields'];
		
		$newfields = implode(",", $fields);

		$sortField = $_POST['sortField'];

		$condition = trim($_POST['condition']);


		if($condition != ""){
			$condition = "where {$condition}";
		}
		
		$sqlQuery = sprintf("select %s from %s %s order by %s", $newfields, $table, $condition, $sortField);

		$result = mysqli_query($db, $sqlQuery);

	    $tableToDisplay = "";
            $tableToDisplay .= "<table border = '1'><tr>";

            foreach ($fields as $value) {
            	//echo"<br>";
            	//echo "$value";
            	$tableToDisplay .= "<td><strong>{$value}</strong></td>";
            }
            $tableToDisplay .= "</tr>";

	if ($result) {
	
		$numberOfRows = mysqli_num_rows($result);

    	if ($numberOfRows == 0) {
      	echo "<h2>No entry exists in the database for the specified email and password.</h2>";
    
    	} else {
    		
    		 while ($recordArray= mysqli_fetch_array($result, MYSQLI_ASSOC)) {
             		$tableToDisplay .= "<tr>";
             		foreach ($fields as $value) {
             			$elements = $recordArray[$value];
             	
             			$tableToDisplay .= "<td>$elements</td>";
             		}
             		$tableToDisplay .= "</tr>";
             }
		}

	}else{
		 echo "Retrieving records failed.".mysqli_error($db);
	}	
             $tableToDisplay .= "</table>";
             echo $tableToDisplay;

 mysqli_free_result($result);
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
