package resource;

import executer.Execute;
import component.Shape.*;

public class OpenSourceSoftware extends Execute {

    public double i = 0;
    public Triangle triangle;

    @Override
    public void create() {
        triangle = new Triangle(100, 100, 200, (int) Math.round(200*0.866), 0.5, 0xFFFFFF);
        add(triangle);
    }

    @Override
    public void render() {
        clearColor(0x000000);
        int centerX = triangle.getCentroidX();
        int centerY = triangle.getCentroidY();
        add(triangle.rotate(i, centerX, centerY));
        i += 0.1;
    }
}