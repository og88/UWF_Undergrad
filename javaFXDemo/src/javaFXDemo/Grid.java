package javaFXDemo;

import javafx.scene.effect.Light.Point;
import javafx.scene.paint.Color;

import java.util.Random;

public class Grid {
	
public static final int SIZE = 10;
public static final Color COLOR = new Color(0.1, 0.1, 0.1, 0.1);

private final int cols; //The number of columns
private final int rows; //The number of rows

private Snake snake;
private Food food;

public Grid(final double width, final double height) {
	rows = (int) width / SIZE;
	cols = (int) height / SIZE;
	
	snake = new Snake(this, new javaFXDemo.Point(rows/2,cols/2));
	
	food = new Food(getRandomPoint());
}

public javaFXDemo.Point wrap(javaFXDemo.Point point2) {
int x = point2.getX();
int y = point2.getY();
if (x >= rows) x = 0;
if (y >= cols) y = 0;
if (x < 0) x = rows - 1;
if (y < 0) y = cols - 1;
return new javaFXDemo.Point(x, y);
}



private javaFXDemo.Point getRandomPoint() {
	Random random = new Random();
    javaFXDemo.Point point;
    do {
        point = new javaFXDemo.Point(random.nextInt(rows), random.nextInt(cols));
    } while (point.equals(snake.getHead()));
    return point;
}

/**
 * This method is called in every cycle of execution.
 */
public void update() {
    if (food.getPoint().equals(snake.getHead())) {
        snake.extend();
        food.setPoint(getRandomPoint());
    } else {
        snake.move();
    }
}

public int getCols() {
    return cols;
}

public int getRows() {
    return rows;
}

public double getWidth1() {
    return rows * SIZE;
}

public double getHeight() {
    return cols * SIZE;
}

public Snake getSnake() {
    return snake;
}

public javaFXDemo.Food getFood() {
    return food;
}


public double getWidth() {
	// TODO Auto-generated method stub
    return rows * SIZE;
}


}
