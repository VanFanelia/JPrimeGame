package de.foobar.ui.elements;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class IntTextDocument extends PlainDocument {


  @Override
  public void insertString(int offs, String str, AttributeSet attributes) throws BadLocationException {
    if (str == null) {
      return;
    }
    String oldString = getText(0, getLength());
    String newString = oldString.substring(0, offs) + str
        + oldString.substring(offs);
    try {
      int result = Integer.parseInt(newString + "0");
      super.insertString(offs, str, attributes);
    } catch (NumberFormatException e) {
      // ignore
    }
  }
}

