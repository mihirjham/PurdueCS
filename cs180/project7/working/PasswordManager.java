import java.util.*;
import java.io.*;
public class PasswordManager
{ 
  
  public static void main(String args[])
  {
    String siteName,userName,password;
    password encryption=new password();
    Scanner scanner = new Scanner(System.in);
    
    PasswordManagerModel pwd = new PasswordManagerModel("input.txt");
    System.out.println(pwd.n);
    for(int i=0; i<pwd.numRecords; i++)
    {
      System.out.println(pwd.records[i].getDomain());
      System.out.println(pwd.records[i].getUsername());
      System.out.println(pwd.records[i].getPassword());
    }
    
    do
    {
      pwd.input = pwd.menu();
      
      switch(pwd.input)
      {
        case 1: 
          
          System.out.print("Enter the site name: ");
          siteName=scanner.next();    
          System.out.print("Enter the username: ");
          userName=scanner.next();
          System.out.print("Enter the password: ");
          password=scanner.next();
          String passwordEncrypted=encryption.encrypt(password);
          
          if(pwd.searchDomain(siteName)==null)
          {
            
            pwd.records[pwd.numRecords] = new UserRecord(siteName,userName, passwordEncrypted);
            pwd.numRecords++;
          }
          else
          {
            pwd.searchDomain(siteName).setUsername(userName);
            pwd.searchDomain(siteName).setPassword(passwordEncrypted);             
          }
          
          break;
        case 2: 
          System.out.print("Enter the site name: ");
          siteName=scanner.next();    
          if(pwd.searchDomain(siteName)==null)
          {
            System.out.print("Invalid site name");
            break;
          }
          System.out.print("Enter the username: ");
          userName=scanner.next();
          System.out.print("Enter the password: ");
          password=scanner.next();
          if(password==encryption.decrypt(pwd.searchDomain(siteName).getPassword()))
          { 
            pwd.searchDomain(siteName).setDomain(null); 
            pwd.searchDomain(siteName).setUsername(null); 
            pwd.searchDomain(siteName).setPassword(null); 
          }
          else
            System.out.print("Authentication failed");
          break;
        case 3:
          System.out.print("Enter the site name: ");
          siteName=scanner.next();    
          if(pwd.searchDomain(siteName)==null)
          {
            System.out.print("Invalid site name");
            break;
          }
          System.out.print("Enter the username: ");
          userName=scanner.next();
          System.out.print("Enter the password: ");
          password=scanner.next();
          if(password==encryption.decrypt(pwd.searchDomain(siteName).getPassword()))
          {
            System.out.print("Enter the new password: ");
            String newPassword=scanner.next();
            pwd.searchDomain(siteName).setPassword(newPassword);            
          }
          else 
            System.out.print("Authentication failed");
          break;
        case 4:
          System.out.print("Enter the site name: ");
          siteName=scanner.next();    
          if(pwd.searchDomain(siteName)==null)
          {
            System.out.print("Invalid site name");
            break;
          }
          String getPassword=encryption.decrypt(pwd.searchDomain(siteName).getPassword());
          System.out.print(getPassword);          
          break;
        case 5: 
          try{
          File  inFile=new File("secure.txt");
          FileOutputStream outStream=new FileOutputStream(inFile);
          PrintWriter pw = new PrintWriter(outStream);
          String content="";
          for(int i=0; i<pwd.numRecords; i++)
          {
            content=content+"\n"+pwd.records[i].getDomain()+"\t"+pwd.records[i].getUsername()+"\t"+pwd.records[i].getPassword();
          }
          pw.println(content);
          pw.close();
          outStream.close();
        }catch   (Exception a){}
        
        
        System.exit(0);
        break;
        default: System.out.println("Please enter a valid choice from the menu!\n");
      }
    }while(pwd.input != 5);
  }
}





