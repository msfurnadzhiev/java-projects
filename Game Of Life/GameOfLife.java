/*
Game Rules:
1.Any live cell with fewer than two live neighbours dies, as if by underpopulation.
2.Any live cell with two or three live neighbours lives on to the next generation.
3.Any live cell with more than three live neighbours dies, as if by overpopulation.
4.Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.
*/

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class GameOfLife {
    public static void main(String[] args) throws InterruptedException {
        int N = 30, M=100;
        int[][] grid = new int[N][M];


        Scanner input = new Scanner(System.in);
        System.out.print("Start points: ");
        int startPoints = input.nextInt();

        for(int i=0; i<startPoints; i++) {
            System.out.print("x: ");
            int x = input.nextInt();
            System.out.print("y: ");
            int y = input.nextInt();
            grid[x][y] = 1;
        }

        clearConsole();

        while(true) {
            System.out.println();
            printGrid(grid,N,M);
            TimeUnit.SECONDS.sleep(1);
            nextGeneration(grid,N,M);
        }
    }

    static void printGrid(int[][] grid, int N, int M) {
        for(int i=0; i<N; i++) {
            for(int j=0; j<M; j++) {
                if(grid[i][j] == 1) {
                    System.out.print("#");
                }
                else {
                    System.out.print(" ");
                }

            }
            System.out.println();
        }
    }

    static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    static void nextGeneration(int[][] grid, int N, int M) {

        int[][] next = new int[N][M];

        for(int n = 1; n < N-1; n++) {
            for (int m = 1; m < M-1; m++) {

                int aliveNeighbours = 0;
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        aliveNeighbours += grid[n + i][m + j];
                    }
                }

                aliveNeighbours -= grid[n][m];

                // Cell is lonely and dies
                if ((grid[n][m] == 1) && (aliveNeighbours < 2)) {
                    next[n][m] = 0;
                }

                // Cell dies due to over population
                else if ((grid[n][m] == 1) && (aliveNeighbours > 3)) {
                    next[n][m] = 0;
                }

                // A new cell is born
                else if ((grid[n][m] == 0) && (aliveNeighbours == 3)) {
                    next[n][m] = 1;
                }

                else {  next[n][m] = grid[n][m]; }
            }
        }

        for(int i=1; i < N; i++) {
            for (int j = 1; j < M; j++) {
                grid[i][j] = next[i][j];
            }
        }
    }
}

