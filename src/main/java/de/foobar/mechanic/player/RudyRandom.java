package de.foobar.mechanic.player;

import de.foobar.IPlayer;

import java.awt.image.BufferedImage;

@SuppressWarnings("unused")
public class RudyRandom implements IPlayer {

  @Override
  public String getPlayerName() {
    return "Rudy Random";
  }

  @Override
  public BufferedImage getPlayerImage() {
    return new BufferedImage(100,100, BufferedImage.TYPE_INT_RGB );
  }

  @Override
  public int pickNumber(int[] currentNumbers, int enemyScore, int yourScore) {
    return currentNumbers[((int) Math.floor(Math.random() * currentNumbers.length))];
  }


}
