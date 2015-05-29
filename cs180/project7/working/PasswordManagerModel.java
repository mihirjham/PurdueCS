import java.util.*;
import java.io.*;

class PasswordManagerModel
{  
  int n, input, numRecords;
  UserRecord records[] = new UserRecord[100];
  String siteName,userName,password;
  
  public PasswordManagerModel(String fileName)
  {
    FileInputStream in = null;
    File newFile;
    Scanner scanner;
    
    try
    {
      newFile = new File(fileName);
      in = new FileInputStream(newFile);
    }
    catch(FileNotFoundException evt)
    {
      System.out.println("File not Found!");
    }
    
    try
    {
      scanner = new Scanner(in);
      this.n = Integer.parseInt(scanner.next());
      numRecords = 0;
      while(scanner.hasNext() == true)
      {
        String domain = scanner.next();
        String username = scanner.next();
        String password = scanner.next();
        this.records[numRecords] = new UserRecord(domain, username, password);
        numRecords++;
      }
      in.close();
    }
    catch(IOException event)
    {
     System.out.println("Reading from file error!");
    }
  }
  public int menu()
  {
    Scanner scanner = new Scanner(System.in);
    
    System.out.println("1. Add Login Information");
    System.out.println("2. Delete Login Information");
    System.out.println("3. Change Password");
    System.out.println("4. Retrieve Password");
    System.out.println("5. Exit");
    
    int input = Integer.parseInt(scanner.next());
    
    return input;
  }
    
  
  public UserRecord searchDomain(String sitename)
  {
    for(int i=0; i<numRecords; i++)
    {
      if(sitename.equals(records[i].getDomain()))
      {
        return records[i];
      }
    }
    return null; 
  }
  
  public void addLogin()
  {
    Scanner scanner = new Scanner(System.in);
    
    System.out.print("Enter the site name: ");
    siteName=scanner.next();    
    System.out.print("Enter the username: ");
    userName=scanner.next();
    System.out.print("Enter the password: ");
    password=scanner.next();
    
    String passwordEncrypted=encryptText(password, n);
    
    if(searchDomain(siteName)==null)
    {
      
      records[numRecords] = new UserRecord(siteName,userName, passwordEncrypted);
      numRecords++;
    }
    else
    {
      searchDomain(siteName).setUsername(userName);
      searchDomain(siteName).setPassword(passwordEncrypted);             
    }
  }
  
  public void deleteLogin()
  {
    Scanner scanner = new Scanner(System.in);
    
    System.out.print("Enter the site name: ");
    siteName=scanner.next();    
    if(searchDomain(siteName)==null)
    {
      System.out.print("Invalid site name");
      return;
    }
    
    System.out.print("Enter the username: ");
    userName=scanner.next();
    System.out.print("Enter the password: ");
    password=scanner.next();
    
    if(password==decryptText(searchDomain(siteName).getPassword()))
    {
      searchDomain(siteName).setDomain(null); 
      searchDomain(siteName).setUsername(null); 
      searchDomain(siteName).setPassword(null); 
    }
    else
      System.out.print("Authentication failed");
  }
  
  public void changePassword()
  {
    Scanner scanner = new Scanner(System.in);
    
    System.out.print("Enter the site name: ");
    siteName=scanner.next();    
    if(searchDomain(siteName)==null)
    {
      System.out.print("Invalid site name");
      return;
    }
    System.out.print("Enter the username: ");
    userName=scanner.next();
    System.out.print("Enter the password: ");
    password=scanner.next();
    if(password==decryptText(searchDomain(siteName).getPassword()))
    {
      System.out.print("Enter the new password: ");
      String newPassword=scanner.next();
      searchDomain(siteName).setPassword(encryptText(newPassword));            
    }
    else 
      System.out.print("Authentication failed");
  }
  
  public String retrievePassword()
  {
    System.out.print("Enter the site name: ");
    siteName=scanner.next();    
    if(searchDomain(siteName)==null)
    {
      return null;
    }
    String getPassword = decryptText(searchDomain(siteName).getPassword());
    return getPassword;
  }
  
  public void saveFile(String filename)
  {
    try
    {
      File  inFile=new File(filename);
      FileOutputStream outStream=new FileOutputStream(inFile);
      PrintWriter pw = new PrintWriter(outStream);
      
      String content="";
      for(int i=0; i<numRecords; i++)
      {
        if((records[i].getDomain() == null) && (records[i].getUsername()==null) && (records[i].getPassword()==null))
        {
          continue;
        }
        else 
          content=content+"\n"+records[i].getDomain()+"\t"+records[i].getUsername()+"\t"+records[i].getPassword();
      }
      pw.println(content);
      pw.close();
      outStream.close();
    }catch   (Exception a){}
  }
}