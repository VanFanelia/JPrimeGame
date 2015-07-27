package de.foobar.ui.elements;

import static de.foobar.ui.helper.ImageSizeHelper.getScaledImage;

import de.foobar.mechanic.player.IPlayer;

import java.awt.Color;
import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;


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
