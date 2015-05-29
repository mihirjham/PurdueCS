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
		
			$criteria = array('title' => $_POST['movieTitle']);
			$cursor = $collection->find($criteria);
		
			foreach($cursor as $obj)
			{
				echo '<h3>' . $obj['title'] . ' (' . $obj['releaseYear'] . ')</h3>';
				echo '<h3>Genre: ' . $obj['genre'] . '</h3>';
				echo 'Plot: ' . $obj['plotSummary'] . '<br>';
			}		
			echo '<br>';	
		
			$criteria = array('movie' => $_POST['movieTitle']);
			$cursor = $collection->find($criteria);
		
			echo 'Cast Members:';
			echo '<ul>';
			foreach($cursor as $obj)
			{
				echo '<li>' . $obj['castMember'] . '</li>';
			}	
			echo '</ul>';
		
			$criteria = array('movieTitle' => $_POST['movieTitle']);
			$cursor = $collection->find($criteria);
			$rating = 0;
			$count = 0;	
			foreach($cursor as $obj)
			{
				$rating = $rating + $obj['rating'];
				$count = $count + 1;
			}
			echo 'Average Rating: ' . $rating/$count . '<br>';
			echo '<br>';
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

