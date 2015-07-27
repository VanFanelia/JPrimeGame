package de.foobar.ui.actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 * Action to close the game and exit the program.
 */
public class CloseAction extends AbstractAction {

  private JFrame frame;

  final Icon smallIcon = new ImageIcon(CloseAction.class.getResource("/icon/cross.png") );

  /**

   * @param frame the frame to close.
   */
  public CloseAction(JFrame frame) {
    this.frame = frame;
    putValue( Action.NAME, "Close" );
    putValue( Action.DISPLAYED_MNEMONIC_INDEX_KEY, 1 );
    putValue( Action.SMALL_ICON, smallIcon );
  }

  @Override
  public void actionPerformed( ActionEvent event ) {
    frame.setVisible(false);
    System.exit(0);
  }
}

