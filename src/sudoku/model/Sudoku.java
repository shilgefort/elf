package sudoku.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Observable;

/**
 * Represents one Game of sudoku. A <code>Sudoku</code> switches between the
 * distinct states <code>INVALID</code>, <code>VALID</code> and <code>WON</code>
 * . <br> </br> It consists of a matrix of 9 x 9 <code>Field</code>. The matrix
 * is subdivided into 9 distinct rows, 9 distinct columns and 9 distinct
 * sub-fields which are a matrix of 3x3 elements. <br> </br> A
 * <code>Sudoku</code> is VALID if every row, column and subfield does only
 * contain distinct <code>SudokuNumber</code> as values or <code>null</code>.
 * <br> </br> Else it is INVALID. <br> </br> A <code>Sudoku</code> is considered
 * to be WON if it is VALID and every <code>Field</code> elements contains a
 * non-null value. At the beginning a <code>Sudoku</code> is VALID. A
 * <code>Sudoku</code> only changes its state via the method {@link
 * #proofState()} which is called from the contained Field instances.
 *
 * @author Mathias Menninghaus (mathias.menninghaus@uos.de)
 */
public class Sudoku extends Observable {

   public enum State {
      INVALID, VALID, WON;
   }

   public Area getArea(int i) {
      return areas.get(i);
   }

   private ArrayList<Area> areas;

   private State state;

   /**
    * Creates a new <code>Sudoku</code>. Will convert the entries in
    * <code>numbers</code> into <code>SudokuNumber</code> elemnts or into
    * <code>null</code> if they are not within 1-9. Will build up a sudoku
    * gameboard which consists of <code>Field</code> elements.
    *
    * @param numbers sudoku gameboard as an <code>int</code> array which will be
    *                converted to a <code>Field</code> array.
    */
   public Sudoku(int[][] numbers) {
      SudokuNumber[][] fields = convert(numbers);

      areas = new ArrayList<Area>(27);
      this.setState(State.VALID);

      /*
       * create rows
       */
      for (SudokuNumber[] row : fields) {
         LinkedList<Field> convRow = new LinkedList<Field>();
         for (int j = 0; j < 9; j++) {
            convRow.add(row[j] == null ? this.new Field() : this.new Field(
                  row[j]));
         }
         this.areas.add(new Row(convRow));
      }
      /*
       * create cols
       */
      for (int i = 0; i < 9; i++) {
         LinkedList<Field> convCol = new LinkedList<Field>();
         for (int j = 0; j < 9; j++) {
            convCol.add(areas.get(j).get(i));
         }
         this.areas.add(new Col(convCol));
      }
      /*
       * create SubFields
       */
      for (int i = 0; i < 3; i++) {
         for (int j = 0; j < 3; j++) {
            LinkedList<Field> convSubField = new LinkedList<Field>();
            for (int k = i * 3; k < i * 3 + 3; k++) {
               for (int l = j * 3; l < j * 3 + 3; l++) {
                  convSubField.add(areas.get(k).get(l));
               }
            }
            this.areas.add(new SubField(convSubField));
         }
      }
   }

   /**
    * Estimates if all <code>Area</code> elements are valid.
    *
    * @return <code>true</code> if all <code>Area</code> elements are valid
    * @see {@link Area#checkValidity()}
    */
   boolean checkValidity() {
      for (Area a : this.areas) {
         if (!a.checkValidity()) {
            return false;
         }
      }
      return true;
   }

   /**
    * Estimates if this <code>Sudoku</code> is already won. i.e. if its state is
    * VALID or WON and all <code>Area</code> elements are completely filled.
    *
    * @return <code>true</code> if this <code>Sudoku</code> is already won
    * @see {@link Area#checkFilled()}
    */
   boolean checkWon() {
      if (this.getState() == State.VALID || this.getState() == State.WON) {
         for (Area a : areas) {
            if (!a.checkFilled()) {
               return false;
            }
         }
         return true;
      } else {
         return false;
      }
   }

   private SudokuNumber[][] convert(int[][] sudoku) {
      SudokuNumber[][] ret = new SudokuNumber[9][9];
      for (int i = 0; i < 9; i++) {
         for (int j = 0; j < 9; j++) {
            ret[i][j] = SudokuNumber.convert(sudoku[i][j]);
         }
      }
      return ret;
   }

   public Field getField(int row, int col) {
      if (row < 0 || row > 8 || col < 0 || col > 8) {
         throw new IllegalArgumentException("out of bounds " + row + "," + col);
      }
      return this.areas.get(row).get(col);
   }

   public State getState() {
      return this.state;
   }

   private void setState(State s) {
      this.state = s;
   }

   void proofState() {
      if (this.checkValidity()) {
         this.setState(State.VALID);
         if (this.checkWon()) {
            this.setState(State.WON);
         }
      } else {
         this.setState(State.INVALID);
      }
      //Meldet Veraenderung an Observer
      this.setChanged();
      this.notifyObservers();
   }

   /**
    * Representation of one cell in a Sudoku game. A Field, that has been
    * initialized with a SudokuNumber != null is changeable.
    */
   public class Field{

      private boolean changeable;
      private SudokuNumber value;

      public Field() {
         this(null);
      }

      public Field(SudokuNumber value) {
         if (value == null) {
            this.changeable = true;
         } else {
            this.changeable = false;
         }
         this.value = value;
      }

      /**
       * Changes the number in this <code>Field</code>. Calls {@link
       * Sudoku#proofState} of the corresponding <code>Sudoku</code> .
       *
       * @param number the new <code>SudokuNumber</code> for this
       *               <code>Field</code>
       * @throws NullPointerException  if <code>number</code> is <code>null</code>
       * @throws IllegalStateException if this <code>Field</code> is not
       *                               changeable, i.e. if it has been created
       *                               with a <code>SudokuNumber</code>
       */
      public void change(SudokuNumber number) {
         if (number == null) {
            throw new NullPointerException();
         }
         if (!this.isChangeable()) {
            throw new IllegalStateException("unchangeable field");
         }
         this.value = number;
         proofState();
         
      }

      public SudokuNumber getValue() {
         return this.value;
      }

      /**
       * Determins whether this Field is changeable or not.
       *
       * @return true, if this Field is changeable.
       */
      public boolean isChangeable() {
         return this.changeable;
      }
   }
}
