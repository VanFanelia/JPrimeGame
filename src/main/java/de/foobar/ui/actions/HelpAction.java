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
 * Action to show a popup with about infos.
 */

public class HelpAction extends AbstractAction {

  private JFrame parent;

  final Icon smallIcon = new ImageIcon(HelpAction.class.getResource("/icon/exclamation.png") );

  /**
   * Default constructor.
   * @param frame the game frame.
   */
  public HelpAction(JFrame frame) {
    this.parent = frame;
    putValue( Action.NAME, "About" );
    putValue( Action.DISPLAYED_MNEMONIC_INDEX_KEY, 1 );
    putValue( Action.SMALL_ICON, smallIcon );
  }

  @Override
  public void actionPerformed( ActionEvent event ) {
    File file = new File(HelpAction.class.getResource("/about.txt").getFile());
    String alert = "error";
    try {
      alert = FileUtils.readFileToString(file);
    } catch (IOException e1) {
      e1.printStackTrace();
    }
    JOptionPane.showMessageDialog(this.parent, alert);
  }

}

