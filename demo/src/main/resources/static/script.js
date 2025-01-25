const canvas = document.getElementById("gameCanvas");
const ctx = canvas.getContext("2d");
const startButton = document.getElementById("startButton");
const scoreElement = document.getElementById("score"); // 新增：分数显示元素

let snake = [{ x: 10, y: 10 }];
let food = { x: 15, y: 15 };
let direction = { x: 0, y: -1 };
let gridSize = 20;
let gameRunning = false; // 游戏是否开始的标志
let score = 0; // 新增：分数变量

function drawGame() {
    ctx.clearRect(0, 0, canvas.width, canvas.height);

    // Draw snake
    snake.forEach(segment => {
        ctx.fillStyle = "green";
        ctx.fillRect(segment.x * gridSize, segment.y * gridSize, gridSize, gridSize);
    });

    // Draw food
    ctx.fillStyle = "red";
    ctx.fillRect(food.x * gridSize, food.y * gridSize, gridSize, gridSize);
}

function moveSnake() {
    const head = { x: snake[0].x + direction.x, y: snake[0].y + direction.y };

    // Check collision with walls
    if (head.x < 0 || head.x >= canvas.width / gridSize || head.y < 0 || head.y >= canvas.height / gridSize) {
        alert("Game Over");
        resetGame();
        return;
    }

    // Check collision with itself
    if (snake.some(segment => segment.x === head.x && segment.y === head.y)) {
        alert("Game Over");
        resetGame();
        return;
    }

    // Check if food is eaten
    if (head.x === food.x && head.y === food.y) {
        // 更新分数
        score += 1;
        scoreElement.textContent = score; // 更新 HTML 中的分数显示

        // 生成新食物
        food = {
            x: Math.floor(Math.random() * (canvas.width / gridSize)),
            y: Math.floor(Math.random() * (canvas.height / gridSize))
        };
    } else {
        snake.pop();
    }

    snake.unshift(head);
}

function gameLoop() {
    if (gameRunning) {
        moveSnake();
        drawGame();
        setTimeout(gameLoop, 200);
    }
}

function resetGame() {
    gameRunning = false;
    direction = { x: 0, y: -1 };
    snake = [{ x: 10, y: 10 }];
    food = { x: 15, y: 15 };
    score = 0; // 重置分数
    scoreElement.textContent = score; // 重置分数显示
    startButton.style.display = "block"; // 重新显示按钮
}

startButton.addEventListener("click", () => {
    gameRunning = true;
    startButton.style.display = "none"; // 隐藏按钮
    gameLoop();
});

document.addEventListener("keydown", (e) => {
    if (e.key === "ArrowUp" && direction.y === 0) direction = { x: 0, y: -1 };
    if (e.key === "ArrowDown" && direction.y === 0) direction = { x: 0, y: 1 };
    if (e.key === "ArrowLeft" && direction.x === 0) direction = { x: -1, y: 0 };
    if (e.key === "ArrowRight" && direction.x === 0) direction = { x: 1, y: 0 };
});