package de.foobar.ui.helper;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;


public class ImageSizeHelper {


  private static final int DEFAULT_WIDTH = 64;
  private static final int DEFAULT_HEIGHT = 64;

  public static BufferedImage getScaledImage(BufferedImage image) {
    return getScaledImage(image,DEFAULT_WIDTH,DEFAULT_HEIGHT);
  }

  /**
   * Scale an image to a specified widht/ height.
   * @param image the image to scale
   * @param width the width.
   * @param height the height
   * @return the new image
   */
  public static BufferedImage getScaledImage(BufferedImage image, int width, int height) {

    int imageWidth  = image.getWidth();
    int imageHeight = image.getHeight();

    double scaleX = (double) width / (double) imageWidth;
    double scaleY = (double) height / (double) imageHeight;
    AffineTransform scaleTransform = AffineTransform.getScaleInstance(scaleX, scaleY);
    AffineTransformOp bilinearScaleOp = new AffineTransformOp(scaleTransform, AffineTransformOp.TYPE_BILINEAR);

    return bilinearScaleOp.filter( image, new BufferedImage(width, height, image.getType()));
  }
}
