class password{  
  int n = -2;
  
  public String encrypt(String password){
    char[] pwd=password.toCharArray();
    char[] pwd2=new char[pwd.length];
    for(int i=0;i<pwd.length;i++)
    {
      pwd2[i]=(char)(pwd[i]+n);
      if(((int)pwd[i]<91&&(int)pwd2[i]>90)||((int)pwd[i]<123&&(int)pwd2[i]>122))
      {
        pwd2[i]=(char)(pwd2[i]-26);
      }        
    }
    String password2=new String(pwd2);
    return password2;
  }
  public String decrypt(String password){
    char[] pwd=password.toCharArray();
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
  public static void main(String [] args){
    password a=new password();
    String x="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    String y="NOPQRSTUVWXYZABCDEFGHIJKLMnopqrstuvwxyzabcdefghijklm";
//    System.out.println(a.encrypt(x));
//    System.out.println(a.decrypt(y));
    System.out.println(a.encrypt("aba"));
  }
}