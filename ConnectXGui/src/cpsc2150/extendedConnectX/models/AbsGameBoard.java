package cpsc2150.extendedConnectX.models;

/**
 * Abstract class for toString override
 *
 * @author Davis Little
 * @version 1.0
 */
public abstract class AbsGameBoard implements IGameBoard{

    /**
     * Overrides the toString method to create string in correct format
     *
     * @return String that shows entire game board
     * @post self = #self AND
     *       toString = [game board content in string format]
     */
    @Override
    public String toString() {
        String returnString = "|";

        for (int i = 0; i < getNumColumns(); i++) {
            returnString += i;
            returnString += "|";
        }

        returnString += "\n";

        for (int i = getNumRows() - 1; i >= 0; i--) {
            for (int j = 0; j < getNumColumns(); j++) {
                returnString += "|";
                returnString += whatsAtPos(new BoardPosition(i, j));
            }
            returnString += "|\n";
        }

        return returnString;
    }
}
