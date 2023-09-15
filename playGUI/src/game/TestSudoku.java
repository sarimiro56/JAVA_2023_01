package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import java.net.URI;


public class TestSudoku extends JFrame {
    private static final int BOARD_SIZE = 9;
    private static final int SUBGRID_SIZE = 3;

    private JTextField[][] cellFields;
    private JButton checkButton;
    private List<int[][]> puzzles; // 스도쿠 문제 리스트
    private int currentPuzzleIndex; // 현재 보여지는 스도쿠 문제의 인덱스
	private int[][] solution;

    public TestSudoku() {
        setTitle("Sudoku Game");
        setSize(400, 400);
        setLayout(new BorderLayout());
        
     // 메뉴바 생성
        JMenuBar menuBar = new JMenuBar();

        // "File" 메뉴 생성
        JMenu fileMenu = new JMenu("Game");

        // "Tutorial" 메뉴 아이템 생성
        JMenuItem tutorialMenuItem = new JMenuItem("Tutorial");
        tutorialMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // 브라우저에서 해당 링크 열기
                    Desktop.getDesktop().browse(new URI("https://m.blog.naver.com/chmpro/150172408586"));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        fileMenu.add(tutorialMenuItem);

        // "Info" 메뉴 아이템 생성
        JMenuItem infoMenuItem = new JMenuItem("Info");
        // Info 메뉴 클릭 시 동작 구현
        infoMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
				JLabel label = new JLabel("<html>이 스도쿠는 JAVA로 만들어져 있습니다.<br>만든이 : 202116029 태은선</html>", JLabel.CENTER);
				JOptionPane.showMessageDialog(null, label, "정보", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        fileMenu.add(infoMenuItem);

        // 메뉴바에 File 메뉴 추가
        menuBar.add(fileMenu);

        // 프레임에 메뉴바 설정
        setJMenuBar(menuBar);


        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(BOARD_SIZE, BOARD_SIZE));
        add(boardPanel, BorderLayout.CENTER);

        cellFields = new JTextField[BOARD_SIZE][BOARD_SIZE];
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                JTextField textField = new JTextField();
                textField.setHorizontalAlignment(JTextField.CENTER);
                textField.setFont(new Font("Arial", Font.BOLD, 20));
                boardPanel.add(textField);
                cellFields[row][col] = textField;
            }
        }

        checkButton = new JButton("Check");
        checkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkSolution();
            }
        });
        add(checkButton, BorderLayout.SOUTH);

        puzzles = generateSudokuPuzzles(2); // 2개의 스도쿠 문제 생성
        currentPuzzleIndex = 0;
        displayCurrentPuzzle(); // 현재 스도쿠 문제 출력
        
        //JFrame 아이콘 변경
        ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("sudoku.png")));
		setIconImage(icon.getImage());
    }

    private List<int[][]> generateSudokuPuzzles(int count) {
        List<int[][]> puzzles = new ArrayList<>();

        int[][] solvedPuzzle1 = new int[][]{
                {2, 9, 5, 7, 4, 3, 8, 6, 1},
                {4, 3, 1, 8, 6, 5, 9, 2, 7},
                {8, 7, 6, 1, 9, 2, 5, 4, 3},
                {3, 8, 7, 4, 5, 9, 2, 1, 6},
                {6, 1, 2, 3, 8, 7, 4, 9, 5},
                {5, 4, 9, 2, 1, 6, 7, 3, 8},
                {7, 6, 3, 5, 2, 4, 1, 8, 9},
                {9, 2, 8, 6, 7, 1, 3, 5, 4},
                {1, 5, 4, 9, 3, 8, 6, 7, 2}
        };

        int[][] solvedPuzzle2 = new int[][]{
                {6, 1, 3, 8, 2, 7, 5, 9, 4},
                {2, 5, 7, 6, 4, 9, 8, 1, 3},
                {9, 8, 4, 5, 3, 1, 6, 2, 7},
                {3, 7, 8, 9, 6, 2, 4, 5, 1},
                {4, 2, 9, 1, 8, 5, 7, 3, 6},
                {5, 6, 1, 3, 7, 4, 9, 8, 2},
                {8, 3, 2, 4, 9, 6, 1, 7, 5},
                {7, 4, 5, 2, 1, 8, 3, 6, 9},
                {1, 9, 6, 7, 5, 3, 2, 4, 8}
        };

        puzzles.add(solvedPuzzle1);
        puzzles.add(solvedPuzzle2);

        return puzzles;
    }

    private void displayCurrentPuzzle() {
        int[][] solvedPuzzle = puzzles.get(currentPuzzleIndex);
        int[][] puzzle = deepCopy(solvedPuzzle);

        Random random = new Random();
        int emptyCells = 50; // 빈 칸 수
        while (emptyCells > 0) {
            int row = random.nextInt(BOARD_SIZE);
            int col = random.nextInt(BOARD_SIZE);
            if (puzzle[row][col] != 0) {
                puzzle[row][col] = 0;
                emptyCells--;
            }
        }

        // 문제 출력
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                int value = puzzle[row][col];
                JTextField textField = cellFields[row][col];
                textField.setText(value == 0 ? "" : String.valueOf(value));
                textField.setEditable(value == 0);
            }
        }

        // 해답 저장
        this.solution = solvedPuzzle;
    }

    private int[][] deepCopy(int[][] original) {
        int[][] copy = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            copy[i] = Arrays.copyOf(original[i], original[i].length);
        }
        return copy;
    }


    private boolean solveSudoku(int[][] puzzle, int[][] solution) {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (puzzle[row][col] == 0) {
                    for (int num = 1; num <= BOARD_SIZE; num++) {
                        if (isValueValid(puzzle, row, col, num)) {
                            puzzle[row][col] = num;
                            if (solveSudoku(puzzle, solution)) {
                                solution[row][col] = num;
                                return true;
                            } else {
                                puzzle[row][col] = 0;
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }



    private boolean isValueValid(int[][] puzzle, int row, int col, int value) {
        // 행 검사
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (puzzle[row][i] == value) {
                return false;
            }
        }

        // 열 검사
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (puzzle[i][col] == value) {
                return false;
            }
        }

        // 서브그리드(3x3) 검사
        // 현재 위치(row, col)를 기준으로 속한 서브그리드의 시작 위치(subgridRowStart, subgridColStart)를 계산
        // 중복된 숫자가 있으면 false 반환하여 중복된 문자가 있으면 안된다고 판단.
        int subgridRowStart = (row / SUBGRID_SIZE) * SUBGRID_SIZE;
        int subgridColStart = (col / SUBGRID_SIZE) * SUBGRID_SIZE;
        for (int i = subgridRowStart; i < subgridRowStart + SUBGRID_SIZE; i++) {
            for (int j = subgridColStart; j < subgridColStart + SUBGRID_SIZE; j++) {
                if (puzzle[i][j] == value) {
                    return false;
                }
            }
        }

        return true;
    }


    private boolean isSolutionCorrect(int[][] userSolution, int[][] solution) {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (userSolution[row][col] != solution[row][col]) {
                    return false;
                }
            }
        }
        return true;
    }

    private void checkSolution() {
        int[][] userSolution = new int[BOARD_SIZE][BOARD_SIZE];
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                String value = cellFields[row][col].getText();
                if (!value.isEmpty()) {
                    userSolution[row][col] = Integer.parseInt(value);
                }
            }
        }

        if (isSolutionCorrect(userSolution, solution)) {
            JOptionPane.showMessageDialog(this, "축하드립니다! 정답입니다!");

            if (currentPuzzleIndex < puzzles.size() - 1) {
                currentPuzzleIndex++;
                displayCurrentPuzzle();
            } else {
                JOptionPane.showMessageDialog(this, "당신은 제공된 스도쿠 퍼즐을 전부 다 푸셨습니다!");
            }
        } else {
            JOptionPane.showMessageDialog(this, "이런, 어딘가 틀렸는지 다시 확인해보세요.");
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                TestSudoku sudokuGame = new TestSudoku();
                sudokuGame.setVisible(true);
            }
        });
    }
}
