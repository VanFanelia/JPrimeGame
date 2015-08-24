package de.foobar.ui.elements;

import javax.swing.JTextField;
import javax.swing.text.Document;

public class IntTextField extends JTextField {

  public IntTextField(int defaultValue, int size) {
    super("" + defaultValue, size);
  }

  protected Document createDefaultModel() {
    return new IntTextDocument();
  }

  @Override
  public boolean isValid() {
    try {
      int input = Integer.parseInt(getText());
      return input > 1;
    } catch (NumberFormatException | NullPointerException e) {
      return false;
    }
  }

  /**
   * Get int value of text field.
   * @return int value
   */
  public int getValue() {
    try {
      return Integer.parseInt(getText());
    } catch (NumberFormatException e) {
      return 0;
    }
  }
}


