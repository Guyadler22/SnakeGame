package frontend;

import backend.Direction;
import backend.Game;
import backend.Snake;
import org.w3c.dom.Text;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.font.TextLayout;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.TimerTask;

public class DrawingCanvas extends JComponent implements ActionListener, KeyListener {

    private Timer timer = new Timer(Main.DELAY, this);
    private java.util.Timer taskTimer = new java.util.Timer();
    private double width;
    private double height;
    private Game game;
    public static final Color DEFAULT_FOOD_COLOR = Color.RED;
    public static final Color DEFAULT_SNAKE_COLOR = Color.GREEN;

    public DrawingCanvas(double w, double h) {
        this.width = w;
        this.height = h;

        // Initialize game screen
        this.game = new Game();

        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        String[] buttons = { "Easy", "Medium", "Hard", "Extreme"};
        int returnValue = JOptionPane.showOptionDialog(null, "Please choose game difficulty", "Snake Game",
                JOptionPane.DEFAULT_OPTION, 0, null, buttons, null);

        switch (returnValue){
            case 0:
                this.timer.setDelay(300);
                break;
            case 1:
                this.timer.setDelay(150);
                break;
            case 2:
                this.timer.setDelay(100);
                break;
            case 3:
                this.timer.setDelay(50);
                break;
            default:
                break;
        }



        this.timer.start();
        this.taskTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                // add food
                if (game.getFoods().size() < 3)
                    game.addRandomFood();
            }
        }, 0, 6000);
    }

    @Override
    protected void paintComponent(Graphics g) {
        // Print screen
        Graphics2D g2d = (Graphics2D) g;
        // Print snake size
        Font font = new Font("Georgia", Font.ITALIC, 38);
        TextLayout textLayout = new TextLayout("Apples: " + String.valueOf(this.game.getSnake().getSize() - Snake.DEFAULT_SIZE), font, g2d.getFontRenderContext());
        textLayout.draw(g2d, 10, 35);
        // Print snake
        for (int i = 0; i < this.game.getSnake().getSize(); i++) {
            if(i == 0) { // Head of the snake
                Ellipse2D.Double e = new Ellipse2D.Double(this.game.getSnake().getX_coordinate().get(i),
                        this.game.getSnake().getY_coordinate().get(i),Main.STEP, Main.STEP);
                g2d.setColor(DEFAULT_SNAKE_COLOR);
                g2d.fill(e);
            }
            else {
                Rectangle2D.Double r = new Rectangle2D.Double(this.game.getSnake().getX_coordinate().get(i),
                        this.game.getSnake().getY_coordinate().get(i),Main.STEP, Main.STEP);
                g2d.setColor(DEFAULT_SNAKE_COLOR);
                g2d.fill(r);
            }

        }
        // Print food
        for (int i = 0; i < this.game.getFoods().size(); i++) {
            Ellipse2D.Double e = new Ellipse2D.Double(this.game.getFoods().get(i).getX_coordinate(),
                    this.game.getFoods().get(i).getY_coordinate(), Main.STEP, Main.STEP);
            g2d.setColor(DEFAULT_FOOD_COLOR);
            g2d.fill(e);
        }
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        // Move forward
        if (isSnakeEatingItself() || isSnakeMovingOut()){
            if (this.game.getTries() > 0)
            {
                // Retry and decrease tries
                int tries = this.game.getTries();
                this.game = new Game();
                JOptionPane.showMessageDialog(Main.f, "You have " + tries + " more tries");
                this.game.setTries(tries - 1);
            }
            else {
                // Game Over
                this.timer.stop();
                JOptionPane.showMessageDialog(Main.f, "GAME OVER!");
                Main.f = new JFrame();
                Main.main(null);
            }
        }
        int eating_index = isSnakeEating();
        if (eating_index != -1) {
            this.game.getSnake().growAndMoveOneStep();
            this.game.getFoods().remove(eating_index);
            this.game.addRandomFood();
        }
        else {
            this.game.getSnake().moveOneStep();
        }
        repaint();
    }

    public int isSnakeEating(){
        for (int i = 0; i < this.game.getFoods().size(); i++) {
            if (this.game.getFoods().elementAt(i).getX_coordinate() - Main.STEP < this.game.getSnake().getX_coordinate().get(0)
            && this.game.getFoods().elementAt(i).getX_coordinate() + Main.STEP > this.game.getSnake().getX_coordinate().get(0)
            && this.game.getFoods().elementAt(i).getY_coordinate() - Main.STEP < this.game.getSnake().getY_coordinate().get(0)
            && this.game.getFoods().elementAt(i).getY_coordinate() + Main.STEP > this.game.getSnake().getY_coordinate().get(0))
                return i;
        }
        return -1;
    }

    public boolean isSnakeEatingItself(){
        for (int i = 1; i < this.game.getSnake().getSize(); i++) {
            if (this.game.getSnake().getX_coordinate().get(i) - Main.STEP < this.game.getSnake().getX_coordinate().get(0)
                    && this.game.getSnake().getX_coordinate().get(i) + Main.STEP > this.game.getSnake().getX_coordinate().get(0)
                    && this.game.getSnake().getY_coordinate().get(i) - Main.STEP < this.game.getSnake().getY_coordinate().get(0)
                    && this.game.getSnake().getY_coordinate().get(i) + Main.STEP > this.game.getSnake().getY_coordinate().get(0))
                return true;
        }
        return false;
    }

    public boolean isSnakeMovingOut(){
        return !(0 < this.game.getSnake().getX_coordinate().get(0)
                && Main.DEFAULT_WIDTH - Main.STEP > this.game.getSnake().getX_coordinate().get(0)
                && 0 < this.game.getSnake().getY_coordinate().get(0)
                && Main.DEFAULT_HEIGHT - 2*Main.STEP > this.game.getSnake().getY_coordinate().get(0));
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        int code = keyEvent.getKeyCode();
        if(code == KeyEvent.VK_RIGHT){
            if (this.game.getSnake().getDirection() != Direction.LEFT)
                this.game.getSnake().setDirection(Direction.RIGHT);
        }
        if(code == KeyEvent.VK_LEFT){
            if (this.game.getSnake().getDirection() != Direction.RIGHT)
                this.game.getSnake().setDirection(Direction.LEFT);
        }
        if(code == KeyEvent.VK_UP){
            if (this.game.getSnake().getDirection() != Direction.DOWN)
                this.game.getSnake().setDirection(Direction.UP);
        }
        if(code == KeyEvent.VK_DOWN){
            if (this.game.getSnake().getDirection() != Direction.UP)
                this.game.getSnake().setDirection(Direction.DOWN);
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }

}
