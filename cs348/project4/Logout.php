<?PHP
	session_start();
	
	$_SESSION["loggedIn"] = null;
	$_SESSION["firstName"] = null;
	$_SESSION["lastName"] = null;
	
	header('Location: /MovieDB.php');
?>
