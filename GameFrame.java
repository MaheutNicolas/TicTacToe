package ticktacktoe;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GameFrame implements ActionListener {
	//IA check for the case non played 
	int[] checkcaseM = { 0, 1, 2, 3, 4, 5, 6, 7, 8 };
	int[] checkcase;
	//arrays with the score, [0] = V, [1] = D, [2] = E
	int[] score;
	JFrame screen;
	JComboBox<String> box;
	JButton[] button = new JButton[9];
	JTextField turnP;
	ImageIcon o;
	ImageIcon x;
	//if player 1 Win, iaTurn() is cut
	boolean player1Win;
	//check if the game is in Solo or Duel Mode
	boolean p1or2;
	//keep in memories the turn 
	boolean player1Turn;
	//prevent the bug of ActionListener when Selection of the item in the comboBox
	boolean firstSelected;
	//check number of case played by line, IA use this for the next move
	int l1 = 0;
	int l2 = 0;
	int l3 = 0;
	int c1 = 0;
	int c2 = 0;
	int c3 = 0;
	int d1 = 0;
	int d2 = 0;
	//case selected in the comboBox ( Display the actual Mode )
	int selected;

	GameFrame(boolean p1or2, int selected) {
		this.selected = selected;
		this.p1or2 = p1or2;
		score = load();
		player1Turn = true;
		player1Win = false;
		firstSelected = false;
		game();
	}

	// charge the game screen with every call
	public void game() {
		
		checkcase = new int[9];
		for (int i = 0; i < 9; i++) {
			checkcase[i] = checkcaseM[i];
		}

		String mScoreBoard = "Victoire = " + score[0] + " / Défaite = " + score[1] + " / Egalité = " + score[2];

		ImageIcon grille = new ImageIcon("grille.png");
		o = new ImageIcon("o.png");
		x = new ImageIcon("x.png");
		ImageIcon icon = new ImageIcon("tictactoe.png");
		ImageIcon thunder = new ImageIcon("thunder.png");
		ImageIcon thunder1 = new ImageIcon("thunderc.png");
		
		String[] refComboBox = {"Solo", "Duel" };
		
		box = new JComboBox<>(refComboBox);
		box.addActionListener(this);
		box.setSelectedIndex(selected);
		firstSelected = true;
		
		JPanel boxPanel = new JPanel();
		boxPanel.add(box);
		boxPanel.setBackground(Color.LIGHT_GRAY);
		
		
		JLabel backGround = new JLabel();
		backGround.setIcon(grille);

		JLabel scoreBoard = new JLabel(mScoreBoard);
		scoreBoard.setFont(new Font("Arial", Font.PLAIN, 20));
		
		turnP = new JTextField("Joueur 1");
		turnP.setFont(new Font("Arial", Font.PLAIN, 20));
		turnP.setBackground(Color.LIGHT_GRAY);
		turnP.setEditable(false);
		turnP.setFocusable(false);

		JLabel title1 = new JLabel("Tic Tac Toe");
		title1.setIcon(thunder1);
		title1.setIconTextGap(5);
		title1.setFont(new Font("Arial", Font.PLAIN, 20));
	

		JLabel title2 = new JLabel();
		title2.setIcon(thunder);
		
		JPanel titleP = new JPanel();
		titleP.setBackground(Color.LIGHT_GRAY);
		titleP.add(title1);
		titleP.add(title2);
		
		JPanel rightP = new JPanel();
		rightP.setBackground(Color.LIGHT_GRAY);
		rightP.setBounds(0,0,70,40);
	
	
		JPanel bottom = new JPanel();
		bottom.setPreferredSize(new Dimension(0, 40));
		bottom.setOpaque(true);
		bottom.setBackground(Color.LIGHT_GRAY);
		
		if( p1or2 == true) {
			bottom.add(scoreBoard);
			bottom.remove(turnP);
		}
		else {
			bottom.add(turnP);
			bottom.remove(scoreBoard);
		}

		JPanel top = new JPanel();

		top.setOpaque(true);
		top.setBackground(Color.LIGHT_GRAY);
		top.setPreferredSize(new Dimension(0, 40));
		top.setLayout(new BorderLayout());
		top.add(rightP,BorderLayout.EAST);
		top.add(titleP, BorderLayout.CENTER);
		top.add(boxPanel, BorderLayout.EAST);
	

		for (int i = 0; i < 9; i++) {
			button[i] = new JButton();
			backGround.add(button[i]);
			button[i].setFocusable(false);
			button[i].setOpaque(false);
			button[i].setContentAreaFilled(false);
			button[i].setBorderPainted(false);
			button[i].addActionListener(this);
		}
		button[0].setBounds(40, 40, 80, 80);
		button[1].setBounds(135, 40, 80, 80);
		button[2].setBounds(230, 40, 80, 80);
		button[3].setBounds(40, 137, 80, 80);
		button[4].setBounds(135, 137, 80, 80);
		button[5].setBounds(230, 137, 80, 80);
		button[6].setBounds(40, 230, 80, 80);
		button[7].setBounds(135, 230, 80, 80);
		button[8].setBounds(230, 230, 80, 80);

		JPanel center = new JPanel();
		center.add(backGround);

		screen = new JFrame("TicTacToe");
		screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		screen.setSize(450, 500);
		screen.setLayout(new BorderLayout(10, 10));
		screen.setIconImage(icon.getImage());
		screen.add(bottom, BorderLayout.PAGE_END);
		screen.add(top, BorderLayout.PAGE_START);
		screen.add(center, BorderLayout.CENTER);
		screen.setLocation(500, 200);
		screen.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		 if(e.getSource()== box) {
			 if(box.getSelectedIndex() == 0 && firstSelected == true) {
				 screen.dispose();
				 new GameFrame(true, 0);
			 }
			 if(box.getSelectedIndex() == 1 && firstSelected == true) {
				 screen.dispose();
				 new GameFrame(false, 1);
			 }
		 }
		 //first player move 
		for (int i = 0; i < 9; i++) {
			if (e.getSource() == button[i]) {
				if (player1Turn == true) {
					if (button[i].getText() == "") {
						button[i].setIcon(x);
						button[i].setText("X");
						player1Turn = false;
						if (i == 0) {
							l1++;
							c1++;
							d1++;
						} else if (i == 1) {
							l1++;
							c2++;
						} else if (i == 2) {
							l1++;
							c3++;
							d2++;
						} else if (i == 3) {
							l2++;
							c1++;
						} else if (i == 4) {
							l2++;
							c2++;
							d1++;
							d2++;
						} else if (i == 5) {
							l2++;
							c3++;
						} else if (i == 6) {
							l3++;
							c1++;
							d2++;
						} else if (i == 7) {
							l3++;
							c2++;
						} else if (i == 8) {
							l3++;
							c3++;
							d1++;
						}
						check();
						turnP.setText("Joueur 2");
						if(p1or2 == true) {
							iaTurn(i);
						}
						
					}
				}
				//second player move 
				else {
					if (button[i].getText() == "") {
						button[i].setIcon(o);
						button[i].setText("O");
						player1Turn = true;
						turnP.setText("Joueur 1");
						check();
					}
				}
			}
		}
	}

	public void iaTurn(int i) {

		
		// if P1 win, it's quit immediately
		if (player1Win == true) {
			return;
		}
		// check if the player can win in the next turn (for the difficulty)
		if (l1 == 2 && player1Turn == false) {
			if (button[1].getText() == "") {
				button[1].setIcon(o);
				button[1].setText("O");
				check();
				player1Turn = true;
			} else if (button[2].getText() == "") {
				button[2].setIcon(o);
				button[2].setText("O");
				player1Turn = true;
				check();
			} else if (button[3].getText() == "") {
				button[3].setIcon(o);
				button[3].setText("O");
				player1Turn = true;
				check();
			}
		}
		if (l2 == 2 && player1Turn == false) {
			if (button[3].getText() == "") {
				button[3].setIcon(o);
				button[3].setText("O");
				check();
				player1Turn = true;
			} else if (button[4].getText() == "") {
				button[4].setIcon(o);
				button[4].setText("O");
				player1Turn = true;
				check();
			} else if (button[5].getText() == "") {
				button[5].setIcon(o);
				button[5].setText("O");
				player1Turn = true;
				check();
			}
		}
		if (l3 == 2 && player1Turn == false) {
			if (button[6].getText() == "") {
				button[6].setIcon(o);
				button[6].setText("O");
				check();
				player1Turn = true;
			} else if (button[7].getText() == "") {
				button[7].setIcon(o);
				button[7].setText("O");
				player1Turn = true;
				check();
			} else if (button[8].getText() == "") {
				button[8].setIcon(o);
				button[8].setText("O");
				player1Turn = true;
				check();
			}
		}
		if (c1 == 2 && player1Turn == false) {
			if (button[0].getText() == "") {
				button[0].setIcon(o);
				button[0].setText("O");
				check();
				player1Turn = true;
			} else if (button[3].getText() == "") {
				button[3].setIcon(o);
				button[3].setText("O");
				player1Turn = true;
				check();
			} else if (button[6].getText() == "") {
				button[6].setIcon(o);
				button[6].setText("O");
				player1Turn = true;
				check();
			}
		}
		if (c2 == 2 && player1Turn == false) {
			if (button[1].getText() == "") {
				button[1].setIcon(o);
				button[1].setText("O");
				check();
				player1Turn = true;
			} else if (button[4].getText() == "") {
				button[4].setIcon(o);
				button[4].setText("O");
				player1Turn = true;
				check();
			} else if (button[7].getText() == "") {
				button[7].setIcon(o);
				button[7].setText("O");
				player1Turn = true;
				check();
			}
		}
		if (c3 == 2 && player1Turn == false) {
			if (button[2].getText() == "") {
				button[2].setIcon(o);
				button[2].setText("O");
				check();
				player1Turn = true;
			} else if (button[5].getText() == "") {
				button[5].setIcon(o);
				button[5].setText("O");
				player1Turn = true;
				check();
			} else if (button[8].getText() == "") {
				button[8].setIcon(o);
				button[8].setText("O");
				player1Turn = true;
				check();
			}
		}
		if (d1 == 2 && player1Turn == false) {
			if (button[0].getText() == "") {
				button[0].setIcon(o);
				button[0].setText("O");
				check();
				player1Turn = true;
			} else if (button[4].getText() == "") {
				button[4].setIcon(o);
				button[4].setText("O");
				player1Turn = true;
				check();
			} else if (button[8].getText() == "") {
				button[8].setIcon(o);
				button[8].setText("O");
				player1Turn = true;
				check();
			}
		}
		if (d2 == 2 && player1Turn == false) {
			if (button[2].getText() == "") {
				button[2].setIcon(o);
				button[2].setText("O");
				check();
				player1Turn = true;
			} else if (button[4].getText() == "") {
				button[4].setIcon(o);
				button[4].setText("O");
				player1Turn = true;
				check();
			} else if (button[6].getText() == "") {
				button[6].setIcon(o);
				button[6].setText("O");
				player1Turn = true;
				check();
			}
		}
		// IA play random by default
		if (player1Turn == false) {
			checkcase = Arrays.stream(checkcase).filter(e -> e != i).toArray();
			int ai_choices;
			int rnd;
			do {
				rnd = new Random().nextInt(checkcase.length);
				ai_choices = checkcase[rnd];
			} while (button[ai_choices].getText() != "");
			button[ai_choices].setIcon(o);
			button[ai_choices].setText("O");
			final int rndF = rnd;
			checkcase = Arrays.stream(checkcase).filter(e -> e != rndF).toArray();
			player1Turn = true;
			check();
		}
	}
	//check if the X or the O win with the help of the Text put when the player play
	private void check() {
		//check X win
		if ((button[0].getText() == "X") && (button[1].getText() == "X") && (button[2].getText() == "X")) {
			xWins();
		}

		else if ((button[3].getText() == "X") && (button[4].getText() == "X") && (button[5].getText() == "X")) {
			xWins();
		}

		else if ((button[6].getText() == "X") && (button[7].getText() == "X") && (button[8].getText() == "X")) {
			xWins();
		}

		else if ((button[0].getText() == "X") && (button[3].getText() == "X") && (button[6].getText() == "X")) {
			xWins();
		}

		else if ((button[1].getText() == "X") && (button[4].getText() == "X") && (button[7].getText() == "X")) {
			xWins();
		}

		else if ((button[2].getText() == "X") && (button[5].getText() == "X") && (button[8].getText() == "X")) {
			xWins();
		}

		else if ((button[0].getText() == "X") && (button[4].getText() == "X") && (button[8].getText() == "X")) {
			xWins();
		}

		else if ((button[2].getText() == "X") && (button[4].getText() == "X") && (button[6].getText() == "X")) {
			xWins();
		}

		// check O win conditions

		else if ((button[0].getText() == "O") && (button[1].getText() == "O") && (button[2].getText() == "O")) {
			oWins();
		}

		else if ((button[3].getText() == "O") && (button[4].getText() == "O") && (button[5].getText() == "O")) {
			oWins();
		}

		else if ((button[6].getText() == "O") && (button[7].getText() == "O") && (button[8].getText() == "O")) {
			oWins();
		}

		else if ((button[0].getText() == "O") && (button[3].getText() == "O") && (button[6].getText() == "O")) {
			oWins();
		}

		else if ((button[1].getText() == "O") && (button[4].getText() == "O") && (button[7].getText() == "O")) {
			oWins();
		}

		else if ((button[2].getText() == "O") && (button[5].getText() == "O") && (button[8].getText() == "O")) {
			oWins();
		}

		else if ((button[0].getText() == "O") && (button[4].getText() == "O") && (button[8].getText() == "O")) {
			oWins();
		}

		else if ((button[2].getText() == "O") && (button[4].getText() == "O") && (button[6].getText() == "O")) {
			oWins();
		}
		// in case of draw, the last player to play is P1
		else if (button[0].getText() != "" && button[1].getText() != "" && button[2].getText() != ""
				&& button[3].getText() != "" && button[4].getText() != "" && button[5].getText() != ""
				&& button[6].getText() != "" && button[7].getText() != "" && button[8].getText() != "") {
			for (int i = 0; i < 9; i++) {
				button[i].setEnabled(false);
			}
			player1Win = true;
			score[2]++;
			Timer time = new Timer();
			TimerTask task = new TimerTask() {
				@Override
				public void run() {
					if(p1or2 == true) {
						save(score);
					}
					else {
						turnP.setVisible(false);
					}
					JOptionPane.showMessageDialog(null, "Egalité", "Tic Tac Toe", JOptionPane.PLAIN_MESSAGE);
					screen.dispose();
					new GameFrame(p1or2, selected);
				}
			};
			time.schedule(task, 1000);
		}

	}

	private void xWins() {
		for (int i = 0; i < 9; i++) {
			button[i].setEnabled(false);
		}
		player1Win = true;
		score[0]++;
		Timer time = new Timer();
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				if(p1or2 == true) {
					save(score);
				}
				else {
					turnP.setVisible(false);
				}
				JOptionPane.showMessageDialog(null,"X Gagne",  "Tic Tac Toe", JOptionPane.PLAIN_MESSAGE);
				screen.dispose();
				new GameFrame(p1or2, selected);
			}
		};
		time.schedule(task, 1000);
	}

	private void oWins() {

		for (int i = 0; i < 9; i++) {
			button[i].setEnabled(false);
		}
		score[1]++;
		if(p1or2 == true) {
			save(score);
		}
		else {
			turnP.setVisible(false);
		}
		Timer time = new Timer();
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				JOptionPane.showMessageDialog(null, "O Gagne",  "Tic Tac Toe", JOptionPane.PLAIN_MESSAGE);
				screen.dispose();
				new GameFrame(p1or2, selected);
			}
		};
		time.schedule(task, 1000);
	}
    //save when call in save.txt
	public static void save(int[] score) {
		try {
			File fileS = new File("save.txt");
			PrintWriter save = new PrintWriter(fileS.getAbsolutePath());
			save.println(score[0]);
			save.println(score[1]);
			save.println(score[2]);
			save.close();
		} catch (FileNotFoundException e) {
			System.out.println("fichier save introuvable");
		}
	}
	// load method who load the previous score

	public static int[] load() {
		int[] s = new int[3];
		File fileS = new File("save.txt");
		Scanner sc;
		try {
			sc = new Scanner(new File(fileS.getAbsolutePath()));
			for (int i = 0; i < 3; i++) {
				s[i] = Integer.valueOf(sc.nextLine());
			}
			sc.close();
		} catch (FileNotFoundException e) {
			System.out.println("fichier non trouvé");
		}

		return s;
	}
}
