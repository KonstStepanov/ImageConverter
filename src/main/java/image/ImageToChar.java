package image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.net.URL;
/**
 *класс реализации конвертора в текстовую графику
 */
public class ImageToChar implements TextGraphicsConverter {
    protected TextColorSchema schema;
    protected int maxWidth;
    protected int maxHeight;
    protected double maxRatio;
    protected int[] colorArr = new int[3];

    @Override
    public void setMaxWidth(int width) {
        this.maxWidth = width;
    }

    @Override
    public void setMaxHeight(int height) {
        this.maxHeight = height;
    }

    @Override
    public void setMaxRatio(double maxRatio) {
        this.maxRatio = maxRatio;
    }

    @Override
    public void setTextColorSchema(TextColorSchema schema) {
        this.schema = schema;
    }

    @Override
    public String convert(String url) throws IOException, BadImageSizeException {
        if (this.schema == null) {
            setTextColorSchema(new ColorSchema());
        }

        BufferedImage img = ImageIO.read(new URL(url));

        double imgWidth = img.getWidth();
        double imgHeight = img.getHeight();

        if (maxRatio != 0 && imgWidth / imgHeight > maxRatio) {
            double ratio = Math.round(imgWidth / imgHeight * 10.0) / 10.0;
            throw new BadImageSizeException(ratio, maxRatio);
        }

        int newWidth;
        int newHeight;
        if (maxWidth != 0 && maxHeight != 0) {
            newWidth = (int) (imgWidth / (imgHeight > imgWidth ? imgHeight / maxHeight : imgWidth / maxWidth));
            newHeight = (int) (imgHeight / (imgHeight > imgWidth ? imgHeight / maxHeight : imgWidth / maxWidth));
        } else if (maxWidth != 0) {
            newWidth = (int) (imgWidth / (imgWidth / maxWidth));
            newHeight = (int) (imgHeight / (imgWidth / maxWidth));
        } else if (maxHeight != 0) {
            newWidth = (int) (imgWidth / (imgWidth / maxHeight));
            newHeight = (int) (imgHeight / (imgWidth / maxHeight));
        } else {
            newWidth = (int) imgWidth;
            newHeight = (int) imgHeight;
        }

        Image scaledImage = img.getScaledInstance(newWidth, newHeight, BufferedImage.SCALE_SMOOTH);
        BufferedImage bwImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_BYTE_GRAY);

        Graphics2D graphics = bwImg.createGraphics();
        graphics.drawImage(scaledImage, 0, 0, null);

        ImageIO.write(bwImg, "png", new File("out.png"));

        WritableRaster bwRaster = bwImg.getRaster();


        StringBuilder strBuilder = new StringBuilder();
        for (int h = 0; h < newHeight; h++) {
            for (int w = 0; w < newWidth; w++) {
                int color = bwRaster.getPixel(w, h, colorArr)[0];
                strBuilder.append(schema.convert(color)).append(schema.convert(color));
            }
            strBuilder.append("\n");
        }
        return strBuilder.toString();
    }

}
