package de.foobar;

import de.foobar.mechanic.GameController;
import de.foobar.ui.GameWindow;

public class PrimeGame {

  public GameWindow gameWindow;

  public GameController gameController;

  public static PrimeGame instance;

  /**
   * Global main class.
   * @param args command line args
   */
  public static void main(String[] args) {
    instance = new PrimeGame();
    instance.gameController = new GameController();
    instance.gameWindow = new GameWindow(instance.gameController);
    instance.gameController.setGameWindow(instance.gameWindow);
    System.out.println(instance);
  }
}
