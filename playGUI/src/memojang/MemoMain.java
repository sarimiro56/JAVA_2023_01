package memojang;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.*;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import calendar.MemoCalendar;
import game.TestSudoku;

public class MemoMain extends JFrame implements ActionListener{
	JTextArea ta;
	JFileChooser jfc;
	boolean isNew = false;//저장 될 내용 있는지 확인
	File file=null;
	String filename=null;
    int cnt;
    FontStyleView fontStyleView;
    StatusBar statusBar;
    JButton newBtn, openBtn, saveBtn, exitBtn, copyBtn, pasteBtn, cutBtn, fontBtn, colBtn;
    
	public MemoMain() {
		setTitle("태은선의 메모장");
		MainView();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ta = new JTextArea();
		//ta.setBackground(Color.);
		JScrollPane jsp = new JScrollPane(ta);
		jfc = new JFileChooser();
		ta.setText("");
		this.add(jsp);
		
		statusBar = new StatusBar(this, this.ta);
		this.add(BorderLayout.SOUTH, statusBar);
		setBounds(800, 200, 500, 700);
		setVisible(true);
	}
	
	public void MainView() {
		KeyStroke key;
		JMenuBar mb = new JMenuBar();
		JMenu m1, m2, m3, m4, m5, m6;
		JMenuItem item;
		JToolBar tb = new JToolBar();
		
		///////////////ToolBar 메뉴///////////////////////////////
		
		ImageIcon newF = new ImageIcon("image/new.png");
		ImageIcon openF = new ImageIcon("image/open.png");
		ImageIcon saveF = new ImageIcon("image/save.png");
		ImageIcon extF = new ImageIcon("image/exit.png");
		ImageIcon copyF = new ImageIcon("image/copy.png");
		ImageIcon pasteF = new ImageIcon("image/paste.png");
		ImageIcon cutF = new ImageIcon("image/cut.png");
		ImageIcon fontF = new ImageIcon("image/font.png");
		ImageIcon colF = new ImageIcon("image/color.png");
		
		newBtn = new JButton(newF);
		openBtn = new JButton(openF);
		saveBtn = new JButton(saveF);
		exitBtn = new JButton(extF);
		copyBtn = new JButton(copyF);
		pasteBtn = new JButton(pasteF);
		cutBtn = new JButton(cutF);
		fontBtn = new JButton(fontF);
		colBtn = new JButton(colF);
		
		newBtn.addActionListener(this);
		openBtn.addActionListener(this);
		saveBtn.addActionListener(this);
		exitBtn.addActionListener(this);
		copyBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ta.copy();
			}
			
		});
		pasteBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ta.paste();
			}
			
		});
		cutBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ta.cut();
			}
			
		});
		
		fontBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fontStyleView = new FontStyleView(ta);
				fontStyleView.setBounds(200, 200, 400, 350);
				fontStyleView.setVisible(true);
			}
			
		});
		colBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Color setColor = JColorChooser.showDialog(getParent(), "색상표", Color.blue);
				if(setColor != null)
					ta.setForeground(setColor);
			}
			
		});
		
		newBtn.setToolTipText("새 파일을 작성합니다.");
		openBtn.setToolTipText("파일을 오픈합니다.");
		saveBtn.setToolTipText("저장합니다.");
		exitBtn.setToolTipText("나갑니다.");
		copyBtn.setToolTipText("복사합니다.");
		pasteBtn.setToolTipText("붙여 넣습니다.");
		cutBtn.setToolTipText("자릅니다.");
		fontBtn.setToolTipText("글꼴을 바꿉니다.");
		colBtn.setToolTipText("글자 색상을 바꿉니다.");
		
		tb.add(newBtn); tb.add(openBtn); tb.add(saveBtn); tb.add(exitBtn); tb.addSeparator();
		tb.add(copyBtn); tb.add(pasteBtn); tb.add(cutBtn); tb.addSeparator();
		tb.add(fontBtn); tb.add(colBtn);
		
		///////////////////메뉴 구성 시작///////////////////////
		//파일 메뉴 구성
		m1 = new JMenu("파일(F)"); m1.setMnemonic(KeyEvent.VK_F); //가상키값관리
		
		item = new JMenuItem("새로 만들기");
		key = KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK);
		item.setAccelerator(key);
		item.addActionListener(this);
		m1.add(item);
		
		item = new JMenuItem("열기");
		key = KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK);
		item.setAccelerator(key);
		item.addActionListener(this);
		m1.add(item);
		
		m1.addSeparator();
		
		item = new JMenuItem("저장");
		key = KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK);
		item.setAccelerator(key);
		item.addActionListener(this);
		m1.add(item);
		
		item = new JMenuItem("다른 이름으로 저장");
		key = KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK + ActionEvent.SHIFT_MASK);
		item.setAccelerator(key);
		item.addActionListener(this);
		m1.add(item);
		
		m1.addSeparator();
		
		item = new JMenuItem("끝내기");
		key = KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK);
		item.setAccelerator(key);
		item.addActionListener(this);
		m1.add(item);
		
		//////////////////////////////////////////////////////////////////
		//편집 메뉴
		m2 = new JMenu("편집(E)"); m2.setMnemonic(KeyEvent.VK_E);
		
		item = new JMenuItem("실행 취소");
		key = KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK);
		item.setAccelerator(key);
		item.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		m2.add(item);
		m2.addSeparator();
		
		item = new JMenuItem("잘라내기");
		key = KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK);
		item.setAccelerator(key);
		item.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ta.cut();
			}
			
		});
		m2.add(item);
		
		item = new JMenuItem("복사");
		key = KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK);
		item.setAccelerator(key);
		item.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ta.copy();
			}
			
		});
		m2.add(item);
		
		item = new JMenuItem("붙여넣기");
		key = KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK);
		item.setAccelerator(key);
		item.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ta.paste();
			}
			
		});
		m2.add(item);
		
		item = new JMenuItem("삭제");
		key = KeyStroke.getKeyStroke((char) KeyEvent.VK_DELETE);
		item.setAccelerator(key);
		item.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ta.cut();
			}
			
		});
		m2.add(item);
		m2.addSeparator();
		
		item = new JMenuItem("찾기");
		key = KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.CTRL_MASK);
		item.setAccelerator(key);
		item.addActionListener(this);
		m2.add(item);
		
		item = new JMenuItem("찾아 바꾸기");
	    key = KeyStroke.getKeyStroke((char) KeyEvent.VK_H, ActionEvent.CTRL_MASK);
	    item.setAccelerator(key);
	    item.addActionListener(this);
	    m2.add(item);
		
		item = new JMenuItem("이동(G)");
		key = KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.CTRL_MASK);
		item.setAccelerator(key);
		item.addActionListener(this);
		m2.add(item);
		m2.addSeparator();
		
		item = new JMenuItem("모두 선택(A)");
		key = KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK);
		item.setAccelerator(key);
		item.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ta.selectAll();
			}
			
		});
		m2.add(item);
		
		item = new JMenuItem("시간/날짜(D)");
		key = KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.CTRL_MASK);
		item.setAccelerator(key);
		item.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Date d = new Date();
				SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd aa HH:mm:ss");
				ta.append(sd.format(d));
				
			}
			
		});
		m2.add(item);
		
		////////////////////////////////////////////////////////////////////////////
		// 서식 메뉴
		m3 = new JMenu("서식(O)"); m3.setMnemonic(KeyEvent.VK_O);
		
		item = new JMenuItem("배경색");
	    key = KeyStroke.getKeyStroke((char) KeyEvent.VK_B);
	    item.setAccelerator(key);
	    item.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	           //색깔 선택 대화 상자 부모는 프레임
	           Color setColor = JColorChooser.showDialog(getParent(), "색상표", Color.yellow);
	           if (setColor != null) //만약 색상을 선택했다면
	              ta.setBackground(setColor); //선택한 색으로 바꿔준다
	        } 
	    });
	    m3.add(item);
		
	    item = new JMenuItem("글자색");
	    key = KeyStroke.getKeyStroke((char) KeyEvent.VK_E);
	    item.setAccelerator(key);
	    item.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	           Color setColor = JColorChooser.showDialog(getParent(), "색상표", Color.green);
	           if (setColor != null) //만약 색상을 선택했다면
	              ta.setForeground(setColor); //선택한 색으로 바꿔준다
	        }
	    });
	    m3.add(item);
	    
		item = new JMenuItem("글꼴(F)");
		key = KeyStroke.getKeyStroke((char) KeyEvent.VK_F);
		item.setAccelerator(key);
		item.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		       fontStyleView = new FontStyleView(ta);
		       fontStyleView.setBounds(200, 200, 400, 350);
		       fontStyleView.setVisible(true);
		    }    
		});
		m3.add(item);
		
		////////////////////////////////////////////////////////////////////
		// 보기 메뉴
		m4 = new JMenu("보기(V)"); m4.setMnemonic(KeyEvent.VK_V);
		
		JMenu submenu = new JMenu("확대하기/축소하기");
		m4.add(submenu);
		submenu.add(new JMenuItem("확대(I)")).addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Font ft = ta.getFont();
				String font_name = ft.getName();
				int font_style = ft.getStyle();
				int font_size = ft.getSize()+2;
				//System.out.println(font_size);
				
				ft = new Font(font_name, font_style, font_size); //name, style, size
				ta.setFont(ft);
			}
			
		});
		submenu.add(new JMenuItem("축소(O)")).addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Font ft = ta.getFont();
				String font_name = ft.getName();
				int font_style = ft.getStyle();
				int font_size = ft.getSize()-2;
				//System.out.println(font_size);
				
				ft = new Font(font_name, font_style, font_size); //name, style, size
				ta.setFont(ft);
			}
			
		});
	    
		submenu.add(new JMenuItem("확대하기/축소하기 기본값 복원")).addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Font ft = ta.getFont();
				String font_name = ft.getName();
				int font_style = ft.getStyle();
				int font_size = ft.getSize();
				//System.out.println(font_size);
				
				ft = new Font(font_name, font_style, 12); //name, style, size
				ta.setFont(ft);
			}
			
		});
		
	    m4.addSeparator();
	    JCheckBoxMenuItem cb = new JCheckBoxMenuItem("상태 표시줄");
	    cb.setState(true);
	    cb.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				if(cb.getState()==true)
					statusBar.setVisible(true);
				else
					statusBar.setVisible(false);
			}

	    	
	    });
	    m4.add(cb);
		
		///////////////////////////////////////////////////////////////////////
		// 도움말 메뉴 (피드백 제외)
		m5 = new JMenu("도움말(H)"); m5.setMnemonic(KeyEvent.VK_H);
		
		item = new JMenuItem("도움말 보기(H)");
		key = KeyStroke.getKeyStroke((char) KeyEvent.VK_H);
		item.setAccelerator(key);
		item.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ImageIcon icon = new ImageIcon("C:\\Users\\태은선\\Pictures\\rabbit.png");
				JLabel label = new JLabel("<html>이 메모장은 JAVA로 만들어져 있습니다.<br>버전 : 1.0.0<br>만든이 : 202116029 태은선<br>만든 날짜: 2023-04-09</html>", icon, JLabel.CENTER);
				JOptionPane.showMessageDialog(null, label, "제목", JOptionPane.INFORMATION_MESSAGE);
			}
			
		});
		m5.add(item);
		m5.addSeparator();
		
		item = new JMenuItem("메모장 정보(A)");
		key = KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.SHIFT_MASK);
		item.setAccelerator(key);
		item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new MemoInfo();
			}	
		});
		m5.add(item);
		
        ///////////////////메뉴부분 끝///////////////////////
		m6 = new JMenu("잡동사니");
		item = new JMenuItem("폰북");
		item.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new HelloSwing();
			}
		});
		m6.add(item);
		
		item = new JMenuItem("계산기");
		item.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				// 새로 추가할 곳(직접)
				new Calculator();
			}
		});
		m6.add(item);
		
		item = new JMenuItem("스케쥴러");
		item.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new MemoCalendar();
			}
		});
		m6.add(item);
		
		item = new JMenuItem("스도쿠 게임");
		item.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        TestSudoku sudokuGame = new TestSudoku();
		        sudokuGame.setVisible(true);
		    }
		});
		m6.add(item);
		
		mb.add(m1);mb.add(m2);mb.add(m3);mb.add(m4);mb.add(m5);mb.add(m6);
		this.setJMenuBar(mb);
		this.add(tb, BorderLayout.NORTH);
	}
	int check() {
		int a = 0;
		String data = "";
		int ch;
		try {
			if(isNew==true) { //기존파일이 존재하는 상태에서 ta 영역의 내용이 변화가 있는지 체크
				FileReader fr = new FileReader(file);
				while((ch=fr.read()) != -1)
					data = data + (char)ch;
				fr.close();
				if(!ta.getText().equals(data))
					a = 1;
			}
			else if(isNew == false && !ta.getText().equals(""))
				//기존파일이 존재 하지는 않지만 ta 영역에 저장할 내용
				a = 1;
		} catch(Exception e) {
			e.getMessage();
		}
		return a;
	}
	
	void open() {
	    int re = jfc.showOpenDialog(this);
	    if(re == jfc.APPROVE_OPTION) {
	        file = jfc.getSelectedFile();
	        this.setTitle(file.getAbsolutePath()+"-태은선의 메모장");
	        String data = "";
	        int ch;
	        try {
	            FileReader fr = new FileReader(file);
	            while((ch= fr.read()) != -1)
	                data = data + (char)ch;
	            
	            ta.setText(data);
	            isNew = true;
	            fr.close();
	        } catch (Exception e) {
	            e.getMessage();
	        }
	    }
	}

	void save() {
		if (isNew == false) {
            int re = jfc.showSaveDialog(this);
            if (re == 0) { 
                file = jfc.getSelectedFile();
                try {
                   FileWriter fw = new FileWriter(file);
                   String data = ta.getText();
                   fw.write(data);
                   fw.close();
                   JOptionPane.showMessageDialog(this, "파일을 저장하였습니다.");
                   isNew = true;
                   this.setTitle(file.getAbsolutePath()+"-태은선의 메모장");
                }
                catch (IOException e) {
                   e.getMessage();
                }
             }
         }
         else { 
            file = jfc.getSelectedFile();
            try {
                 FileWriter fw = new FileWriter(file);
                 String data = ta.getText(); 
                 fw.write(data);
                 fw.close();
                 JOptionPane.showMessageDialog(this, "파일을 저장하였습니다.");
              }
              catch (IOException e) {
                 e.getMessage();
              }
         }
	}

	void saveAs() {
		isNew = false;
	    save();
	}

	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new MemoMain();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String cmd = e.getActionCommand();
		
		if(cmd.equals("새로 만들기") || e.getSource() == newBtn) {
			int a = check();
			if(a==1) {
				int b = JOptionPane.showConfirmDialog(this, "작성된 내용을 저장하시겠습니까?", "저장여부확인", 1);
				if(b==0) save();
				else if(b==1) {
					ta.setText("");
					isNew = false;
					file = null;
				}
			}
		}
		
		else if(cmd.equals("열기") || e.getSource() == openBtn) {
			int a = check();
			System.out.println(a);
			if(a==1) {
				int b = JOptionPane.showConfirmDialog(this, "작성된 내용을 저장하시겠습니까?", "저장여부확인", 1);
				if(b==0) save();
				else if(b==1) open();
			}
			else open();
		}
		
		else if(cmd.equals("저장") || e.getSource() == saveBtn) {
			save( );
		}
		
		else if(cmd.equals("다른 이름으로 저장") ) {
			saveAs( );
		}
		
		else if(cmd.equals("끝내기") || e.getSource() == exitBtn) {
			System.exit(0);
		}
		
		else if(cmd.equals("찾기")) {
			Find fi = new Find(this, ta);
			fi.showFind();
		}
		
		else if(cmd.equals("찾아 바꾸기")) {
			Find fi = new Find(this, ta);
			fi.showReplace();
		}
	        else if(cmd.equals("이동")){
	            ta.setCaretPosition(0);
	            //미완성
	        }
	}
}

//Joptionpane.show... 메모장 정보에 자신의 메모장 소개를 할것, 사진도 첨부, 버전(1.0.0), 만든이, 만든 날짜 등...