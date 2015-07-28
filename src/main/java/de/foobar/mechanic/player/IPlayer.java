package de.foobar.mechanic.player;

import java.awt.image.BufferedImage;

/**
 * Interface to implement for a Player.
 */
public interface IPlayer {

  public String getPlayerName();

  public BufferedImage getPlayerImage();

  public int pickNumber(int[] currentNumbers, int enemyScore, int yourScore);

}