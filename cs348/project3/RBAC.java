import java.sql.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class RBAC
{
	private Connection con = null;
	private String currentUser = null;

	public RBAC()
	{
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}

		try
		{
			con = DriverManager.getConnection("jdbc:oracle:thin:@claros.cs.purdue.edu:1524:strep", "mjham", "Od4aPrmq");
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}

	public void run()
	{
		String input = null;
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

		try
		{
			do
			{
				input = bufferedReader.readLine();
				String args[] = input.split(" ");

				if(args[0].equals("LOGIN"))
				{
					String username = args[1];

					String query = "Select password from Users where username = ?";
					try
					{
						PreparedStatement ps = con.prepareStatement(query);
						ps.setString(1, username);

						ResultSet rs = ps.executeQuery();
						
						if(!rs.isBeforeFirst())
						{
							System.out.println("Invalid Login");
						}

						while(rs.next())
						{
							String password = rs.getString("password");
							//if(username.equals("admin"))
							//	args[2] = args[2] + "\n";
							if(!password.equals(args[2]))
								System.out.println("Invalid Login");
							else
							{
								currentUser = username;
								System.out.println("Login successful");
							}
						}
						
						rs.close();
						ps.close();
					}
					catch(SQLException e)
					{
						e.printStackTrace();
					}
				}
				else if(args[0].equals("CREATE"))
				{
					if(currentUser == null)
					{
						System.out.println("Authorization Failure");

					}
					else if(!currentUser.equals("admin"))
					{
						System.out.println("Authorization Failure");
					}
					else
					{
						if(args[1].equals("ROLE"))
						{
							String update = "Insert into Roles values(?,?)";

							try
							{
								PreparedStatement ps = con.prepareStatement(update);
								ps.setString(1, args[2]);
								ps.setString(2, args[3]);
								ps.executeUpdate();
								ps.close();
							}
							catch(SQLException e)
							{
								e.printStackTrace();
							}
						}
						else if(args[1].equals("USER"))
						{
							String update = "Insert into Users values(?,?)";

							try
							{
								PreparedStatement ps = con.prepareStatement(update);
								ps.setString(1, args[2]);
								ps.setString(2, args[3]);
								ps.executeUpdate();
								ps.close();
								System.out.println("User created successfully");
							}
							catch(SQLException e)
							{
								e.printStackTrace();
							}
						}
					}
				}
				else if(args[0].equals("GRANT"))
				{
					if(currentUser == null)
					{
						System.out.println("Authorization Failure");
					}
					else if(!currentUser.equals("admin"))
					{
						System.out.println("Authorization Failure");
					}
					else
					{
						if(args[1].equals("ROLE"))
						{
							String update = "Insert into UserRoles values(?,?)";

							try
							{
								PreparedStatement ps = con.prepareStatement(update);
								ps.setString(1, args[2]);
								ps.setString(2, args[3]);
								ps.executeUpdate();
								ps.close();
								System.out.println("Role assigned successfully");
							}
							catch(SQLException e)
							{
								e.printStackTrace();
							}
						}
						else if(args[1].equals("PRIVILEGE"))
						{
							String update = "Insert into RolePrivileges values(?,?,?)";
							try
							{
								PreparedStatement ps = con.prepareStatement(update);
								ps.setString(1, args[4]);
								ps.setString(2, args[6]);
								ps.setString(3, args[2]);
								ps.executeUpdate();
								ps.close();
							}
							catch(SQLException e)
							{
								e.printStackTrace();
							}
						}
					}
				}
				else if(args[0].equals("REVOKE"))
				{
					if(currentUser == null)
					{
						System.out.println("Authorization Failure");
					}
					else if(!currentUser.equals("admin"))
					{
						System.out.println("Authorization Failure");
					}
					else
					{
						String update = "Delete from RolePrivileges where roleName = ? and tableName = ? and privName = ?";

						try
						{
							PreparedStatement ps = con.prepareStatement(update);
							ps.setString(1, args[4]);
							ps.setString(2, args[6]);
							ps.setString(3, args[2]);
							ps.executeUpdate();
							ps.close();
						}
						catch(SQLException e)
						{
							e.printStackTrace();
						}
					}
				}
				else if(args[0].equals("INSERT"))
				{
					String checkPrivilege = "Select rp.privName From UserRoles ur, RolePrivileges rp Where ur.username = ? and ur.roleName = rp.roleName and rp.tableName = ? and rp.privName = ?";

					try
					{
						PreparedStatement ps = con.prepareStatement(checkPrivilege);
						ps.setString(1, currentUser);
						ps.setString(2, args[2]);
						ps.setString(3, args[0]);
						ResultSet rs = ps.executeQuery();

						if(!rs.isBeforeFirst())
						{
							System.out.println("Authorization Failure");
						}
						else
						{
							String values[] = (args[3].substring(7,args[3].length()-1)).split(",");
							int encryptedColumn = Integer.parseInt(args[5]);

							String params = "(";
							for(int i = 0; i < values.length+2; i++)
							{
								if(i == values.length-1+2)
									params = params + "?";
								else
									params = params + "?, ";
							}
							params = params + ")";
							
							String update = "Insert into " + args[2] + " values" + params;
							PreparedStatement updateStatement = con.prepareStatement(update);
							if(encryptedColumn <= 0)
							{
								for(int i = 0; i < values.length; i++)
								{
									updateStatement.setString(i+1, values[i]);
								}
								updateStatement.setString(values.length+1, args[5]);
								updateStatement.setString(values.length+2, args[6]);
							}
							else
							{
								for(int i = 0; i < values.length; i++)
								{
									if(encryptedColumn == (i+1))
									{
										updateStatement.setString(i+1, encrypt(values[i], args[6]));
									}
									else
									{
										updateStatement.setString(i+1, values[i]);
									}
								}
								updateStatement.setString(values.length+1, args[5]);
								updateStatement.setString(values.length+2, args[6]);
							}

							updateStatement.executeUpdate();
							updateStatement.close();			
						}

						rs.close();
						ps.close();
					}
					catch(SQLException e)
					{
						e.printStackTrace();
					}
				}
				else if(args[0].equals("SELECT"))
				{
					String checkPrivilege = "Select rp.privName From UserRoles ur, RolePrivileges rp Where ur.username = ? and ur.roleName = rp.roleName and rp.tableName = ? and rp.privName = ?";

					try
					{
						PreparedStatement ps = con.prepareStatement(checkPrivilege);
						ps.setString(1, currentUser);
						ps.setString(2, args[3]);
						ps.setString(3, args[0]);

						ResultSet rs = ps.executeQuery();

						if(!rs.isBeforeFirst())
						{
							System.out.println("Authorization Failure");
						}
						else
						{
							String query = "Select * from " + args[3];

							PreparedStatement queryStatement = con.prepareStatement(query);
							ResultSet querySet = queryStatement.executeQuery();
							ResultSetMetaData metaData = querySet.getMetaData();

							for(int i = 0; i < metaData.getColumnCount()-2; i++)
							{
								System.out.print(metaData.getColumnName(i+1));
								if(i == metaData.getColumnCount()-1-2)
									System.out.println();
								else
									System.out.print(", ");
							}

							while(querySet.next())
							{
								String record[] = new String[metaData.getColumnCount()];
								for(int i = 0; i < metaData.getColumnCount(); i++)
								{
									record[i] = querySet.getString(metaData.getColumnName(i+1));
								}

								String checkUser = "Select * from UserRoles where username = ? and roleName = ?";
								PreparedStatement userStatement = con.prepareStatement(checkUser);
								userStatement.setString(1, currentUser);
								userStatement.setString(2, record[metaData.getColumnCount()-1]);
								ResultSet userSet = userStatement.executeQuery();

								if(!userSet.isBeforeFirst())
								{
									for(int i = 0; i < record.length-2; i++)
									{
										System.out.print(record[i]);
										if(i == record.length-2-1)
											System.out.println();
										else
											System.out.print(", ");
									}
								}
								else
								{
									for(int i = 0; i < record.length-2; i++)
									{
										if(Integer.parseInt(record[record.length-2]) <= 0)
										{
											System.out.print(record[i]);
										}
										else if(Integer.parseInt(record[record.length-2]) == (i+1))
										{
											System.out.print(decrypt(record[i], record[record.length-1]));
										}
										else
										{
											System.out.print(record[i]);
										}

										if(i == record.length-2-1)
											System.out.println();
										else
											System.out.print(", ");
									}
								}
								userSet.close();
								userStatement.close();
							}
							querySet.close();
							queryStatement.close();
						}
						rs.close();
						ps.close();
					}
					catch(SQLException e)
					{
						e.printStackTrace();
					}
				}
			}while(!input.equals("QUIT"));
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	public String encrypt(String value, String role)
	{
		String query = "Select encryptionKey from Roles where roleName = ?";
		String encryptedValue = null;
		try
		{
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, role);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
			{
				int key = rs.getInt("encryptionKey");
				char chars[] = value.toCharArray();

				for(int i = 0; i < chars.length; i++)
				{
					chars[i] = (char)(chars[i] + key);
				}
				
				encryptedValue = new String(chars);
			}
			rs.close();
			ps.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		return encryptedValue;
	}

	public String decrypt(String value, String role)
	{
		String query = "Select encryptionKey from Roles where roleName = ?";
		String encryptedValue = null;
		try
		{
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, role);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
			{
				int key = rs.getInt("encryptionKey");
				char chars[] = value.toCharArray();

				for(int i = 0; i < chars.length; i++)
				{
					chars[i] = (char)(chars[i] - key);
				}
				
				encryptedValue = new String(chars);
			}
			rs.close();
			ps.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		return encryptedValue;
	}

	public static void main(String args[])
	{
		RBAC rbac = new RBAC();
		rbac.run();
	}
}
