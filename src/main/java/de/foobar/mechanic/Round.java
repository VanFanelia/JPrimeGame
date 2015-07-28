package de.foobar.mechanic;

import de.foobar.mechanic.player.IPlayer;
import org.apache.commons.lang3.builder.ToStringBuilder;


public class Round {

  private IPlayer player1;

  private IPlayer player2;

  private int scorePlayer1 = 0;

  private int scorePlayer2 = 0;

  public Round(IPlayer player1, IPlayer player2) {
    this.player1 = player1;
    this.player2 = player2;
  }

  public IPlayer getPlayer1() {
    return player1;
  }

  public IPlayer getPlayer2() {
    return player2;
  }

  public int getScorePlayer1() {
    return scorePlayer1;
  }

  public int getScorePlayer2() {
    return scorePlayer2;
  }

  public void setScorePlayer1(int scorePlayer1) {
    this.scorePlayer1 = scorePlayer1;
  }

  public void setScorePlayer2(int scorePlayer2) {
    this.scorePlayer2 = scorePlayer2;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("player1", player1)
        .append("player2", player2)
        .append("scorePlayer1", scorePlayer1)
        .append("scorePlayer2", scorePlayer2)
        .toString();
  }

  /**
   * get score of player.
   * @param currentPlayer the player
   * @return the score
   */
  public int getScoreOfPlayer(IPlayer currentPlayer) {
    if ( this.player1 == currentPlayer) {
      return this.getScorePlayer1();
    }
    return this.getScorePlayer2();
  }

  /**
   * Add a score to the current score of a player.
   * @param currentPlayer the player
   * @param pickedNumber the score to add
   */
  public void addScore(IPlayer currentPlayer, int pickedNumber) {
    if ( this.player1 == currentPlayer) {
      this.setScorePlayer1(this.getScorePlayer1() + pickedNumber);
    } else {
      this.setScorePlayer2(this.getScorePlayer2() + pickedNumber);
    }

  }
}
