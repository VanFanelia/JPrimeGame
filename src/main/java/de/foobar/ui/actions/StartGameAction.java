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
  private PlayerList playerList;

  final Icon smallIcon = new ImageIcon(StartGameAction.class.getResource("/icon/pi_math.png") );

  /**
   * Default constructor.
   * @param gameController the controller class to handle the game
   * @param playerList the players fighting each other
   */
  public StartGameAction(GameController gameController, PlayerList playerList) {
    this.gameController = gameController;
    putValue( Action.NAME, "Start new Game" );
    putValue( Action.DISPLAYED_MNEMONIC_INDEX_KEY, 1 );
    putValue( Action.SMALL_ICON, smallIcon );
    this.playerList = playerList;
  }

  @Override
  public void actionPerformed(ActionEvent event) {
    this.gameController.startGame(this.playerList.getSelectedValuesList());
  }


}

