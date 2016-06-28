import javax.swing.JFrame;

import sudoku.SudokuFields;
import sudoku.model.*;

public class SudokuExe {

		public static void main(String[]args){
			
			Sudoku model = new Sudoku(SudokuFields.nearlyWonSudoku);
			
			SudokuView view = new SudokuView(model);
			
			JFrame frame = new JFrame("Sudoku");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setContentPane(view);
			
			frame.pack();
			frame.setVisible(true);
			
			
		}

}
