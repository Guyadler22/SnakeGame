package backend;

import frontend.Main;

import java.util.Vector;

public class Game {
    private static final int DEFAULT_TRIES = 2;
    private static final int MAX_NUM_OF_FOODS = 3;

    private Snake snake;
    private int tries;
    private Vector<Food> foods;

    public Game() {
        this.tries = DEFAULT_TRIES;
        this.snake = new Snake();
        this.foods = new Vector<>();
        // Add random food in the screen
        this.addRandomFood();
    }

    public int getTries() {
        return tries;
    }

    public void setTries(int tries) {
        this.tries = tries;
    }

    public Vector<Food> getFoods() {
        return foods;
    }

    public void setFoods(Vector<Food> foods) {
        this.foods = foods;
    }

    public Snake getSnake() {
        return snake;
    }

    public void setSnake(Snake snake) {
        this.snake = snake;
    }

    public void addRandomFood(){
        this.foods.add(new Food((Main.DEFAULT_HEIGHT - 2 * Main.STEP) * Math.random() ,
                (Main.DEFAULT_WIDTH - 2 * Main.STEP) * Math.random()));
    }

    public void removeFood(Food food){
        this.foods.remove(food);
    }
}
