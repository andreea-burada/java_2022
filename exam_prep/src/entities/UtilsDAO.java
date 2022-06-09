package entities;
import java.sql.*;

public class UtilsDAO {
	// attributes
	protected static Connection c;
	
	// setting the SQL connection
	public static void setConnection()
	{
		try {
			// loading the driver class
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:test.db");
			c.setAutoCommit(false);
		}
		catch (ClassNotFoundException cnfex)
		{
			cnfex.printStackTrace();
		}
		catch (SQLException sqlex)
		{
			sqlex.printStackTrace();
		}
	}
	
	// selecting data
	public static String selectData() throws SQLException
	{
		java.sql.Statement query = c.createStatement();
		String allData = "";
	    ResultSet queryResult = query.executeQuery("select count(*) from PHONES");
	    int noRows = queryResult.getInt(1);	// columns start from 1
		queryResult = query.executeQuery("select * from PHONES");
		String val;
		for (int i = 0; i < noRows; i++)
		{	
			queryResult.next(); 
			for (int j = 1; j <= 4; j++)
			{
				val = queryResult.getString(j);
				allData += val + (j == 4 ? "" : ":");
			}
			allData += "\r\n";	
		}
		return allData;
	}
	
	// closing the SQL connection
	public static void closeConnection()
	{
		try {
			c.close();
		} catch (SQLException sqlex) {
			sqlex.printStackTrace();
		}
	}
}
