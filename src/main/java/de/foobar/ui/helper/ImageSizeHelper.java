package de.foobar.ui.helper;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

/**
 * Editor: van on 26.07.15.
 */
public class ImageSizeHelper {


	private static final int DEFAULT_WIDTH = 64;
	private static final int DEFAULT_HEIGHT = 64;

	public static BufferedImage getScaledImage(BufferedImage image) {
		return getScaledImage(image,DEFAULT_WIDTH,DEFAULT_HEIGHT);
	}

	public static BufferedImage getScaledImage(BufferedImage image, int width, int height) {

		int imageWidth  = image.getWidth();
		int imageHeight = image.getHeight();

		double scaleX = (double)width/imageWidth;
		double scaleY = (double)height/imageHeight;
		AffineTransform scaleTransform = AffineTransform.getScaleInstance(scaleX, scaleY);
		AffineTransformOp bilinearScaleOp = new AffineTransformOp(scaleTransform, AffineTransformOp.TYPE_BILINEAR);

		return bilinearScaleOp.filter( image, new BufferedImage(width, height, image.getType()));
	}
}
