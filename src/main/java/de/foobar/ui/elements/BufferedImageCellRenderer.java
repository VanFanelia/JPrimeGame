package de.foobar.ui.elements;

import java.awt.Component;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;


@SuppressWarnings("unused")
public class BufferedImageCellRenderer extends DefaultTableCellRenderer {

  @Override
  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
    super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    if (value instanceof BufferedImage) {
      setIcon(new ImageIcon((BufferedImage)value));
      setText(null);
    } else {
      setText("Bad image");
    }
    return this;
  }

}