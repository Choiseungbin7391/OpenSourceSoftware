package component;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class PixelPanel extends JPanel {
    private final int width;
    private final int height;
    private final BufferedImage image;
    private int[] pixels;

    public PixelPanel(int width, int height) {
        this.width = width;
        this.height = height;
        this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        this.pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
        setPreferredSize(new Dimension(width, height));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g2d.drawImage(image, 0, 0, getWidth(), getHeight(), null);
    }

    public void setPixelsColor(int[] pixels) {
        if (pixels != null && pixels.length == this.pixels.length)
            System.arraycopy(pixels, 0, this.pixels, 0, this.pixels.length);
    }

    public int[] getPixelsColor() {
        int[] clone = pixels.clone();
        return clone;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}