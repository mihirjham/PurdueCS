<?PHP
	session_start();
        
	try
        {
                $conn = new Mongo('localhost');
                $db = $conn->test;
                $collection = $db->items;
		
		$criteria = array('username' => $_POST["username"], 'password' => $_POST["password"]);
		$cursor = $collection->find($criteria);
		
		$_SESSION["loggedIn"] = null;
		foreach($cursor as $obj)
		{
			$_SESSION["loggedIn"] = $_POST["username"];
			$_SESSION["firstName"] = $obj["firstName"];
			$_SESSION["lastName"] = $obj["lastName"]; 
		}
	
		$conn->close();
        }
        catch(MongoConnectionException $e)
        {
                die('Error connecting to the MongoDB server<br/>Please check if you have started it.');
        }
        catch(MongoException $e)
        {
                die('Error: ' . $e->getMessage());
        }
	
	if($_SESSION["loggedIn"] != null)
		header('Location: /MovieDB.php');
	else
		header('Location: /ErrorLogin.html');
?>

