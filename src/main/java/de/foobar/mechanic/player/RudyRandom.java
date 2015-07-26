package de.foobar.mechanic.player;

import java.awt.image.BufferedImage;

/**
 * Editor: van on 24.03.15.
 */
public class RudyRandom implements IPlayer
{
	@Override
	public String getPlayerName() {
		return "Rudy Random";
	}

	@Override
	public BufferedImage getPlayerImage() {
		return new BufferedImage(100,100, BufferedImage.TYPE_INT_RGB );
	}

	@Override
	public void initNumbers(int[] numbers) {
		//ignore, i am the lucky guy
	}

	@Override
	public int pickNumber(int[] currentNumbers, int enemyScore, int yourScore)
	{
		return currentNumbers[((int) Math.floor(Math.random() * currentNumbers.length))];
	}


}
