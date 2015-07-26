package de.foobar.mechanic.player;

import java.awt.image.BufferedImage;

/**
 * Editor: van on 24.03.15.
 */
public class PaulPickLast implements IPlayer
{
	@Override
	public String getPlayerName() {
		return "Paul Pick Last";
	}

	@Override
	public BufferedImage getPlayerImage() {
		BufferedImage picture = new BufferedImage(64,64, BufferedImage.TYPE_INT_RGB );
		for(int x = 10; x<55;x++) {
			for(int y = 10; y<55;y++) {
				picture.setRGB(x,y, 21351);
			}
		}
		return picture;
	}

	@Override
	public void initNumbers(int[] numbers) {
		//ignore, i am the lucky guy
	}

	@Override
	public int pickNumber(int[] currentNumbers, int enemyScore, int yourScore)
	{
		return currentNumbers[currentNumbers.length-1];
	}


}