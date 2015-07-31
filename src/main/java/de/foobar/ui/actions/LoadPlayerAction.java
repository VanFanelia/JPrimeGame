package de.foobar.ui.actions;

import de.foobar.mechanic.GameController;
import de.foobar.ui.helper.ClassFileFilter;
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

  private GameController gameController;

  public LoadPlayerAction(GameController gameController) {
    this.gameController = gameController;
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
    //chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    chooser.setFileFilter(new ClassFileFilter());
    chooser.setAcceptAllFileFilterUsed(false);

    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
      System.out.println("getCurrentDirectory(): " + chooser.getCurrentDirectory());
      System.out.println("getSelectedFile() : " + chooser.getSelectedFile());

      this.gameController.addAdditionalPlayers(chooser.getCurrentDirectory());
    } else {
      System.out.println("No Selection ");
    }

  }
}

