package executer;

import component.Window;
import javax.swing.*;

public class Execute extends Window {

    Timer frameTimer;

    public Execute() {
        super();
        frameTimer = new Timer(33, e -> {
            clearColor();
            render();
            frame.revalidate();
            frame.repaint();
        });
        frameTimer.start();
    }

    public void create() {}

    public void render() {}
}
