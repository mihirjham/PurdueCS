import java.util.*;
import java.io.*;

class PasswordManagerModel
{  
  int n, input, numRecords;
  String siteName, userName, password;
  UserRecord records[] = new UserRecord[100];
  
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
    int input=0;
    
    System.out.println("1. Add Login Information");
    System.out.println("2. Delete Login Information");
    System.out.println("3. Change Password");
    System.out.println("4. Retrieve Password");
    System.out.println("5. Exit");
    
    try
    {
      input = Integer.parseInt(scanner.next());
    }
    catch(NumberFormatException e)
    {
      System.out.println("Numbers only!!");
      scanner.next();
    }
      return input;
  }
  
  public UserRecord searchDomain(String sitename)
  {
    for(int i=0; i<numRecords; i++)
    {
      if(records[i] == null)
      {
        continue;
      }
      
      if(sitename.equals(records[i].getDomain()))
      {
        return records[i];
      }
    }
    return null; 
  }
  
  public String encryptText(String text, int n)
  {
    char[] textChar = text.toCharArray();
    
    char[] textChar2=new char[textChar.length];
    
    n = n % 26;
    if(n < 0)
    {
      n = n + 26;
    }
    for(int i=0;i<textChar.length;i++)
    {
      textChar2[i]=(char)(textChar[i] + n);
      
      if(((int)textChar[i]<91&&(int)textChar2[i]>90)||((int)textChar[i]<123&&(int)textChar2[i]>122))
      {
        textChar2[i]=(char)(textChar2[i]-26);
      }        
    }
    String password2 = new String(textChar2);
    
    return password2;
  }
  
  public String decryptText(String text, int n)
  {
    char[] pwd=text.toCharArray();
    
    char[] pwd2=new char[pwd.length];
    
    n = n % 26;
    if(n < 0)
    {
      n = n + 26;
    }
    
    for(int i=0;i<pwd.length;i++)
    {
      pwd2[i]=(char)(pwd[i]-n);
      if(((int)pwd[i]>64&&(int)pwd2[i]<65)||((int)pwd[i]>96&&(int)pwd2[i]<97))
      {
        pwd2[i]=(char)(pwd2[i]+26);
      }    
    }
    String password2=new String(pwd2);
    return password2;
  }
  
  public void addLogin()
  {
    Scanner scanner = new Scanner(System.in);
    Console console = System.console();
    
    System.out.print("Enter the site name: ");
    siteName=scanner.next();    
    System.out.print("Enter the username: ");
    userName=scanner.next();
    char passwordArray[] = console.readPassword("Enter the password: ");
    password= new String(passwordArray);
    
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
    Console console = System.console();
    
    System.out.print("Enter the site name: ");
    siteName=scanner.next();    
    if(searchDomain(siteName)==null)
    {
      System.out.print("Invalid site name\n");
      return;
    }
    
    System.out.print("Enter the username: ");
    userName=scanner.next();
    char passwordArray[] = console.readPassword("Enter the password: ");
    password= new String(passwordArray);
    
    for(int i =0; i<numRecords; i++)
    {
      if((siteName.equals(records[i].getDomain())))
      {
        if((userName.equals(records[i].getUsername())))
        {
          if((password.equals(decryptText(records[i].getPassword(), n))))
          {
            System.out.println(records[i].getDomain() + " entry deleted");
            records[i] = null;
            break;
          }
          else
          {
            System.out.println("Invalid password!");
            break;
          }
        }
        else
        {
          System.out.println("Invalid usernamwe!");
          break;
        }
      }
    }
}
  
  public void changePassword()
  {
    Scanner scanner = new Scanner(System.in);
    Console console = System.console();
    
    System.out.print("Enter the site name: ");
    siteName=scanner.next();    
    if(searchDomain(siteName)==null)
    {
      System.out.print("Invalid site name\n");
      return;
    }
    
    char passwordArray[] = console.readPassword("Enter the password for "+siteName+": ");
    password= new String(passwordArray);

    for(int i =0; i<numRecords; i++)
    {
      if(records[i] == null)
      {
        continue;
      }
      if((siteName.equals(records[i].getDomain())))
      {
        if((password.equals(decryptText(records[i].getPassword(), n))))
        {
          char newPasswordArray[] = console.readPassword("Enter the new password: ");
          String newPassword = new String(newPasswordArray);
          records[i].setPassword(encryptText(newPassword, n));
          System.out.println(records[i].getDomain()+" password changed");
          break;
        }
        else
        {
          System.out.println("Invalid password!");
          break;
        } 
      }
    }
  }
  
  public String retrievePassword()
  {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter the site name: ");
    siteName=scanner.next();    
    if(searchDomain(siteName)==null)
    {
      return null;
    }
    String getPassword = decryptText(searchDomain(siteName).getPassword(), n);
    return getPassword;
  }
  
  public void saveFile(String filename)
  {
    FileOutputStream outStream = null;
    File inFile;
    PrintWriter pw;
    
    try
    {
      inFile=new File(filename);
      outStream=new FileOutputStream(inFile);
    }
    catch(FileNotFoundException evt)
    {
      System.out.println("No such file exists!");
    }
    
    try
    {
      pw = new PrintWriter(outStream);
      System.out.println("Writing to the file...");
      String content="";
      for(int i=0; i<numRecords; i++)
      {
        if(records[i] == null)
        {
        }
        else 
          content=content+"\n"+records[i].getDomain()+"\t"+encryptText(records[i].getUsername(), n)+"\t"+records[i].getPassword();
      }
      pw.println(content);
      pw.close();
      outStream.close();
    }catch(IOException a)
    {
      System.out.println("Writing error!");
    }
  }
}
    
