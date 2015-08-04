package de.foobar.mechanic;

import de.foobar.IPlayer;

public class CheatDetectedException extends Exception {

  private IPlayer cheater;

  public CheatDetectedException(String message) {
    super(message);
  }

  public CheatDetectedException(String message, IPlayer currentPlayer) {
    super(message);
    this.cheater = currentPlayer;
  }

  public IPlayer getCheater() {
    return cheater;
  }

  public void setCheater(IPlayer cheater) {
    this.cheater = cheater;
  }
}
