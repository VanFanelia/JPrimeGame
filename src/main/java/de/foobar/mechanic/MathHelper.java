package de.foobar.mechanic;

import java.util.ArrayList;
import java.util.List;

public class MathHelper {

  /**
   * Method get prime factors of a number.
   * @param pickedNumber the number to check
   * @param numbers a list of numbers which filters the prime factors
   * @return a list of prime factors.
   */
  public static List<Integer> getDivisors(int pickedNumber, List<Integer> numbers) {

    List<Integer> result = new ArrayList<>();

    for (Integer number : numbers) {

      if (pickedNumber % number == 0 && number != pickedNumber) {
        result.add(number);
      }
    }

    return result;
  }

  /**
   * convert an int[] to List of Integer.
   * @param ints the ints
   * @return the collection
   */
  public static List<Integer> convertIntArrayToList(int[] ints) {
    List<Integer> result = new ArrayList<>();
    for (int i: ints) {
      result.add(i);
    }
    return result;
  }

}
