package sudoku.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Observable;

/**
 * Represents one area (row, column or subfield) of a Sudoku.
 *
 * @author Mathias Menninghaus (mathias.menninghaus@uos.de)
 */
public class Area extends Observable{

   private ArrayList<Sudoku.Field> fields;

   /**
    * Creates an Area. Must be instantiated with a <code>Collection</code> of
    * exactly <code>9</code> <code>Field</code> elements.
    *
    * @param fields
    * @throws IllegalArgumentException if not 9 <code>Field</code> elements are
    *                                  submitted within instantiation
    */
   Area(Collection<Sudoku.Field> fields) {
      if (fields.size() != 9) {
         throw new IllegalArgumentException();
      }
      if (fields.contains(null)) {
         throw new NullPointerException();
      }
      this.fields = new ArrayList<Sudoku.Field>();
      this.fields.addAll(fields);
   }

   /**
    * Estimates if every <code>Field</code> in this area is set with a
    * <code>SudokuNumber</code>
    *
    * @return <code>true</code> if every <code>Field</code> is set.
    */
   public boolean checkFilled() {
      for (Sudoku.Field f : fields) {
         if (f.getValue() == null) {
            return false;
         }
      }
      return true;
   }

   /**
    * Estimates if the <code>SudokuNumber</code> of all <code>Field</code> in
    * this <code>Area</code> are distinct.
    *
    * @return <code>true</code> if values of this Area <code>Field</code> values
    * are distinct
    */
   public boolean checkValidity() {
      boolean[] there = new boolean[9];
      /*
       * boolean array which determines, which number already has been set in
       * this area
       */
      Arrays.fill(there, false);

      /*
       * go through every Field of this Area and lookup, if the number in
       * that Field already has been set in this Area
       */
      for (Sudoku.Field f : fields) {
         if (f.getValue() != null) {

            if (there[f.getValue().ordinal()]) {
               return false;
            } else {
               there[f.getValue().ordinal()] = true;
            }
         }
      }
      return true;
   }

   public Sudoku.Field get(int index) {
      return fields.get(index);
   }
}
