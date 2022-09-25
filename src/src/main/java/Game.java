import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Game {
    static final String PLAYER_1 = "Player1";
    static final int MAX_PRIORITY = 3;
    static final String PLAYER_2 = "Player2";
    static Map<Integer, ArrayList<Integer>> usedPriorityMap = new HashMap<>(){{
        put(1, new ArrayList<>(List.of(1,1,2,2,3,3)));
        put(2, new ArrayList<>(List.of(1,1,2,2,3,3)));
    }};
    static String[][] board = new String[3][3];
    static final Map<Integer, Integer[]> gridMap = new HashMap<>(){{
        put(1, new Integer[]{0,0});
        put(2, new Integer[]{0,1});
        put(3, new Integer[]{0,2});
        put(4, new Integer[]{1,0});
        put(5, new Integer[]{1,1});
        put(6, new Integer[]{1,2});
        put(7, new Integer[]{2,0});
        put(8, new Integer[]{2,1});
        put(9, new Integer[]{2,2});
    }};
    static Map<Integer, CellStatus> statusMap = new HashMap<>();
    public static void main(String[] args) {
        // take input as grid number and priority box
        initialiseBoard(board);
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to the game!\nPlayer 1's turn");
        int currentPlayer = 1;
        while (true) {
            System.out.println("Available pieces: "+ usedPriorityMap.get(currentPlayer));
            int grid = sc.nextInt();
            int priority = sc.nextInt();
            CellStatus currentCellStatus = statusMap.getOrDefault(grid, new CellStatus(-1, -1, -1));

            if (validateAndUpdate(currentPlayer, currentCellStatus, grid, priority)) {

            } else {
                System.out.println("Validation failed, please try again");
                continue;
            }
            if(isGameOver(board, PLAYER_1) || isGameOver(board, PLAYER_2)) {
                System.out.println("++++++++++++++++++");
                System.out.println("+++ Game over! +++");
                System.out.println("++++++++++++++++++");
                return;
            }
            if(currentPlayer == 1)
                currentPlayer = 2;
            else
                currentPlayer = 1;
            System.out.println("Player"+currentPlayer+"'s turn");
        }
    }

    private static void initialiseBoard(String[][] board) {
        for (String[] strings : board) {
            Arrays.fill(strings, "***********");
        }
    }

    private static boolean isGameOver(String[][] board, String player) {
        // diagonal
        if(board[0][0].contains(player) && board[1][1].contains(player) && board[2][2].contains(player)){
            System.out.println("Left diagonal matched for "+ player);
            System.out.println(player+" wins!");
            return true;
        } else if (board[0][2].contains(player) && board[1][1].contains(player) && board[2][0].contains(player)) {
            System.out.println("Right diagonal matched for "+ player);
            System.out.println(player+" wins!");
            return true;
        }
        // rows
        for(int i=0; i<board.length; i++) {
            if(board[i][0].contains(player) && board[i][1].contains(player) && board[i][2].contains(player)){
                System.out.println("Row "+(i+1)+" matched for "+ player);
                System.out.println(player+" wins!");
                return true;
            }
        }
        // columns
        for(int i=0; i<board[0].length; i++) {
            if(board[0][i].contains(player) && board[1][i].contains(player) && board[2][i].contains(player)){
                System.out.println("Column "+(i+1)+" matched for "+ player);
                System.out.println(player+" wins!");
                return true;
            }
        }
        return false;
    }

    private static boolean validateAndUpdate(int currentPlayer, CellStatus currentCellStatus, int grid, int priority) {
        // Validations:
        // grid input is in bounds
        // current player cannot use the same priority he used before
        // input priority should be greater than current cell's priority

        if (grid > 0 && grid <= 9 &&
            priority <= MAX_PRIORITY && priority > 0 &&
            usedPriorityMap.get(currentPlayer).contains(priority) &&
            currentCellStatus.priority < priority) {

            usedPriorityMap.get(currentPlayer).remove(priority);
            currentCellStatus.priority = priority;
            currentCellStatus.player = currentPlayer;

            updateBoard(grid, currentCellStatus);
            return true;
        } else {
            System.out.println("currentCellStatus.player != currentPlayer => " + (currentCellStatus.player != currentPlayer));
            System.out.println("!usedPriorityMap.get(currentPlayer-1).contains(priority) => " + (!usedPriorityMap.get(currentPlayer).contains(priority)));
            System.out.println("currentCellStatus.priority < priority => " + (currentCellStatus.priority < priority));
        }
        return false;
    }

    private static void updateBoard(int grid, CellStatus currentCellStatus) {
        Integer[] index = gridMap.get(grid);
        board[index[0]][index[1]] = "Player"+currentCellStatus.player+" : "+ currentCellStatus.priority;
        System.out.println("Board status: ");
        System.out.print("|________________________________________|\n| ");
        for(String[] row: board) {
            Arrays.stream(row).forEach(x -> System.out.print(x+" ,"));
            System.out.print("|\n| ");
        }
        System.out.println("_______________________________________|\n");
        statusMap.put(grid, currentCellStatus);
    }
}
