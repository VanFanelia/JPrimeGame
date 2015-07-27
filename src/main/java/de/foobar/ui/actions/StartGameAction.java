package de.foobar.ui.actions;

import de.foobar.mechanic.GameController;
import de.foobar.ui.elements.PlayerList;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;


public class StartGameAction extends AbstractAction {

  private GameController gameController;
  private PlayerList group1;
  private PlayerList group2;

  final Icon smallIcon = new ImageIcon(StartGameAction.class.getResource("/icon/pi_math.png") );

  /**
   * Default constructor.
   * @param gameController the controller class to handle the game
   * @param group1 the first group
   * @param group2 the second group
   */
  public StartGameAction(GameController gameController, PlayerList group1, PlayerList group2) {
    this.gameController = gameController;
    putValue( Action.NAME, "Start new Game" );
    putValue( Action.DISPLAYED_MNEMONIC_INDEX_KEY, 1 );
    putValue( Action.SMALL_ICON, smallIcon );
    this.group1 = group1;
    this.group2 = group2;
  }

  @Override
  public void actionPerformed(ActionEvent event) {
    this.gameController.startGame(this.group1.getSelectedValuesList(), this.group2.getSelectedValuesList());
  }


}

