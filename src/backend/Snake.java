package backend;

import frontend.Main;

import java.util.Vector;

public class Snake {

    public static final int DEFAULT_SIZE = 3;
    private static final Direction DEFAULT_DIRECTION = Direction.UP;

    private int size;
    private Direction direction;
    private Vector<Double> x_coordinate;
    private Vector<Double> y_coordinate;


    public Snake() {
        this.size = DEFAULT_SIZE;
        this.direction = DEFAULT_DIRECTION;
        this.x_coordinate = new Vector<>();
        this.y_coordinate = new Vector<>();

        // Default location of the snake
        for (int i = 0; i < DEFAULT_SIZE; i++) {
            this.x_coordinate.add(Main.DEFAULT_WIDTH * 0.5);
            this.y_coordinate.add(Main.DEFAULT_HEIGHT * 0.5 + Main.STEP * i);
        }
    }

    public Vector<Double> getX_coordinate() {
        return x_coordinate;
    }

    public void setX_coordinate(Vector<Double> x_coordinate) {
        this.x_coordinate = x_coordinate;
    }

    public Vector<Double> getY_coordinate() {
        return y_coordinate;
    }

    public void setY_coordinate(Vector<Double> y_coordinate) {
        this.y_coordinate = y_coordinate;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void moveOneStep() {
        // Move one Main.STEP
        Double temp;
        for (int i = this.size - 1; i > 0; i--) {
            this.y_coordinate.set(i, this.y_coordinate.get(i - 1));
            this.x_coordinate.set(i, this.x_coordinate.get(i - 1));
        }
        switch (this.direction) {
            case UP:
                this.y_coordinate.set(0, this.y_coordinate.get(0) - Main.STEP);
                break;
            case DOWN:
                this.y_coordinate.set(0, this.y_coordinate.get(0) + Main.STEP);
                break;
            case LEFT:
                this.x_coordinate.set(0, this.x_coordinate.get(0) - Main.STEP);
                break;
            case RIGHT:
                this.x_coordinate.set(0, this.x_coordinate.get(0) + Main.STEP);
                break;
        }
    }

    public void growAndMoveOneStep() {
        // Move one Main.STEP
        this.y_coordinate.add(this.y_coordinate.get(this.size - 1));
        this.x_coordinate.add(this.x_coordinate.get(this.size - 1));

        this.moveOneStep();

        // Grow
        this.size++;
    }

}

