package com.snakeio.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SnakeGameState {
    private List<int[]> snake; // Snake body, each element is [x, y]
    private int[] food; // Food position [x, y]
    private String direction; // Current direction: UP, DOWN, LEFT, RIGHT
    private boolean isGameOver;

    private static final int GRID_SIZE = 20; // Grid size for the game

    public SnakeGameState() {
        this.snake = new ArrayList<>();
        this.snake.add(new int[]{10, 10}); // Initialize snake in the center of the grid
        this.food = generateFood(); // Generate initial food
        this.direction = "UP"; // Default starting direction
        this.isGameOver = false;
    }

    public void updateDirection(String newDirection) {
        // Prevent reversing direction
        if ((direction.equals("UP") && newDirection.equals("DOWN")) ||
            (direction.equals("DOWN") && newDirection.equals("UP")) ||
            (direction.equals("LEFT") && newDirection.equals("RIGHT")) ||
            (direction.equals("RIGHT") && newDirection.equals("LEFT"))) {
            return;
        }
        this.direction = newDirection;
    }

    public void move() {
        if (isGameOver) return;

        int[] head = snake.get(0);
        int[] newHead = switch (direction) {
            case "UP" -> new int[]{head[0], head[1] - 1};
            case "DOWN" -> new int[]{head[0], head[1] + 1};
            case "LEFT" -> new int[]{head[0] - 1, head[1]};
            case "RIGHT" -> new int[]{head[0] + 1, head[1]};
            default -> head;
        };

        // Check collision with walls
        if (newHead[0] < 0 || newHead[0] >= GRID_SIZE || newHead[1] < 0 || newHead[1] >= GRID_SIZE) {
            isGameOver = true;
            return;
        }

        // Check collision with itself
        for (int i = 1; i < snake.size(); i++) {
            if (Arrays.equals(snake.get(i), newHead)) {
                isGameOver = true;
                return;
            }
        }

        // Add new head
        snake.add(0, newHead);

        // Check if food is eaten
        if (Arrays.equals(newHead, food)) {
            food = generateFood();
        } else {
            // Remove tail
            snake.remove(snake.size() - 1);
        }
    }

    private int[] generateFood() {
    Random random = new Random();
    final int[] foodPosition = new int[2]; // Use a single-element container
    do {
        foodPosition[0] = random.nextInt(GRID_SIZE); // Random x within grid
        foodPosition[1] = random.nextInt(GRID_SIZE); // Random y within grid
    } while (snake.stream().anyMatch(segment -> Arrays.equals(segment, foodPosition)));
    return foodPosition;
}

    // Getters
    public List<int[]> getSnake() {
        return snake;
    }

    public int[] getFood() {
        return food;
    }

    public boolean isGameOver() {
        return isGameOver;
    }
}