package memojang;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Find extends JDialog implements ActionListener{
   private JLabel lFind = new JLabel("찾을 문자열 : ");
   private JLabel lReplace = new JLabel("바꿀 문자열 : ");
   private JTextField tFind = new JTextField(20);
   private JTextField tReplace = new JTextField(20);
   private JButton bFind = new JButton("찾기");
   private JButton bReplace = new JButton("바꾸기");
   private JTextArea ta;

   public Find(JFrame parent, JTextArea ta) { //나를 소유하는 소유자의 상위 객체 - Frame, 필요 데이터
      super(parent, "찾기", false);
      this.ta = ta; //함께 가르쳐야 한다.
      setLayout(null);
      lFind.setBounds(10, 30, 80, 30);  lReplace.setBounds(10, 90, 80, 30);
      tFind.setBounds(90, 30, 150, 30);  tReplace.setBounds(90, 90, 150, 30);
      bFind.setBounds(250, 30, 80, 30);  bReplace.setBounds(250, 90, 80, 30);
      add(lFind); add(tFind); add(bFind); add(lReplace);
      add(tReplace); add(bReplace);
      setResizable(false); //사이즈 조절 불가
      bFind.addActionListener(this);
      bReplace.addActionListener(this);
      this.addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent e) {
            Find.this.dispose();
         }
      });
   }
   
   public void showFind() { //memoMain 프레임에서 눌렸을 때 설정한다.
      //setLocationRelativeTo : 인수의 값을 기준으로 상대적 위치 잡아라
      setTitle("찾기"); this.setLocationRelativeTo(this); setSize(400, 120); setVisible(true);
   }
   
   public void showReplace() {
      //setLocationRelativeTo : 인수의 값을 기준으로 상대적 위치 잡아라
      setTitle("찾아 바꾸기"); this.setLocationRelativeTo(this); setSize(400, 200); setVisible(true);
   }
   
   //버튼의 객체 이름으로 눌린 값 보기 (버튼 이름은 actionCommand)
   
   //getSelectionStart : 선택된 영역(현재 자신이 놓은 커서)의 시작 위치
   //getSelectionEnd : 선택된 영역(현재 자신이 놓은 커서)의 마지막 위치
   
   private void find() { //memoMain 프레임에서 눌렸을 때 설정한다.
      String text = ta.getText(); //ta의 내용을 가져온다.
      text = text.replaceAll("\\r", ""); //ta의 내용 중 "\\r"(줄바꿈 기호)을 공백으로 바꿔 저장한다. -> 쉼표 별로 읽은 파일을 나눠주는 것이 핵심이다!
      String str = tFind.getText(); //찾을 문자열을 입력한 값을 가져온다.
      int end = text.length(); //ta의 전체 길이
      int len = str.length(); //찾을 문자열의 길이
      int start = ta.getSelectionEnd(); //입력된 데이터의 마지막 인덱스를 얻는다 (커서가 있는 위치를 정수형으로)
      if (start == end) start = 0; //만약에 커서를 맨 마지막에 놓았다면 처음부터 찾도록 하여라
      //만약 커서 맨 마지막에 놓지 않았다면 찾는 위치는 현재 자신이 놓은 커서의 위치부터이다.
      for (; start <= end - len; start++) {
         // 찾아야 하는 문자는 5개인데 남은 문자의 길이가 5 이하이면 그만 찾으라는 반복문
         if (text.substring(start, start + len).equals(str)) {  //현재 커서 위치부터 찾아야 할 문자 개수 추출
            ta.setText(text); //메모 전체 내용 가져와
            ta.setSelectionStart(start); //커서의 앞 위치 선정
            ta.setSelectionEnd(start + len); //커서의 뒤 위치 선정해
            ta.requestFocus(); //포커스를 설정함으로써 선택된 영역이 나타난다
            return;
         }
      }
      //검색이 끝난 후
      ta.setSelectionStart(end); //현재 문서의 맨 끝에 커서를 놔준다
      ta.setSelectionEnd(end); //현재 문서의 맨 끝에 커서를 놔준다.
      // 다 끝나면 처음부터 다시 찾는다.
      ta.requestFocus();
   }
   
   private void replace() {
      String str = tReplace.getText(); //바꿀 문자열을 입력한 값을 가져온다.
      if (ta.getSelectedText().equals(tFind.getText())) //찾는 문자열의 내용과 ta에서 같다면 find 함수로 찾은 선택된 문자값이 같을 때
         ta.replaceRange(str, ta.getSelectionStart(), ta.getSelectionEnd()); //문자를 바꿔라
      else find(); //없다면 다시 찾아라
   }
      
   public void actionPerformed(ActionEvent e) {
      if (e.getSource() == bFind) //찾기 버튼이 눌리면
         find(); //find 함수 실행
      else if (e.getSource() == bReplace) //바꾸기 버튼이 눌리면
         replace(); //replace 함수 실행
   }
}