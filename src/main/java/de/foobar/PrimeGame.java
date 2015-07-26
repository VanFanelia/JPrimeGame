package de.foobar;

import de.foobar.mechanic.GameController;
import de.foobar.ui.GameWindow;

/**
 * Editor: van on 24.03.15.
 */
public class PrimeGame {


	public GameWindow gameWindow;

	public GameController gameController;

	public static void main(String[] args)
	{
		PrimeGame game = new PrimeGame();
		game.gameController = new GameController();
		game.gameWindow = new GameWindow(game.gameController);
		game.gameController.setGameWindow(game.gameWindow);
	}
}
