package cpsc2150.extendedConnectX.models;

/**
 * Interface for the GameBoard class.
 *
 * @author Davis Little
 * @version 1.0
 *
 * Initialization ensures: [All chars in self are empty spaces] AND
 *                         [self size = num_rows * num_columns]
 *
 * Defines:     num_rows: N - total number of rows
 *              num_columns: N - total number of columns
 *              num_to_win: N - total number in a row to win a game
 *
 * Constraints: No gaps between non-space tokens
 *
 */
public interface IGameBoard {
    public static final int MIN_ROWS = 3;
    public static final int MAX_ROWS = 100;
    public static final int MIN_COLUMNS = 3;
    public static final int MAX_COLUMNS = 100;
    public static final int MIN_NUM_TO_WIN = 3;
    public static final int MAX_NUM_TO_WIN = 25;
    public static final int MIN_PLAYERS = 2;
    public static final int MAX_PLAYERS = 10;

    /**
     * Returns the number of rows
     * @return num_rows
     *
     * @post [no changes to variables]
     *          getNumRows = num_rows
     */
    public int getNumRows();

    /**
     * Returns the number of columns
     * @return num_columns
     *
     * @post [no changes to variables]
     *          getNumColumns = num_columns
     */
    public int getNumColumns();

    /**
     * Returns the number in a row required to win
     * @return num_to_win
     *
     * @post [no changes to variables]
     *          getNumToWin = num_to_win
     */
    public int getNumToWin();

    /**
     * returns true if the column can accept another token; false
     * otherwise.
     *
     * @param c column
     *
     * @return true iff one token is empty out of self[0 through num_rows][c]
     *         ELSE false
     *
     * @pre
     *          c {@code >=} 0 AND c {@code <} MAX_COLUMNS
     *
     * @post
     *          self = #self
     *          checkIfFree iff
     *              [Blank space in column]
     */
    public default boolean checkIfFree(int c) {
        BoardPosition pos = null;
        for (int i = 0; i < getNumRows(); i++) {
            pos = new BoardPosition(i, c);
            if (whatsAtPos(pos) == ' ') {
                return true;
            }
        }
        return false;
    }

    /**
     * places the character p in column c. The token will be placed in
     * the lowest available row in column c.
     *
     * @param p character to place
     * @param c column to place it in
     *
     * @pre
     *          (c {@code >=} 0 AND c {@code <} MAX_COLUMNS) AND
     *          (checkIfFree(c) = true)
     *
     * @post
     *          self[next available][c] = p
     */
    public void placeToken(char p, int c);

    /**
     * this function will check to see if the last token placed in
     * column c resulted in the player winning the game. If so it will return
     * true, otherwise false.
     * Note: this is not checking the entire board for a win, it is
     * just checking if the last token placed results in a win.
     * You may call other methods to complete this method
     *
     * @param c column
     *
     * @return true iff (last token placed in c results in
     *         num_to_win in a row for a player), false otherwise
     *
     * @pre
     *          c {@code >=} 0 AND c {@code <} MAX_COLUMNS AND
     *          [c is column where the latest token is placed]
     *
     * @post
     *          self = #self AND
     *          checkForWin iff
     *              [c is the last token to make a row of
     *              num_to_win vertically, horizontally, or diagonally]
     */
    public default boolean checkForWin(int c) {
        BoardPosition pos = null;
        BoardPosition temp = null;

        boolean endFound = false;
        int end = 0;
        char player;

        //Find the end of the column
        for (int i = 0; i < getNumRows(); i++) {
            if (whatsAtPos(new BoardPosition(i, c)) == ' ') {
                if (i == 0) {
                    return false;
                }
                else {
                    endFound = true;
                    end = i;
                    break;
                }
            }
        }
        if (!endFound) {
            end = getNumRows();
        }

        pos = new BoardPosition(end - 1, c);

        //Set player character based on end
        player = whatsAtPos(pos);

        if (checkHorizWin(pos, player)) {
            return true;
        }
        else if (checkVertWin(pos, player)) {
            return true;
        }
        else return checkDiagWin(pos, player);
    }

    /**
     * this function will check to see if the game has resulted in a
     * tie. A game is tied if there are no free board positions remaining.
     * You do not need to check for any potential wins because we can assume
     * that the players were checking for win conditions as they played the
     * game. It will return true if the game is tied and false otherwise.
     *
     * @return true iff (no empty positions), false otherwise
     *
     * @pre
     *          [game doesn't have a winner yet]
     *
     * @post
     *          self = #self AND
     *          checkTie iff
     *              [no empty positions in self]
     */
    public default boolean checkTie() {
        for (int i = 0; i < getNumRows(); i++) {
            for (int j = 0; j < getNumColumns(); j++) {
                if (whatsAtPos(new BoardPosition(i, j)) == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * checks to see if the last token placed (which was placed in
     * position pos by player p) resulted in 5 in a row horizontally. Returns
     * true if it does, otherwise false
     *
     * @param pos position of token
     * @param p player character
     *
     * @return true iff (num_to_win in a row horizontally for player), otherwise false
     *
     * @pre
     *          0 {@code <=} pos.getRow() {@code <} MAX_ROWS AND 0 {@code <=} pos.getColumn() {@code <} MAX_COLUMNS AND
     *          [p is valid player] AND
     *          [pos is BoardPosition type] AND
     *          [pos is position on the latest play] AND
     *          [p is located at pos]
     *
     * @post
     *          self = #self AND
     *          checkHorizWin iff
     *              [pos is last in a row of num_to_win markers of the same type]
     */
    public default boolean checkHorizWin(BoardPosition pos, char p) {
        boolean rightSide = (pos.getColumn() == getNumColumns() - 1);
        boolean leftSide = pos.getColumn() == 0;
        boolean playerFound = true;
        int numFound = 1;

        //Check to the right
        for (int i = 1; i < getNumToWin() && playerFound; i++) {
            playerFound = false;

            if (pos.getColumn() + i == getNumColumns()) {
                rightSide = true;
            }
            if (rightSide) {
                break;
            }
            if (whatsAtPos(new BoardPosition(pos.getRow(), pos.getColumn() + i)) == p) {
                playerFound = true;
                ++numFound;
            }
        }

        //Check to the left
        playerFound = true;
        for (int i = 1; i < getNumToWin() && playerFound; i++) {
            playerFound = false;

            if (pos.getColumn() - i == -1) {
                leftSide = true;
            }
            if (leftSide) {
                break;
            }
            if (whatsAtPos(new BoardPosition(pos.getRow(), pos.getColumn() - i)) == p) {
                playerFound = true;
                ++numFound;
            }
        }
        return (numFound == getNumToWin());
    }

    /**
     * checks to see if the last token placed (which was placed in
     * position pos by player p) resulted in 5 in a row vertically. Returns
     * true if it does, otherwise false
     *
     * @param pos position of token
     * @param p player character
     *
     * @return true iff (5 in a row vertically for player), otherise false
     *
     * @pre
     *          0 {@code <=} pos.getRow() {@code <} MAX_ROWS AND 0 {@code <=} pos.getColumn() {@code <} MAX_COLUMNS AND
     *          [p is valid player] AND
     *          [pos is BoardPosition type] AND
     *          [pos is position on the latest play] AND
     *          [p is located at pos]
     *
     * @post
     *          self = #self AND
     *          checkVertWin iff
     *              [pos is last in a column of num_to_win markers of the same type]
     */
    public default boolean checkVertWin(BoardPosition pos, char p) {
        boolean top = (pos.getRow() == getNumRows() - 1);
        boolean bottom = pos.getRow() == 0;
        boolean playerFound = true;
        int numFound = 1;

        //Check above
        for (int i = 1; i < getNumToWin() && playerFound; i++) {
            playerFound = false;

            if (pos.getRow() + i == getNumRows()) {
                top = true;
            }
            if (top) {
                break;
            }
            if (whatsAtPos(new BoardPosition(pos.getRow() + i, pos.getColumn())) == p) {
                playerFound = true;
                ++numFound;
            }
        }

        //Check below
        playerFound = true;
        for (int i = 1; i < getNumToWin() && playerFound; i++) {
            playerFound = false;

            if (pos.getRow() - i == -1) {
                bottom = true;
            }
            if (bottom) {
                break;
            }
            if (whatsAtPos(new BoardPosition(pos.getRow() - i, pos.getColumn())) == p) {
                playerFound = true;
                ++numFound;
            }
        }

        return (numFound == getNumToWin());
    }

    /**
     * checks to see if the last token placed (which was placed in
     * position pos by player p) resulted in 5 in a row diagonally. Returns
     * true if it does, otherwise false
     * Note: there are two diagonals to check
     *
     * @param pos position of token
     * @param p player character
     *
     * @return true iff (5 in a row diagonally for player), otherwise false
     *
     * @pre
     *          0 {@code <=} pos.getRow() {@code <} MAX_ROWS AND 0 {@code <=} pos.getColumn() {@code <} MAX_COLUMNS AND
     *          [p is valid player] AND
     *          [pos is BoardPosition type] AND
     *          [pos is position on the latest play] AND
     *          [p is located at pos]
     *
     * @post
     *          self = #self AND
     *          checkDiagWin iff
     *              [pos is last in a row diagonally of num_to_win markers of the same type]
     */
    public default boolean checkDiagWin(BoardPosition pos, char p) {
        boolean rightSide = (pos.getColumn() == getNumColumns() - 1);
        boolean leftSide = pos.getColumn() == 0;
        boolean top = (pos.getRow() == getNumRows() - 1);
        boolean bottom = pos.getRow() == 0;
        boolean playerFound = true;
        int numFound = 1;

        //Check top right
        for (int i = 1; i < getNumToWin() && playerFound; i++) {
            playerFound = false;

            if (pos.getColumn() + i == getNumColumns()) {
                rightSide = true;
            }
            if (rightSide) {
                break;
            }
            if (pos.getRow() + i == getNumRows()) {
                top = true;
            }
            if (top) {
                break;
            }
            if (whatsAtPos(new BoardPosition(pos.getRow() + i, pos.getColumn() + i)) == p) {
                playerFound = true;
                ++numFound;
            }
        }

        //Check bottom left
        playerFound = true;
        for (int i = 1; i < getNumToWin() && playerFound; i++) {
            playerFound = false;

            if (pos.getColumn() - i == -1) {
                leftSide = true;
            }
            if (leftSide) {
                break;
            }
            if (pos.getRow() - i == -1) {
                bottom = true;
            }
            if (bottom) {
                break;
            }
            if (whatsAtPos(new BoardPosition(pos.getRow() - i, pos.getColumn() - i)) == p) {
                playerFound = true;
                ++numFound;
            }
        }

        //If number to win found return true
        if (numFound == getNumToWin()) {
            return true;
        }

        //Reset numFound
        numFound = 1;

        //Check top left
        playerFound = true;
        for (int i = 1; i < getNumToWin() && playerFound; i++) {
            playerFound = false;

            if (pos.getColumn() - i == -1) {
                leftSide = true;
            }
            if (leftSide) {
                break;
            }
            if (pos.getRow() + i == getNumRows()) {
                top = true;
            }
            if (top) {
                break;
            }
            if (whatsAtPos(new BoardPosition(pos.getRow() + i, pos.getColumn() - i)) == p) {
                playerFound = true;
                ++numFound;
            }
        }

        //Check bottom right
        playerFound = true;
        for (int i = 1; i < getNumToWin() && playerFound; i++) {
            playerFound = false;

            if (pos.getColumn() + i == getNumColumns()) {
                rightSide = true;
            }
            if (rightSide) {
                break;
            }
            if (pos.getRow() - i == -1) {
                bottom = true;
            }
            if (bottom) {
                break;
            }
            if (whatsAtPos(new BoardPosition(pos.getRow() - i, pos.getColumn() + i)) == p) {
                playerFound = true;
                ++numFound;
            }
        }

        return (numFound == getNumToWin());
    }

    /**
     * returns what is in the GameBoard at position pos
     * If no marker is there, it returns a blank space char.
     *
     * @param pos position of token
     *
     * @return char at current game board position
     *
     * @pre
     *          [pos is BoardPosition type] AND
     *          pos.getRow {@code >=} 0 AND pos.getRow {@code <} MAX_ROWS AND
     *          pos.getColumn {@code >=} 0 AND pos.getColumn {@code <} MAX_COLUMNS
     *
     * @post
     *          self = #self AND
     *          whatsAtPos = [char at pos]
     */
    public char whatsAtPos(BoardPosition pos);

    /**
     * returns true if the player is at pos; otherwise, it returns
     * false
     * Note: this method will be implemented very similarly to
     * whatsAtPos, but it's asking a different question. We only know they
     * will be similar because we know GameBoard will contain a 2D array. If
     * the data structure were to change in the future, these two methods
     * could be radically different.
     *
     * @param pos position of token
     * @param player player character
     *
     * @return true iff (self[pos.getRow()][pos.getColumn] = player)
     *
     * @pre
     *          [pos is BoardPosition type] AND
     *          0 {@code <=} pos.getRow {@code <} ROWS AND
     *          0 {@code <=} pos.getColumn {@code <} COLUMNS
     *
     * @post
     *          self = #self AND
     *          isPlayerAtPos iff
     *              [player is present at specified position]
     */
    public default boolean isPlayerAtPos(BoardPosition pos, char player) {
        return whatsAtPos(pos) == player;
    }
}
