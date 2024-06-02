package frontend;

import javax.swing.*;

public class Main {

    public static final int DEFAULT_WIDTH = 600;
    public static final int DEFAULT_HEIGHT = 600;
    public static final int STEP = 25;
    public static final int DELAY = 250;

    public static JFrame f = new JFrame();

    public static void main(String[] args) {
        int w = DEFAULT_WIDTH;
        int h = DEFAULT_HEIGHT;
        DrawingCanvas dc = new DrawingCanvas(w,h);
        f.setSize(w,h);
        f.add(dc);
        f.setTitle("Snake Game");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }
}
