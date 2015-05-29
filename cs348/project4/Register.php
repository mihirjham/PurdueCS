<?PHP
	session_start();
	
	try
	{
		$conn = new Mongo('localhost');
		$db = $conn->test;
		$collection = $db->items;
		
		$item = array('username' => $_POST["username"], 'password' => $_POST['password'], 'firstName' => $_POST['firstname'], 'lastName' => $_POST['lastname']);
		$collection->insert($item);
		
		$_SESSION["loggedIn"] = $_POST['username'];
		$_SESSION["firstName"] = $_POST['firstname'];
		$_SESSION["lastName"] = $_POST['lastname'];
		
		$conn->close();
	}
	catch(MongoConnectionException $e)
	{
		die('Error connecting to the MongoDB server');
	}
	catch(MongoException $e)
	{
		die('Error: ' . $e->getMessage());
	}	
	
	header('Location: /MovieDB.php');
?>
