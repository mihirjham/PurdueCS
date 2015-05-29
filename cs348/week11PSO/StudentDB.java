import java.sql.*;

public class StudentDB
{
	Connection con;

	public StudentDB()
	{
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}
		catch(ClassNotFoundException e)
		{
			System.out.println("Error: " + e.getMessage());
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

	public void getStudentsFromDepartment(int department)
	{
		String studentQuery = "Select sname from Student where deptid = " + department;

		try
		{
			Statement studentStatement = con.createStatement();
			ResultSet studentSet = studentStatement.executeQuery(studentQuery);

			while(studentSet.next())
			{
				String sname = studentSet.getString("sname");
				System.out.println(sname);
			}

			studentSet.close();
			studentStatement.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}

	public void getStudentsInClass(String className)
	{
		String classQuery = "Select snum from Enrolled where cname = ?";
		try
		{
			PreparedStatement ps = con.prepareStatement(classQuery);
			ps.setString(1, className);

			ResultSet rs = ps.executeQuery();

			while(rs.next())
			{
				int snum = rs.getInt("snum");
				String studentQuery = "Select sname from Student where snum = ?";
				PreparedStatement preparedStudentStatement = con.prepareStatement(studentQuery);
				preparedStudentStatement.setInt(1, snum);

				ResultSet studentSet = preparedStudentStatement.executeQuery();

				while(studentSet.next())
				{
					String sname = studentSet.getString("sname");
					System.out.println(snum + "\t" + sname);
				}
				studentSet.close();
				preparedStudentStatement.close();
			}

			rs.close();
			ps.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}

	public void enroll(String className, int student)
	{
		String insert = "Insert into Enrolled values(?, ?, ?)";
		try
		{
			PreparedStatement ps = con.prepareStatement(insert);
			ps.setInt(1, student);
			ps.setString(2, className);
			ps.setString(3, null);

			ps.executeUpdate();
			ps.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}

	public static void main(String args[])
	{
		StudentDB student = new StudentDB();
		student.getStudentsFromDepartment(11);
		System.out.println();
		student.getStudentsInClass("ENG40000");
		student.enroll("ENG40000", 48291);
		System.out.println();
		student.getStudentsInClass("ENG40000");
	}
}