package memojang;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;

public class StatusBar extends JPanel{
	JPanel leftBar, midBar, rightBar;
	JLabel left, mid, right;
	JTextArea ta;
	
	public StatusBar(JFrame f, JTextArea ta) {
		this.ta = ta;
		left = new JLabel();
		mid = new JLabel();
		right = new JLabel();
		
		
		leftBar = new JPanel();
		leftBar.setBackground(new Color(230, 230, 230));
		midBar = new JPanel();
		midBar.setBackground(new Color(240, 240, 240));
		rightBar = new JPanel();
		rightBar.setBackground(new Color(245, 245, 245));
		
		leftBar.add(left); midBar.add(mid); rightBar.add(right);
		
		this.setLayout(new GridLayout(1,3));
		this.add(leftBar);
		this.add(midBar);
		this.add(rightBar);
		ta.addCaretListener(new CaretListener() {

			@Override
			public void caretUpdate(CaretEvent e) {
				// TODO Auto-generated method stub
				// 커서의 위치값 가져와서 mid 레이블에 표시	
				int x=0, y=0;
				
				try {
//					y = ta.getCaretPosition(); // 행에 관계없이 계속 누적시킨 열 값
//					x = ta.getLineOfOffset(y); // 행 번호
					int cor = ta.getCaretPosition(); //커서의 열 값 y
					x = ta.getLineOfOffset(cor); //커서 열 값 위치에 해당되는 행(x)값
					System.out.print(x + " " + cor + ",");
					int startcor = ta.getLineStartOffset(x);
					System.out.print(startcor + ", ");
					y = cor - startcor;
					System.out.print(x + " " + y);
					
				} catch (BadLocationException ex) {
					ex.printStackTrace();
				} 
				
				mid.setText("행 : " + x + " 열 : " + y);
			}
			
		});
		
		Timer timer = new Timer(3000, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				repaint();
			}
			
		});
		timer.start();
	}
	
	protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    // 날짜를 생성해서 left 레일블에 표시
	    Date d = new Date();
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd aa HH:mm:ss");				
		left.setText(sd.format(d));
				
		
		// 글짜크기 비율값 계산해서 right 레이블에 표시
		Font ft = ta.getFont();
		double rate = ft.getSize()/12.0 * 100;
		right.setText(String.format("%.0f", rate)+"%");
	}
	
}
