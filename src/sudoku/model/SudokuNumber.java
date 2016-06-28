package sudoku.model;

public enum SudokuNumber {

   ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE;

   public static SudokuNumber convert(int number) {
      switch (number) {
         case 1:
            return ONE;
         case 2:
            return TWO;
         case 3:
            return THREE;
         case 4:
            return FOUR;
         case 5:
            return FIVE;
         case 6:
            return SIX;
         case 7:
            return SEVEN;
         case 8:
            return EIGHT;
         case 9:
            return NINE;
         default:
            return null;
      }
   }

   public static int convert(SudokuNumber number) {
      switch (number) {
         case ONE:
            return 1;
         case TWO:
            return 2;
         case THREE:
            return 3;
         case FOUR:
            return 4;
         case FIVE:
            return 5;
         case SIX:
            return 6;
         case SEVEN:
            return 7;
         case EIGHT:
            return 8;
         case NINE:
            return 9;
         default:
            throw new IllegalStateException();
      }

   }

}
