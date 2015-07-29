package de.foobar.mechanic.runable;

import de.foobar.mechanic.GameController;
import javax.swing.SwingWorker;

public class GameWorker extends SwingWorker<Boolean, Boolean> {

  private GameController gameController;

  public GameWorker(GameController gameController) {
    this.gameController = gameController;
  }

  protected void done() {
    this.gameController.gameFinished();
  }

  @Override
  protected Boolean doInBackground() throws Exception {
    gameController.startGame();
    return true;
  }


}
