package de.foobar.ui.actions;

import de.foobar.mechanic.GameController;
import de.foobar.ui.elements.PlayerList;
import java.awt.event.ActionEvent;
import javax.swing.*;

/**
 * Editor: van on 22.07.15.
 */
public class StartGameAction extends AbstractAction {

	private GameController gameController;
	private PlayerList group1;
	private PlayerList group2;

	final Icon smallIcon = new ImageIcon(StartGameAction.class.getResource("/icon/pi_math.png") );

	public StartGameAction(GameController gameController, PlayerList group1, PlayerList group2) {
		this.gameController = gameController;
		putValue( Action.NAME, "Start new Game" );
		putValue( Action.DISPLAYED_MNEMONIC_INDEX_KEY, 1 );
		putValue( Action.SMALL_ICON, smallIcon );
		this.group1 = group1;
		this.group2 = group2;
	}

	public PlayerList getGroup1() {
		return group1;
	}

	public void setGroup1(PlayerList group1) {
		this.group1 = group1;
	}

	public PlayerList getGroup2() {
		return group2;
	}

	public void setGroup2(PlayerList group2) {
		this.group2 = group2;
	}

	public GameController getGameController() {
		return gameController;
	}

	public void setGameController(GameController gameController) {
		this.gameController = gameController;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.gameController.startGame(this.group1.getSelectedValuesList(), this.group2.getSelectedValuesList());
	}


}

