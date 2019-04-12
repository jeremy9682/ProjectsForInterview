<!doctype html>
<html>
<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <title>Administrative</title>

</head>
<body>
	<h1>Applications</h1>



 <form action="administrativeProcess.php" method="post">
        <strong>Select fields to display:</strong><br>
        <select name="fields[]" multiple required>
            <option value="name">name</option>
            <option value="email">email</option>
            <option value="gpa">gpa</option>
            <option value="year">year</option>
            <option value="gender">gender</option>
        </select>
        <br>
       <strong>Select field to sort applications:</strong>
        <select name="sortField">
            <option value="name">name</option>
            <option value="email">email</option>
            <option value="gpa">gpa</option>
            <option value="year">year</option>
            <option value="gender">gender</option>
        </select>
        <br>
        <strong>Filter Condition:</strong>
        <input type="text" name="condition">
        <br>
        <input type="submit" value="Display Applications" name="displayApplications">
        <button type="submit" onclick="location.href ='main.html';">Return to main menu</button>
    </form>
</body>
</html>