import java.sql.*;
import java.util.Scanner;

public class PhoneManager {
	static Connection con = DBConnect.makeCon();
	static PreparedStatement ps = null;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if(con != null) {
//			insert();
//			delete();
			select();
			update();
			select();
		}
			
		
	}
	
	public static void update() {
		Scanner s = new Scanner(System.in);
		String key = s.nextLine();
		
		ResultSet rs = null;
		String sql = "select * from person where phone = ?";
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, key);
			rs = ps.executeQuery();
			
			if(rs.next()) {
				String e = s.nextLine();
				int a = s.nextInt();
				sql = "update person set email = ?, age = ? where phone = ?";
				ps = con.prepareStatement(sql);
				ps.setString(1, e);
				ps.setInt(2, a);
				ps.setString(3, key);
				ps.executeUpdate();
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void delete() {
	    ResultSet rs = null;
	    Scanner s = new Scanner(System.in);
	    String sql = "select * from person where phone=?";
	    String phone = s.nextLine();
	    try {
	        ps = con.prepareStatement(sql);
	        ps.setString(1, phone);
	        rs = ps.executeQuery();
	        if (rs.next()) {
	            String Sql = "delete from person where phone=?";
	            ps = con.prepareStatement(Sql);
	            ps.setString(1, phone);
	            int result = ps.executeUpdate();
	            if (result == 1) {
	                System.out.println("데이터가 삭제되었습니다.");
	            } else {
	                System.out.println("데이터 삭제 실패하였습니다.");
	            }
	        } else {
	            System.out.print("일치하는 번호가 없어 삭제가 불가능합니다." + "\n");
	        }
	    } catch (SQLException e){
	        e.printStackTrace();
	    } 
	}
	
	public static void select() {
		ResultSet rs = null;
		String sql = "select * from person";
		try {
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			System.out.println("------------------ 연락처 --------------------");
			System.out.println("이름" + "\t" + "번호" + "\t" + "이메일" + "\t" + "나이");
			while(rs.next()) {
				System.out.println(rs.getString(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getInt(4));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void insert() {
		Scanner s = new Scanner(System.in);
		String sql = "insert into person values(?, ?, ?, ?)";
		String name = s.nextLine();
		String phone = s.nextLine();
		String email = s.nextLine();
		int age = s.nextInt();
		
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, name);
			ps.setString(2, phone);
			ps.setString(3, email);
			ps.setInt(4, age);
			int a = ps.executeUpdate();
			System.out.println(a);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
