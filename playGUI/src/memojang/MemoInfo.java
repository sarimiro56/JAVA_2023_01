package memojang;

import java.awt.Color;

import javax.swing.*;

public class MemoInfo extends JFrame {
	JTabbedPane t = new JTabbedPane(JTabbedPane.BOTTOM);
	
	public MemoInfo() {
		this.setTitle("메모장 정보");
		JPanel p1 = new JPanel(); p1.setBackground(Color.white);
		JPanel p2 = new JPanel(); p2.setBackground(Color.getHSBColor(50, 50, 20)); //명도, 채도, 밝기
		JPanel p3 = new JPanel(); p3.setBackground(Color.getHSBColor(50, 50, 50));
		t.add("도움말", p1);
		t.add("메뉴소개", p2);
		t.add("고객센터안내", p3);
		
		this.add(t);
		setSize(450, 350);
		setLocationRelativeTo(this);
		setVisible(true);
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new MemoInfo();
	}
}
