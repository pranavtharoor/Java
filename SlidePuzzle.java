/*
Program Name: SlidePuzzle.java (Mystic Square)
Author: Pranav Tharoor (pranav.tharoor@gmail.com)
Description: Click the number you want to slide to move it to the adjascent empty space.
Your aim is to arrange the numbers in acending order.
*/

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.Border;
import java.util.Random;

public class SlidePuzzle extends JFrame {

	public static void main(String args[]) {
		new SlidePuzzle();
	}

	int rows = 4, columns = 4;
	Random random = new Random();
	JButton[][] numberButton = new JButton[rows][columns];
	JButton shuffleButton = new JButton("Shuffle");
	final int CELL_HEIGHT = 60, CELL_LENGTH = 60, CELL_PADDING = 10, PANEL_BORDER = 25;
	Color backgroundColor = new Color(103,200,190);
	Color cellColor = new Color(102, 204, 255);
	Color textColor = new Color(102, 102, 102);
	Color cellColorCorrect = new Color(102, 204, 102);
	Color textColorCorrect = new Color(255, 255, 255);
	Border mainBorder = BorderFactory.createEmptyBorder(20, 20, 20, 20);
	ListenForButton buttonClicked = new ListenForButton();

	public SlidePuzzle() {
		this.setSize(CELL_LENGTH * (columns + 1) + CELL_PADDING * columns , CELL_HEIGHT * (rows + 2) + CELL_PADDING * rows + PANEL_BORDER * 2);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Game Of 15");
		JPanel mainPanel = new JPanel();
		JPanel settingsPanel = new JPanel();
		JPanel outerPanel = new JPanel();
		mainPanel.setBorder(mainBorder);
		GridLayout mainLayout = new GridLayout(rows, columns, CELL_PADDING, CELL_PADDING);
		mainPanel.setLayout(mainLayout);
		mainPanel.setBackground(backgroundColor);
		settingsPanel.setBackground(backgroundColor);
		outerPanel.setBackground(backgroundColor);

		//Initialize a 2-D array of buttons to form the grid
		for(int i = 0, k = 1; i < rows; i++)
			for(int j = 0; j < columns; j++) {
				if(j == columns - 1 && i == rows - 1)
					numberButton[i][j] = new JButton(" ");
				else
					numberButton[i][j] = new JButton(String.valueOf(k++));
				numberButton[i][j].setPreferredSize(new Dimension(CELL_LENGTH, CELL_HEIGHT));
				numberButton[i][j].setBackground(cellColor);
				numberButton[i][j].setBorderPainted(false);
				numberButton[i][j].setForeground(textColor);
				numberButton[i][j].addActionListener(buttonClicked);
				mainPanel.add(numberButton[i][j]);
			}

		shuffleButton.setPreferredSize(new Dimension(CELL_LENGTH * 2, CELL_HEIGHT));
		shuffleButton.setBackground(cellColor);
		shuffleButton.setBorderPainted(false);
		shuffleButton.setForeground(textColor);
		shuffleButton.addActionListener(buttonClicked);
		settingsPanel.add(shuffleButton);
		outerPanel.add(mainPanel);
		outerPanel.add(settingsPanel);
		this.add(outerPanel);
		this.setVisible(true);

		//Initialize the grid with the correct color
		buttonClicked.check();

	}

	class ListenForButton implements ActionListener {
		void shuffleNumbers() {
			int emptyButtonRow = -1, emptyButtonColumn = -1;

			//Search for the blank space 
			for(int i = 0; i < rows; i++)
				for(int j = 0; j < columns; j++)
					if(numberButton[i][j].getText() == " "){
						emptyButtonRow = i;
						emptyButtonColumn = j;
					}

			int k = 0;

			//Click randomly on one of the adjacent buttons to the blank space
			while( k < (rows - 1) * (columns - 1))
				if(emptyButtonRow != 0 && random.nextInt(4) == 0) {
					emptyButtonRow--;
					numberButton[emptyButtonRow][emptyButtonColumn].doClick();
					k++;
				} else if(emptyButtonColumn != columns - 1 && random.nextInt(4) == 1) {
					emptyButtonColumn++;
					numberButton[emptyButtonRow][emptyButtonColumn].doClick();
					k++;
				} else if(emptyButtonRow != rows - 1 && random.nextInt(4) == 2) {
					emptyButtonRow++;
					numberButton[emptyButtonRow][emptyButtonColumn].doClick();
					k++;
				} else if(emptyButtonColumn != 0 && random.nextInt(4) == 3) {
					emptyButtonColumn--;
					numberButton[emptyButtonRow][emptyButtonColumn].doClick();
					k++;
				}

		}
		void check(){
			int flag = 1, emptyButtonRow = -1, emptyButtonColumn = -1;

			//Check if the numbers are in order
			for(int i = 0; i < rows; i++)
				for(int j = 0; j < columns; j++) {
					if(numberButton[i][j].getText() != " " && Integer.parseInt(numberButton[i][j].getText()) == i * columns + j + 1) {
						numberButton[i][j].setBackground(cellColorCorrect);
						numberButton[i][j].setForeground(textColorCorrect);
					} else if(numberButton[i][j].getText() != " " && Integer.parseInt(numberButton[i][j].getText()) != i * columns + j + 1) {
						numberButton[i][j].setBackground(cellColor);
						numberButton[i][j].setForeground(textColor);
						flag = 0;
					} else {
						emptyButtonRow = i;
						emptyButtonColumn = j;
					}
				}

			//If the number is correctly positioned, change its color
			if(flag == 1)
				numberButton[emptyButtonRow][emptyButtonColumn].setBackground(cellColorCorrect);
		}
		public void swap(int a, int b, int a1, int b1) {

			//swaps number at [a][b] with [a1][b1]
			String buttonText = numberButton[a][b].getText();
			numberButton[a][b].setText(numberButton[a1][b1].getText());
			numberButton[a1][b1].setText(buttonText);
			numberButton[a1][b1].setBackground(cellColor);
			numberButton[a1][b1].setForeground(textColor);

		}
		public void actionPerformed(ActionEvent e) {

			//Respond to a button being clicked by swapping it
			for(int i = 0; i < rows; i++)
				for(int j = 0; j < columns; j++)
					if(e.getSource() == shuffleButton)
						shuffleNumbers();
					else if(e.getSource() == numberButton[i][j]) {
						if(i != rows - 1 && numberButton[i + 1][j].getText() == " ")
							swap((i + 1), j, i, j);
						else if(i != 0 && numberButton[i - 1][j].getText() == " ")
							swap((i - 1), j, i, j);
						else if(j != columns - 1 && numberButton[i][j + 1].getText() == " ")
							swap(i, (j + 1), i, j);
						else if(j != 0 && numberButton[i][j - 1].getText() == " ")
							swap(i, (j - 1), i, j);
						check();
					}

		}
	}
}