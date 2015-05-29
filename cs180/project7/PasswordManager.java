public class PasswordManager
{ 
  public static void main(String args[])
  {
    PasswordManagerModel pwd = new PasswordManagerModel("input.txt");
    do
    {
      pwd.input = pwd.menu();
      
      switch(pwd.input)
      {
        case 1: 
          pwd.addLogin(); 
          break;
        case 2: 
          pwd.deleteLogin(); 
          break;
        case 3: 
          pwd.changePassword(); 
          break;
        case 4: 
          String password = pwd.retrievePassword(); 
          if(password == null) 
            System.out.println("Invalid sitename!"); 
          else 
            System.out.println("Password: "+password); 
          break;
        case 5: 
          pwd.saveFile("secure.txt"); 
          System.out.println("Exiting the application.");
          System.exit(0);
          break;
        default: System.out.println("Please enter a valid choice from the menu!\n");
      }
    }while(pwd.input != 5);
  }        

}

    