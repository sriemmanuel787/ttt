import java.util.*;

public class TTT {
    // Properties
    private static final int SIZE = 3;
    public static final char[] marks = { 'X', 'O', '+', '-' };
    Object[] players = new Object[2];
    private int activePlayer = 0;
    private char[][][] state;
    private boolean turn;
    private boolean gameOn;
    private String num;

    // Class constructor
    public TTT() {
        Scanner scn = new Scanner(System.in);
        this.turn = true;
        this.gameOn = true;
        this.state = new char[SIZE][SIZE][SIZE];
        char t = '@';
        /*
         * for (int i = 0; i < SIZE; i++) {
         * for (int j = 0; j < SIZE; j++) {
         * for (int k = 0; k < SIZE; k++) {
         * state[i][j][k] = t;
         * t++;
         * }
         * }
         * }
         */
        while (gameOn) {
            draw();
            move();
            gameOn = !checkWin();
        }
    }

    // Methods
    public void draw() {
        System.out.println();
        for (char[][] board : state) {
            for (char[] rows : board) {
                for (char x : rows) {
                    if (x == 0) {
                        System.out.print("[ ]");
                    } else {
                        System.out.print("[" + x + "]");
                    }
                }
                System.out.println();
            }
            System.out.println();
        }
        System.out.println();
    }

    public boolean move() {
        Scanner makeMove = new Scanner(System.in);
        System.out.println("Gimme x y z");
        int x = makeMove.nextInt();
        int y = makeMove.nextInt();
        int z = makeMove.nextInt();

        if (validate(x, y, z)) {
            state[x][y][z] = activatePlayer();
            return true;
        } else
            return false;

    }

    public boolean validate(int x, int y, int z) {
        return (state[x][y][z] == 0 && x < SIZE && y < SIZE && z < SIZE);
    }

    private char activatePlayer() {
        activePlayer++;
        activePlayer %= Math.min(players.length, marks.length);

        return marks[activePlayer];
    }

    private char isSame(char[] x) {
        for (int i = 0; i < x.length; i++) {
            if (x[i] == 0 || x[0] != x[i])
                return 0;
        }
        return x[0];
    }

    public char[][][] zRotate(int iterate) {
        if (iterate == 0)
            return state;

        char[][][] stateRotate = new char[SIZE][SIZE][SIZE];
        char[][][] tempRotate = new char[SIZE][SIZE][SIZE];
        for (int i = 0; i < stateRotate.length; i++) {
            for (int j = 0; j < stateRotate.length; j++) {
                for (int k = 0; k < stateRotate.length; k++) {
                    tempRotate[i][j][k] = state[i][j][k];
                }
            }
        }

        for (int x = 0; x < iterate; x++) {
            for (int i = 0; i < stateRotate.length; i++) {
                for (int j = 0; j < stateRotate.length; j++) {
                    for (int k = 0; k < stateRotate.length; k++) {
                        stateRotate[i][j][k] = tempRotate[i][k][SIZE - j - 1];
                    }
                }
            }
            for (int i = 0; i < stateRotate.length; i++) {
                for (int j = 0; j < stateRotate.length; j++) {
                    for (int k = 0; k < stateRotate.length; k++) {
                        tempRotate[i][j][k] = stateRotate[i][j][k];
                    }
                }
            }
        }
        return stateRotate;
    }

    public char[][][] yRotate(int iterate) {
        if (iterate == 0)
            return state;

        char[][][] stateRotate = new char[SIZE][SIZE][SIZE];
        char[][][] tempRotate = new char[SIZE][SIZE][SIZE];
        for (int i = 0; i < stateRotate.length; i++) {
            for (int j = 0; j < stateRotate.length; j++) {
                for (int k = 0; k < stateRotate.length; k++) {
                    tempRotate[i][j][k] = state[i][j][k];
                }
            }
        }

        for (int x = 0; x < iterate; x++) {
            for (int i = 0; i < stateRotate.length; i++) {
                for (int j = 0; j < stateRotate.length; j++) {
                    for (int k = 0; k < stateRotate.length; k++) {
                        stateRotate[i][j][k] = tempRotate[k][SIZE - i - 1][j];
                    }
                }
            }
            for (int i = 0; i < stateRotate.length; i++) {
                for (int j = 0; j < stateRotate.length; j++) {
                    for (int k = 0; k < stateRotate.length; k++) {
                        tempRotate[i][j][k] = stateRotate[i][j][k];
                    }
                }
            }
        }
        return stateRotate;
    }

    private boolean checkWin() {
        char[] vals = new char[SIZE];

        // Multi-board diagonals
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < vals.length; j++) {
                vals[j] = zRotate(i)[j][j][j];
            }
            // System.out.println("Cross-board " + (i + 1) + ": " + Arrays.toString(vals));
            if (isSame(vals) != 0) {
                System.out.println(marks[activePlayer] + " wins!");
                return true;
            }

        }

        // Single board diagonals
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < SIZE; j++) {
                for (int k = 0; k < SIZE; k++) {
                    vals[k] = zRotate(i)[k][k][j];
                }
                // System.out.println("Diagonal " + (i + 1) + ": " + Arrays.toString(vals));
                if (isSame(vals) != 0) {
                    System.out.println(marks[activePlayer] + " wins!");
                    return true;
                }
            }
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < SIZE; j++) {
                for (int k = 0; k < SIZE; k++) {
                    vals[k] = yRotate(i)[k][k][j];
                }
                // System.out.println("Diagonal " + (i + 5) + ": " + Arrays.toString(vals));
                if (isSame(vals) != 0) {
                    System.out.println(marks[activePlayer] + " wins!");
                    return true;
                }
            }
        }

        // Columns
        for (int l = 0; l < 2; l++) {
            for (int i = 0; i < vals.length; i++) {
                for (int j = 0; j < vals.length; j++) {
                    for (int k = 0; k < vals.length; k++) {
                        vals[k] = zRotate(l)[i][j][k];
                    }
                    // System.out.println("Column " + (j + 1) + ": " + Arrays.toString(vals));
                    if (isSame(vals) != 0) {
                        System.out.println(marks[activePlayer] + " wins!");
                        return true;
                    }
                }
            }
        }

        for (int l = 0; l < 2; l++) {
            for (int i = 0; i < vals.length; i++) {
                for (int j = 0; j < vals.length; j++) {
                    for (int k = 0; k < vals.length; k++) {
                        vals[k] = yRotate(l)[i][j][k];
                    }
                    // System.out.println("Column " + (j + 5) + ": " + Arrays.toString(vals));
                    if (isSame(vals) != 0) {
                        System.out.println(marks[activePlayer] + " wins!");
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "look at how cool i am!!! :)";
    }
}
