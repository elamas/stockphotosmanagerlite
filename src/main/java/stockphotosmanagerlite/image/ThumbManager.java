package stockphotosmanagerlite.image;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class ThumbManager {

	public final static void generateThumb(String fileInput, String fileOutput, int maxWidth, int maxHeight) throws Exception {
		BufferedImage image = ImageIO.read(new File(fileInput));
		
		//calculo el factor de multiplicacion a aplicar en funcion de si es vertical u horizontal
		int width = image.getWidth();
		int height = image.getHeight();
		float factor;
		
		if (width > height) {
			//horizontal
			factor = (float)maxWidth/width;
		} else {
			//vertical o cuadrada
			factor = (float)maxHeight/height;
		}
		
		boolean preserveAlpha = true;
	    int imageType = preserveAlpha ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
	    BufferedImage scaledBI = new BufferedImage((int)(width*factor), (int)(height*factor), imageType);
	    Graphics2D g = scaledBI.createGraphics();
	    if (preserveAlpha) {
	            g.setComposite(AlphaComposite.Src);
	    }
	    g.drawImage(image, 0, 0, (int)(width*factor), (int)(height*factor), null); 
	    g.dispose();
	    ImageIO.write(scaledBI, "JPG", new File(fileOutput));
	}
}
