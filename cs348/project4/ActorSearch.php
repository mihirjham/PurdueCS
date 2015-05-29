<?PHP
	session_start();
	$loggedIn = $_SESSION["loggedIn"];
	
	if($loggedIn == null)
                echo '<a href="/LoginRegister.html">Login/Register</a><br>';
        else
        {
                echo 'Hi ' . $_SESSION["firstName"] . ' ' . $_SESSION["lastName"] . '<br>';
                echo '<a href="/Logout.php">Logout</a><br>';
        }


?>
<html>
        <form action="Actors.php" method="POST">
                Search for Actor<br>
                <input type="text" name="actorName">
                <br>
                <br><br>
                <input type="submit" value="Search">
        </form>
	
	<h2>Navigate</h2>
		<a href = '/MovieDB.php'>Main</a><br>
		<a href = '/MovieSearch.php'>Movies</a><br>
		<a href = '/ActorSearch.php'>Actors</a><br>
</html>

<?PHP
        $loggedIn = $_SESSION["loggedIn"];

        if($loggedIn == null)
                echo '<a href="/LoginRegister.html">Login/Register</a><br>';
        else
                echo '<a href="/Logout.php">Logout</a><br>';
?>

