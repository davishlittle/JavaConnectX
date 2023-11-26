package cpsc2150.extendedConnectX.controllers;

import cpsc2150.extendedConnectX.models.*;
import cpsc2150.extendedConnectX.views.*;

/**
 * The controller class will handle communication between our View and our Model ({@link IGameBoard})
 * <p>
 * This is where you will write code
 * <p>
 * You will need to include your your {@link BoardPosition} class, {@link IGameBoard} interface
 * and both of the {@link IGameBoard} implementations from Project 4.
 * If your code was correct you will not need to make any changes to your {@link IGameBoard} implementation class.
 *
 * @version 2.0
 */
public class ConnectXController {

    /**
     * <p>
     * The current game that is being played
     * </p>
     */
    private IGameBoard curGame;

    /**
     * <p>
     * The screen that provides our view
     * </p>
     */
    private ConnectXView screen;

    /**
     * <p>
     * Keeps track if the game has been won or tied
     * </p>
     */
    private boolean endGame;

    /**
     * <p>
     * Keeps track of which players turn it is
     * </p>
     */
    private int turn;

    /**
     * <p>
     * Constant for the maximum number of players.
     * </p>
     */
    public static final int MAX_PLAYERS = 10;
    
    /**
     * <p>
     * The number of players for this game. Note that our player tokens are hard coded.
     * </p>
     */
    private final int numPlayers;

    /**
     * <p>
     * Hard coded player tokens for each player. Maximum of 10
     * </p>
     */
    private final char[] players = {'X', 'O', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'};

    /**
     * <p>
     * This creates a controller for running the Extended ConnectX game
     * </p>
     * 
     * @param model
     *      The board implementation
     * @param view
     *      The screen that is shown
     * @param np
     *      The number of players for this game.
     * 
     * @post [ the controller will respond to actions on the view using the model. ]
     */
    public ConnectXController(IGameBoard model, ConnectXView view, int np) {
        this.curGame = model;
        this.screen = view;
        this.numPlayers = np;

        endGame = false;
    }

    /**
     * <p>
     * This processes a button click from the view.
     * </p>
     * 
     * @param col 
     *      The column of the activated button
     * 
     * @post [ will allow the player to place a token in the column if it is not full, otherwise it will display an error
     * and allow them to pick again. Will check for a win as well. If a player wins it will allow for them to play another
     * game hitting any button ]
     */
    public void processButtonClick(int col) {
        //If the game is won or tied reset
        if (endGame) {
            turn = 0;
            endGame = false;
            this.newGame();
            return;
        }

        //If the selected spot isn't valid, print an error and return
        if (!curGame.checkIfFree(col)) {
            screen.setMessage("The selected column is full. Pick a different one.");
            return;
        }

        //If it is valid, find the current row for use with setting the marker
        int row = 1;
        for (int i = 0; i < curGame.getNumRows(); i++) {
            BoardPosition pos = new BoardPosition(i, col);
            char currentSpace = curGame.whatsAtPos(pos);
            if (currentSpace == ' ') {
                row = pos.getRow();
                break;
            }
        }

        //Place the token and set the marker
        curGame.placeToken(players[turn], col);
        screen.setMarker(row, col, players[turn]);

        //Check for tie and win
        if (curGame.checkTie()) {
            screen.setMessage("Tie game! Press any button to start a new game.");
            endGame = true;
        }
        else if (curGame.checkForWin(col)) {
            screen.setMessage("Player " + players[turn] + " won! Press any button to start a new game.");
            endGame = true;
        }

        //Increment the current player, going back to player one if current player is the last
        if (turn + 1 == numPlayers) {
            turn = 0;
        }
        else {
            turn++;
        }

        //Change the current message to say the correct player's turn if game is not over
        if (!endGame) {
            screen.setMessage("It is " + players[turn] + "'s turn.");
        }
    }

    /**
     * <p>
     * This method will start a new game by returning to the setup screen and controller
     * </p>
     * 
     * @post [ a new game gets started ]
     */
    private void newGame() {
        //close the current screen
        screen.dispose();
        
        //start back at the set up menu
        SetupView screen = new SetupView();
        SetupController controller = new SetupController(screen);
        screen.registerObserver(controller);
    }
}