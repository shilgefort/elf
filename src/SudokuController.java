import java.awt.event.*;
import sudoku.model.*;

public class SudokuController implements ActionListener{
	
	private Sudoku model;
	private MyTextField textfield;
	
	public SudokuController(Sudoku model, MyTextField textfield){
		this.model=model;
		this.textfield=textfield;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		String text=textfield.getText();
		if(!text.isEmpty()){
			if(isNumber(text)){
				if(Integer.parseInt(text)>=1 && Integer.parseInt(text)<=9){
					model.getField(textfield.row, textfield.col).change(SudokuNumber.convert(Integer.parseInt(text)));
				}	
			}
	
		}
		
	}
	
	private boolean isNumber(String text){
		return text.matches("[-+]?\\d*\\.?\\d+");
	}
}	
