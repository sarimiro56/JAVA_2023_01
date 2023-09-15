import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {

	public static Connection makeCon() {
		// TODO Auto-generated method stub
		String url = "jdbc:mysql://localhost:3306/app?serverTimezone=Asia/Seoul";
		String user = "root";
		String pass = "1234";
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, user, pass);
			return con;
		}
		catch(Exception e) {
			e.printStackTrace();
			return con;
		}
//		finally {
//			try {
//				con.close();
//			}
//			catch(SQLException se) {
//				se.printStackTrace();
//			}
//		}
	}
}
