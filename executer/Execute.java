package executer;

import component.Shape;
import component.Window;
import javax.swing.*;

public class Execute {

    private Timer frameTimer;
    private Window window;

    public Execute() {
        this("640X480");
    }

    public Execute(String str) {
        this.window = new Window(str);
        create();
        frameTimer = new Timer(33, e -> {
            render();
            window.revalidate();
            window.repaint();
        });
        frameTimer.start();
    }
    
    public final void clearColor(int color) {
        window.clearColor(color);
    }

    public final void add(Shape shape) {
        window.makePoly(shape.getVertices(), shape.getColor());
    }

    public void create() {}

    public void render() {}
}
