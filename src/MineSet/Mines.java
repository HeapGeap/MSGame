package MineSet;
import java.util.Random;
import java.util.Scanner;
public class Mines {

        private int height /* rows */, width /* columns */, numMines, totalToReveal /* excluding mines */;
        public String[][] board;
        private Random rand = new Random(); // Generate random numbers
        private int h, w; // Assist with random received values
        public static int winLose=-1;

        // @SuppressWarnings("resource")
        public Mines(int height, int width, int numMines) {
             int newMines = -1;
             boolean checkMinesNum = false; // If number of mines is legal
             Scanner sc = new Scanner(System.in);
            this.height = height;
            this.width = width;

              if (numMines < width * height && numMines >= 0) checkMinesNum = true; while
              (checkMinesNum != true) {
              System.out.println("Please enter a valid number of mines (lower than " +
              height * width + "):"); newMines = sc.nextInt(); if (newMines < width *
              height && newMines >= 0) { checkMinesNum = true; numMines = newMines; } } //


            this.numMines = numMines;
            totalToReveal = (height * width) - this.numMines;
            if(height<30||width<30) {
                board = new String[height][width]; // Create game's board with nulls initialized
            }
            for (int i = 0; i < height; i++) // Initialize all slots in the board
                for (int j = 0; j < width; j++)
                    board[i][j] = "NF."; // Nothing ON slot, False none is revealed yet and . as default
            while (numMines != 0) {
                h = rand.nextInt(height - 1); // Generate random number for height
                w = rand.nextInt(width - 1); // Generate random number for width
                if (board[h][w].charAt(2) != 'M') { // Make sure place wasn't used before
                    board[h][w] = board[h][w].substring(0, 2) + "M"; // Mark a place containing a mine with M
                    numMines--;
                }
            }
            for (int i = 0; i < height; i++) // Initialize slot values
                for (int j = 0; j < width; j++)
                    board[i][j] = board[i][j].substring(0, 2) + get(i, j);
        }

        public int getCol() {
            return width;
        }

        public int getRow() {
            return height;
        }

        private void checkPlace(int i, int j) { // Is it a legal move? (borders)
            if (i >= height || j >= width || i < 0 || j < 0)
                throw new IllegalArgumentException("Illegal move on board!");
        }

        private boolean inBoard(int i, int j) { // Is move legal? (make move or not)
            if (i >= height || j >= width || i < 0 || j < 0)
                return false;
            return true;
        }

        public boolean addMine(int i, int j) {
            checkPlace(i, j);
            if (board[i][j].contains("M")) {
                System.out.println("Adding a mine has failed, slot already contains one!");
                return false;
            }
            board[i][j] = board[i][j].substring(0, 2) + "M";
            numMines++; // Mine was added
            totalToReveal--; // Subtract one slot (occupied by mine)
            for (int r = 0; r < height; r++) // Initialize slot values
                for (int c = 0; c < width; c++)
                    board[r][c] = board[r][c].substring(0, 2) + get(r, c);
            return true;
        }

        public boolean open(int i, int j) {
            checkPlace(i, j);
            if (board[i][j].charAt(1) == 'T') // Already revealed
                return true;
            if (board[i][j].charAt(2) == 'M') { // Slot contains a mine (user lost)
                board[i][j] = board[i][j].substring(0, 1) + "T" + "B"; // B for boom
                totalToReveal = 0; // Finish game
                isDone();
                System.out.println("Game over, boy");
                winLose=0;
                return true;
            }
            if (board[i][j].charAt(2) != 'E' && board[i][j].charAt(2) != 'M' && board[i][j].charAt(2) != 'B') {
                board[i][j] = board[i][j].substring(0, 1) + "T" + board[i][j].substring(2, 3);
                totalToReveal--;
                if (isDone()) {
                    System.out.println("You win, boy");
                    winLose=1;
                }
                return true;
            }
            if (board[i][j].charAt(2) == 'E') {
                board[i][j] = board[i][j].substring(0, 1) + "T" + board[i][j].substring(2, 3);
                totalToReveal--;
                for (int r = -1; r < 2; r++) {
                    for (int c = -1; c < 2; c++) {
                        if (!(r == 0 && c == 0) && inBoard(i + r, j + c)) {
                            if (isDone()) {
                                System.out.println("You win, boy");
                                winLose=1;
                            }
                            open(i + r, j + c);
                        }
                    }
                }
                return true;
            }
            return false;
        }

        public void toggleFlag(int x, int y) {
            checkPlace(x, y);
            if (board[x][y].contains("T"))
                return;
            board[x][y] = "D" + board[x][y].substring(1, 3); // Overwrite it with flag mark (D)
        }

        public void toggleQM(int x, int y) {
            checkPlace(x, y);
            if (board[x][y].contains("T"))
                return;
            board[x][y] = "?" + board[x][y].substring(1, 3); // Overwrite it with question mark (?)
        }

        public boolean isDone() {
            if (totalToReveal != 0)
                return false;
            setShowAll();
            return true; // True if no more slots to reveal (won the game)
        }

        public String get(int i, int j) {
            checkPlace(i, j);
            Integer countMines = 0; // To use the object's toString method
            if (board[i][j].contains("F")) { // Slot not revealed
                if (board[i][j].contains("D")) // Slot has flag
                    return "F"; // F for flag
                else if (board[i][j].contains("?")) // Slot is marked as question mark (unsure of slot's content)
                    return "?";
            } // Remaining slots are revealed
            if (board[i][j].contains("M"))
                return "M";
            else {
                for (int r = -1; r < 2; r++) {
                    for (int c = -1; c < 2; c++) {
                        if (!(r == 0 && c == 0) && inBoard(i + r, j + c) && board[i + r][j + c].contains("M")) {
                            countMines++;
                            if (countMines > 8 || countMines < 0)
                                throw new IllegalArgumentException("Something went wrong - mines count is illegal");
                        }
                    }
                }
            }
            if (countMines == 0)
                return "E";
            else
                return countMines.toString();
        }

        public void setShowAll() { // Set all slots to be revealed when game is over (win\lose)
            for (int i = 0; i < height; i++)
                for (int j = 0; j < width; j++)
                    if (!board[i][j].contains("T"))
                        board[i][j] = board[i][j].substring(0, 1) + "T" + board[i][j].substring(2, 3);
        }

        @Override
        public String toString() {
            String s = "";
            for (int r = 0; r < height; r++) {
                for (int c = 0; c < width; c++) {
                    if (board[r][c].charAt(1) == 'T')
                        s += board[r][c].substring(2, 3);
                    else { // Meaning slot isn't revealed
                        if (board[r][c].contains("D") || board[r][c].contains("?"))
                            s += get(r, c);
                        else
                            s += "X";
                    }
                    if (c == width - 1) // New row
                        s += '\n';
                }
            }
            return s;
        }
    }
