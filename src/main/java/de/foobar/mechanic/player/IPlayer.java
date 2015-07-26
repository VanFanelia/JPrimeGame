package de.foobar.mechanic.player;

import java.awt.image.BufferedImage;

/**
 * Editor: van on 24.03.15.
 */
public interface IPlayer
{
	public String getPlayerName();

	public BufferedImage getPlayerImage();

	public void initNumbers(int[] numbers);

	public int pickNumber(int[] currentNumbers, int enemyScore, int yourScore);

}