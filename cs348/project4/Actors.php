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

		
		echo '<h1>Search Results</h1>';
	
		try
		{
			$conn = new Mongo('localhost');
			$db = $conn->test;
			$collection = $db->items;
		
			$criteria = array('actorName' => $_POST['actorName']);
			$cursor = $collection->find($criteria);
		
			foreach($cursor as $obj)
			{
				echo '<h3>' . $obj['actorName'] . '</h3>';
				echo $obj['birthdate'] . '<br>';
			}		
			
			$criteria = array('castMember' => $_POST['actorName']);
			$cursor = $collection->find($criteria);
			
			echo '<ul>';
			foreach($cursor as $obj)
			{
				echo '<li>' . $obj['movie'] . '</li>';
			}
			echo '</ul>';
			
			$criteria = array('actor' => $_POST['actorName']);
			$cursor = $collection->find($criteria);
			
			foreach($cursor as $obj)
			{
				echo '<img src = \'/images/' . $obj['fileName'] . '\'>';
			}
			$conn->close();
		}
		catch(MongoConnectionException $e)
		{
			die('Error connecting to the MongoDB server.<br>Please check if you have started it.<br>');
		}
		catch(MongoException $e)
		{
			die('Error: ' . $e->getMessage());
		}	
?>

<html>
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

