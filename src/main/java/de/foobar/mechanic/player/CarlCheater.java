package de.foobar.mechanic.player;

import de.foobar.IPlayer;

import java.awt.image.BufferedImage;

/**
 * Example Player who always picks the highest number from the pool.
 */
@SuppressWarnings("unused")
public class CarlCheater implements IPlayer {

  @Override
  public String getPlayerName() {
    return "Carl Cheater";
  }

  @Override
  public BufferedImage getPlayerImage() {
    BufferedImage picture = new BufferedImage(64,64, BufferedImage.TYPE_INT_RGB );
    for (int x = 10; x < 55; x++) {
      for (int y = 10; y < 55; y++) {
        picture.setRGB(x, y, 11351);
      }
    }
    return picture;
  }

  @Override
  public int pickNumber(int[] currentNumbers, int enemyScore, int yourScore) {
    return Integer.MAX_VALUE;
  }


}
