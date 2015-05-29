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
	<h1>Welcome to the CS34800 movie database
	<?PHP 
		$loggedIn = $_SESSION["loggedIn"];
		if($loggedIn != null)
		{
			echo ', ' . $_SESSION["firstName"] . ' ' . $_SESSION["lastName"] ;
		}
	?>
	!</h1>
	<br>
	<a href="/MovieSearch.php">Movies</a><br>
	<a href="/ActorSearch.php">Actors</a><br>
</html>

<?PHP
	$loggedIn = $_SESSION["loggedIn"];
	
	if($loggedIn == null)
		echo '<a href="/LoginRegister.html">Login/Register</a><br>';
	else
		echo '<a href="/Logout.php">Logout</a><br>';
?>
