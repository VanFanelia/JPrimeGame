package de.foobar.ui.actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;

/**
 * Action to load a directory with player in it.
 */
public class LoadPlayerAction extends AbstractAction {

  final Icon smallIcon = new ImageIcon(LoadPlayerAction.class.getResource("/icon/folder_user.png") );


  /**
   * Default constructor.
   */
  public LoadPlayerAction() {
    putValue( Action.NAME, "Load Player" );
    putValue( Action.DISPLAYED_MNEMONIC_INDEX_KEY, 2 );
    putValue( Action.SMALL_ICON, smallIcon );
  }

  @Override
  public void actionPerformed( ActionEvent event ) {
    System.out.println("load player action");

    JFileChooser chooser = new JFileChooser();
    chooser.setCurrentDirectory(new java.io.File("."));
    chooser.setDialogTitle("Select directory with player");
    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    chooser.setAcceptAllFileFilterUsed(false);

    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
      System.out.println("getCurrentDirectory(): " + chooser.getCurrentDirectory());
      System.out.println("getSelectedFile() : " + chooser.getSelectedFile());
    } else {
      System.out.println("No Selection ");
    }

  }
}

