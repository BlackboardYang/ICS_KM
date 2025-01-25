package com.snakeio.demo;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/game")
public class SnakeGameController {
    private SnakeGameState gameState = new SnakeGameState();

    // 获取游戏状态
    @GetMapping("/state")
    public Map<String, Object> getState() {
        Map<String, Object> response = new HashMap<>();
        response.put("snake", gameState.getSnake());
        response.put("food", gameState.getFood());
        response.put("isGameOver", gameState.isGameOver());
        return response;
    }

    // 更新方向
    @PostMapping("/direction")
    public void updateDirection(@RequestBody Map<String, String> body) {
        String newDirection = body.get("direction");
        gameState.updateDirection(newDirection);
    }

    // 更新蛇的位置（可选自动移动端点）
    @PostMapping("/move")
    public void moveSnake() {
        gameState.move();
    }
}