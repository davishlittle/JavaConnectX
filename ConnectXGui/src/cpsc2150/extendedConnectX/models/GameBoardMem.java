package cpsc2150.extendedConnectX.models;

import java.util.*;

/**
 * Memory efficient version of the game
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
 */
public class GameBoardMem extends AbsGameBoard implements IGameBoard{
    private Map<Character, List<BoardPosition>> board = new HashMap<Character, List<BoardPosition>>();

    private final int numRows;
    private final int numColumns;
    private final int numToWin;

    /**
     * Constructor creates a new board and initializes the variables
     *
     * @param r number of rows
     * @param c number of columns
     * @param w number in a row to win
     *
     * @post numRows = r AND
     *       numColumns = c AND
     *       numToWin = w
     */
    public GameBoardMem(int r, int c, int w) {
        numRows = r;
        numColumns = c;
        numToWin = w;
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
        if (!board.containsKey(p)) {
            board.put(p, new ArrayList<>());
        }
        for (int i = 0; i < numRows; i++) {
            if (whatsAtPos(new BoardPosition(i, c)) == ' ') {
                board.get(p).add(new BoardPosition(i, c));
                break;
            }
        }
    }

    public char whatsAtPos(BoardPosition pos) {
        for (HashMap.Entry<Character, List<BoardPosition>> map : board.entrySet()) {
            if (isPlayerAtPos(pos, map.getKey())) {
                return map.getKey();
            }
        }
        return ' ';
    }

    public boolean isPlayerAtPos(BoardPosition pos, char player) {
        if (!board.containsKey(player)) {
            return false;
        }
        for (BoardPosition current : board.get(player)) {
            if (current.equals(pos)) {
                return true;
            }
        }
        return false;
    }


}
