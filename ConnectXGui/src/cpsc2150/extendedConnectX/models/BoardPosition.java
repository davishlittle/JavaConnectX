package cpsc2150.extendedConnectX.models;

/**
 * <p>This class, BoardPosition, keeps track of an individual
 * cell for the board.</p>
 *
 * @author Davis Little
 * @version 1.0
 *
 */
public class BoardPosition {

    private final int row;
    private final int column;

    /**
     * Constructor for the BoardPosition class. Sets row and
     * column variables.
     *
     * @param r row
     * @param c column
     *
     * @post
     *          row = r AND column = c
     */
    public BoardPosition(int r, int c) {
        row = r;
        column = c;
    }

    /**
     * Returns the row variable
     *
     * @return row
     *
     * @post
     *          row = #row AND column = #column AND
     *          getRow = row
     */
    public int getRow() {
        return row;
    }

    /**
     * Returns the column variable
     *
     * @return column
     *
     * @post
     *          row = #row AND column = #column AND
     *          getColumn = column
     */
    public int getColumn() {
        return column;
    }
    
    /**
     * Overrides the toString method to create string in correct format
     *
     * @return String in form "row,column"
     *
     * @post
     *          row = #row AND column = #column AND
     *          toString = [String in form "row,column"]
     */
    @Override
    public String toString() {
        String returnString = "";

        returnString += row;
        returnString += ",";
        returnString += column;

        return returnString;
    }

    /**
     * Overrides the equals method to compare a BoardPosition object with self
     *
     * @param o object to compare
     *
     * @return true iff [BoardPositions are equal], false otherwise
     *
     * @post
     *          row = #row AND column = #column AND
     *          equals = true iff [this row and column] =
     *          [input BoardPosition row and column]
     */
    @Override
    public boolean equals(Object o) {
        if (o.getClass() != getClass()) {
            return false;
        }
        BoardPosition pos = (BoardPosition)o;
        return (pos.getRow() == row && pos.getColumn() == column);
    }
}
