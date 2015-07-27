package de.foobar.ui.actions;

import org.apache.commons.io.FileUtils;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Action to show a popup with rule of the game.
 */
public class GameRuleAction extends AbstractAction {

  private JFrame parent;

  final Icon smallIcon = new ImageIcon(GameRuleAction.class.getResource("/icon/pi_math.png") );

  /**
   * Default constructor.
   * @param frame the main game Frame.
   */
  public GameRuleAction(JFrame frame) {
    this.parent = frame;
    putValue( Action.NAME, "Game Rules" );
    putValue( Action.DISPLAYED_MNEMONIC_INDEX_KEY, 1 );
    putValue( Action.SMALL_ICON, smallIcon );
  }

  @Override
  public void actionPerformed( ActionEvent event ) {
    File file = new File(GameRuleAction.class.getResource("/rules.txt").getFile());
    String alert = "error";
    try {
      alert = FileUtils.readFileToString(file);
    } catch (IOException e1) {
      e1.printStackTrace();
    }
    JOptionPane.showMessageDialog(this.parent, alert);
  }

}

