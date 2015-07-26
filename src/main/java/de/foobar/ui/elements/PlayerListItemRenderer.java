package de.foobar.ui.elements;

import de.foobar.mechanic.player.IPlayer;
import static de.foobar.ui.helper.ImageSizeHelper.getScaledImage;
import java.awt.*;
import javax.swing.*;

/**
 * Editor: van on 22.07.15.
 */
public class PlayerListItemRenderer extends DefaultListCellRenderer {

	private JLabel label;
	private Color textSelectionColor = Color.BLACK;
	private Color backgroundSelectionColor = Color.CYAN;
	private Color textNonSelectionColor = Color.BLACK;
	private Color backgroundNonSelectionColor = Color.WHITE;

	public PlayerListItemRenderer() {
		label = new JLabel();
		label.setOpaque(true);
	}

	@Override
	public Component getListCellRendererComponent( JList list, Object value, int index, boolean selected, boolean expanded) {

		IPlayer player = (IPlayer) value;
		label.setIcon(new ImageIcon(getScaledImage(player.getPlayerImage())));
		label.setText(player.getPlayerName());

		if (selected) {
			label.setBackground(backgroundSelectionColor);
			label.setForeground(textSelectionColor);
		} else {
			label.setBackground(backgroundNonSelectionColor);
			label.setForeground(textNonSelectionColor);
		}

		return label;
	}



}
