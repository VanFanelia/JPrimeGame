package de.foobar.ui.elements;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Editor: van on 26.07.15.
 */
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