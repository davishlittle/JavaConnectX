package cpsc2150.extendedConnectX.models;

/**
 * <p>This class, GameBoard, keeps track of the game board
 * itself.</p>
 *
 * @author Davis Little
 * @version 1.0
 *
 * @Correspondences self = board
 *                  num_rows = numRows
 *                  num_columns = numColumns
 *                  num_to_win = numToWin
 *
 * @Invariants      [char in board contains empty space or player character] AND
 *                  MIN_COLUMNS {@code <=} numColumns {@code <=} MAX_COLUMNS AND
 *                  MIN_ROWS {@code <=}  numRows {@code <=} MAX_ROWS AND
 *                  MIN_NUM_TO_WIN {@code <=} numToWin {@code <=} MAX_NUM_TO_WIN
 *
 *
 */
public class GameBoard extends AbsGameBoard implements IGameBoard {

    private final int numRows;
    private final int numColumns;
    private final int numToWin;
    private char board[][];

    /**
     * Constructor for the class. Is empty.
     *
     * @param r number of rows
     * @param c number of columns
     * @param w number to win the game
     *
     * @post [Each char in board contains an empty space character] AND
     *          numRows = r AND
     *          numColumns = c AND
     *          numToWin = w
     */
    public GameBoard(int r, int c, int w) {
        numRows = r;
        numColumns = c;
        numToWin = w;
        board = new char[numRows][numColumns];
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                board[i][j] = ' ';
            }
        }
    }

    public int getNumRows() {
        return numRows;
    }

    public int getNumColumns() {
        return numColumns;
    }

    public int getNumToWin() {
        return numToWin;
    }

    public void placeToken(char p, int c) {
        for (int i = 0; i < numRows; i++) {
            if (board[i][c] == ' ') {
                board[i][c] = p;
                return;
            }
        }
    }

    public char whatsAtPos(BoardPosition pos) {
        return board[pos.getRow()][pos.getColumn()];
    }
}
