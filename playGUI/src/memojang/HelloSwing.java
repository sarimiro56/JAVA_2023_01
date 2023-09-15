package memojang;
//import java.awt.Color;
//import java.awt.Container;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.*;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class HelloSwing extends JFrame implements ActionListener{  //BorderLayout배치방식 --> 동 서 남 북 센터 방향으로 배치
	// ActionEvent[클래스] (이벤트 발생 시킴) -> ActionListener[인터페이스] (감시하다가 이벤트 발생에 대해 일 처리, 즉 연결시킴) -> actionPerformed()[추상 메서드] (이벤트가 벌어졌을 때 어떤 일을 해야하는가 정해야함.)
	//  ㄴ1. 버튼이 눌리는 동작 2. 텍스트 창에서 글 작성하고 enter 치는 순간 3. 메뉴가 선택되는 것
	JTextField tf1, in_tf1, in_tf2, in_tf3, in_tf4;
	JTable table;
	DefaultTableModel dtm;
	Connection con = null;
	PreparedStatement ps = null;  //static 함수 내에서 사용할 수 있게함.
	String name;
	
	public HelloSwing() {
		this.setTitle("나는 프레임으로 탄생한 HelloSwing 객체");
//		this.setSize(1000,500);
//		this.setLocation(100, 200);
//		Container c = this.getContentPane();
//		c.setBackground(Color.orange);
		this.setBounds(100, 100, 800, 800);
		this.setLayout(null);  //FlowLayout은 LinearLayout과 같은 형태
		
		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel();
		JPanel insert = new JPanel();
		insert.setSize(100, 100);
		insert.setBackground(Color.cyan);
		
		
		Object[] title = {"이름", "전화번호", "이메일", "나이" };
		Object[][] value = new Object[0][4]; //2차원 배열
		dtm = new DefaultTableModel(value, title);
		table = new JTable(dtm);
		JScrollPane p3 = new JScrollPane(table);
		
		this.add(p1);
		p1.setBackground(Color.blue);
		p2.setBackground(Color.pink);
		
		JButton b1 = new JButton("전체조회");
		JButton b2 = new JButton("추가");
		JButton b3 = new JButton("검색");
		JButton b4 = new JButton("삭제");
		JButton b5 = new JButton("수정");
		
		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
		b4.addActionListener(this);
		b5.addActionListener(this);
		
		
		p1.add(b1); p1.add(b2); p1.add(b3); p1.add(b4); p1.add(b5);  //버튼 추가
		tf1 = new JTextField(20);
		p2.add(tf1); //텍스트상자 추가
//		this.add(b1, BorderLayout.NORTH); this.add(b2, BorderLayout.EAST); 
//		this.add(b3, BorderLayout.WEST); this.add(b4, BorderLayout.SOUTH); 
//		this.add(b5);
		
		JLabel in_la1 = new JLabel("이름: ");
		JLabel in_la2 = new JLabel("전화번호: ");
		JLabel in_la3 = new JLabel("이메일: ");
		JLabel in_la4 = new JLabel("나이: ");
		
		
		in_tf1 = new JTextField(20); //name
		in_tf2 = new JTextField(20); //phonenumber
		in_tf3 = new JTextField(20); //email
		in_tf4 = new JTextField(20); //age
		
		insert.add(in_la1); insert.add(in_tf1);
		insert.add(in_la2); insert.add(in_tf2);
		insert.add(in_la3); insert.add(in_tf3);
		insert.add(in_la4); insert.add(in_tf4);
		
		p1.setBounds(10, 10, 400, 40);
		p2.setBounds(10, 60, 400, 40);
		insert.setBounds(10, 110, 580, 60);
		p3.setBounds(10, 180, 760, 560);
		this.add(p1); //버튼 디스플레이 패널
		this.add(p2);
		this.add(insert); //정보를 추가하기 위한 패널
		this.add(p3);  //스크롤패널 추가
		this.setVisible(true);
		
		table.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent e) {
		        int row = table.getSelectedRow();
		        TableModel data = table.getModel();

		        // TODO Auto-generated method stub
		        name = (String) data.getValueAt(row, 0);
		        String phone = (String) data.getValueAt(row, 1);
		        String email = (String) data.getValueAt(row, 2);
		        String age = (String) data.getValueAt(row, 3);

		        in_tf1.setText(name);
		        in_tf2.setText(phone);
		        in_tf3.setText(email);
		        in_tf4.setText(age);
		    }
		});

	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new HelloSwing();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String cmd = e.getActionCommand();
		
		if(cmd.equals("전체조회")) {
			 display(1);
		}
		else if(cmd.equals("추가")) {
			if(in_tf1.getText() != null && in_tf2.getText() != null) {
				insert();
			    select();
			    display(1);
			}
		}
		else if(cmd.equals("검색")) {
			display(2);
		}
		else if(cmd.equals("삭제")) {
			delete();
		}
		else if(cmd.equals("수정")) {
			update();
			//테이블에서 한 사람 클릭해서 insert 칸에 수정할 데이터 값을 넣고, 수정을 누르면 수정됨. insert 칸에 데이터 떠야하고, 나이랑 이메일만 수정 가능. 그리고 수정 후에는 select() 실행.
		}
	}
	
	public void display(int key) {  //key가 1일땐 select, 2는 search
		dtm.setRowCount(0);
//		dtm.setNumRows(0);
		con = makeCon(); //DB 사용 가능해짐.
		ResultSet rs = null;
		if(key==1)
			rs = select(); //select() 호출
		else
			rs = search();
		try {
			String info[] = new String[4];
			while(rs.next()) {
				 info[0] = rs.getString(1);
				 info[1] = rs.getString(2);
				 info[2] = rs.getString(3);
				 info[3] = rs.getString(4);
				 dtm.addRow(info);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
    public void update() {
        try {
            String sql = "update person set email = ?, age = ? where name = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, in_tf3.getText());
            ps.setString(2, in_tf4.getText());
            ps.setString(3, name);
            int result = ps.executeUpdate();
            if (result == 1) {
                display(1); // 테이블 다시 출력
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void delete() {
        int row = table.getSelectedRow();
        
        String name = (String) table.getValueAt(row, 0);
        String sql = "delete from person where name=?";
        try {
        	ps = con.prepareStatement(sql);
            ps.setString(1, name);
            int result = ps.executeUpdate();
            if (result == 1) {
                display(1); // 테이블 다시 출력
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


	
	public ResultSet search() {
		ResultSet rs = null;
		String sql = "select * from person where name = ?";
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, tf1.getText());
			rs = ps.executeQuery();
			return rs;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return rs;
		}
	}
	
	public void insert() {
		con = makeCon(); 
		ResultSet rs = null;
		
		try {
			String sql = "select * from person where phone=?";
			ps = con.prepareStatement(sql);
			ps.setString(1, in_tf2.getText());
			rs = ps.executeQuery();
			if(!rs.next()) {
				if(!in_tf4.getText().equals("")) //equals는 비어있는지 값이 서로 같은가 비교
					sql = "insert into person values(?, ?, ?, ?)";
				else
					sql = "insert into person(name, phone, email) values(?, ?, ?)";
				ps = con.prepareStatement(sql);
				ps.setString(1, in_tf1.getText());
				ps.setString(2, in_tf2.getText());
				ps.setString(3, in_tf3.getText());
				if(!in_tf4.getText().equals(""))
					ps.setInt(4, Integer.parseInt(in_tf4.getText()));
				int a = ps.executeUpdate();
				if(a==1) System.out.println("삽입성공");
			}
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ResultSet select() {
		ResultSet rs = null;
		String sql = "select * from person";
		try {
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			return rs;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return rs;
		}
	}
	
	public Connection makeCon() {
		// TODO Auto-generated method stub
		String url = "jdbc:mysql://localhost:3306/app?serverTimezone=Asia/Seoul";
		String user = "root";
		String pass = "1234";
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, user, pass);
			System.out.println("연결성공");
			return con;
		}
		catch(Exception e) {
			e.printStackTrace();
			return con;
		}
	}


} //클래스의 끝