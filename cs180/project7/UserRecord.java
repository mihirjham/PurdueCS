class UserRecord
{
  String domain, username, password;
  
  public UserRecord(String domain, String username, String password)
  {
    this.domain = domain;
    this.username = username;
    this.password = password;
  }
  
  public void setDomain(String newDomain)
  {
    this.domain = newDomain;
  }
  
  public void setUsername(String newUsername)
  {
    this.username = newUsername;
  }
  
  public void setPassword(String newPassword)
  {
    this.password = newPassword;
  }
  
  public String getDomain()
  {
    return this.domain;
  }
  
  public String getUsername()
  {
    return this.username;
  }
  
  public String getPassword()
  {
    return this.password;
  }
}