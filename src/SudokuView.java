import javax.swing.*;

import java.awt.*;
import java.util.Observable;
import java.util.Observer;
import sudoku.model.*;
public class SudokuView extends JPanel implements Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Sudoku model;
		
	/**
	 * Konstruktor
	 * @param model uebergebenes Sudoku
	 */
	public SudokuView(Sudoku model) {
		this.model=model;
		this.model.addObserver(this);
		this.setLayout(new GridLayout(9,9));
		
		for(int i=0;i<9;i++){
			for(int j=0; j<9; j++){
				String name;
				if(!model.getField(i, j).isChangeable()){
					int value=SudokuNumber.convert(model.getField(i, j).getValue());
					name=""+value;
					JLabel label=new JLabel(name);
					label.setPreferredSize(new Dimension(30,30));
					label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
					add(label);
					
				}
				else{
					MyTextField textfield = new MyTextField(i, j);
					textfield.setPreferredSize(new Dimension(30, 30));
					textfield.setEditable(true);
					textfield.setBorder(BorderFactory.createLineBorder(Color.BLACK));
					add(textfield);
					SudokuController c = new SudokuController(this.model, textfield);
					textfield.addActionListener(c);
					
				}
			
			}		
		}
	}
	@Override
	public void update(Observable o, Object arg) {
		if(model.getState()==Sudoku.State.INVALID){
			this.setBackground(Color.RED);
		}
		if(model.getState()==Sudoku.State.WON){
			this.setBackground(Color.GREEN);
			System.out.println("Gewonnen!");
		}
		if(model.getState()==Sudoku.State.VALID){
			this.setBackground(Color.WHITE);
			System.out.println("Go on");
		}
	}
}
