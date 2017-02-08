package com.example.behnam.myapplication;

import android.content.Context;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.security.InvalidParameterException;
import java.util.ArrayList;

/**
 * Created by behnam on 2/7/17.
 */



public class GameBoard {

    // game state constants
    private static int IS_PLAYING = 0;
    private static int X_WON = 1;
    private static int O_WON = 2;
    private static int DRAW = 3;

    //tile state constants
    private static int X = 100;
    private static int O = 101;
    private static int EMPTY = 102;

    private static String X_TEXT = "X";
    private static String O_TEXT = "O";
    private static String EMPTY_TEXT = "";

    private ArrayList<Button> mButtonList;

    private int mGameState;


    private int mCurrentPlayer;

    private Context mContext;

    private TextView mTextView;


    public GameBoard(Context context, ArrayList<Button> buttonList, TextView tvResult) {
        mContext = context;
        mButtonList = buttonList;
        mTextView = tvResult;

        initGame();

    }

    public void initGame(){
        setGameState(IS_PLAYING);
        setCurrentPlayer(X);

        enableUI();
        clearUI();
    }

    // no one should be allowed to change past moves!
    public boolean isLegalMove(int buttonIndex) {
        if (getButtonState(buttonIndex) == EMPTY) {
            return true;
        } else
            return false;
    }

    private void updateUI(int buttonIndex) {
        //Log.d("tag", "update UI" + buttonIndex);
        setButtonState(buttonIndex, stateToText(mCurrentPlayer));
    }


    /**
     * Here we check to see if the game had a winner, and then update the gb state accordingly
     *
     * **/
    private void determineGbState() {
        if (    //check rows
                (getButtonState(1) == getButtonState(2) && getButtonState(2) == getButtonState(3)
                        && getButtonState(1) != EMPTY)

                        || (getButtonState(4) == getButtonState(5) && getButtonState(5) == getButtonState(6)
                        && getButtonState(4) != EMPTY)

                        || (getButtonState(7) == getButtonState(8) && getButtonState(8) == getButtonState(9)
                        && getButtonState(7) != EMPTY)

                        //check columns
                        || (getButtonState(1) == getButtonState(4) && getButtonState(4) == getButtonState(7)
                        && getButtonState(1) != EMPTY)

                        || (getButtonState(2) == getButtonState(5) && getButtonState(5) == getButtonState(8)
                        && getButtonState(2) != EMPTY)

                        || (getButtonState(3) == getButtonState(6) && getButtonState(6) == getButtonState(9)
                        && getButtonState(3) != EMPTY)


                        //check diagonals
                        || (getButtonState(1) == getButtonState(5) && getButtonState(5) == getButtonState(9)
                        && getButtonState(1) != EMPTY)
                        || (getButtonState(3) == getButtonState(5) && getButtonState(5) == getButtonState(7)
                        && getButtonState(3) != EMPTY)
                ) {
            if (mCurrentPlayer == X) {
                setGameState(X_WON);
            } else if (mCurrentPlayer == O) {
                setGameState(O_WON);
            }
        }
        else if (isBoardFull()) {
            setGameState(DRAW);
        } else setGameState(IS_PLAYING);

    }

    private boolean gameOver() {
        return !isPlaying();
    }

    private void showResults() {
        if (mGameState == X_WON) {
            mTextView.setText("Player X wins!");
        } else if (mGameState == O_WON) {
            mTextView.setText("Player O wins!");
        } else if(mGameState == DRAW) {
            mTextView.setText("Draw!");
        }
    }
    
    public void restartGame() {
        initGame();
    }

    private void clearUI(){
        for (Button button :
                mButtonList) {
            button.setText(EMPTY_TEXT);
        }

    }

    private void disableUI() {
        for (Button button :
                mButtonList) {
            button.setEnabled(false);
        }
    }

    private void enableUI() {
        for (Button button:
                mButtonList) {
            button.setEnabled(true);
        }
    }


    private boolean isBoardFull() {
        for (Button button : mButtonList
                ) {
            if (button.getText().equals(EMPTY_TEXT)) {
                return false;
            }
        }

        return true;
    }



    private String stateToText(int state) {
        try {

            if (X == state) {
                return X_TEXT;
            } else if (O == state) {
                return O_TEXT;
            } else if (EMPTY == state) {
                return EMPTY_TEXT;
            }

        } catch (InvalidParameterException e) {
            Log.d("GameBoard", "state is neither X, O, or EMPTY");
        }
        return null;
    }

        private int textToState(String text) {
        try {


            if (text.equals(X_TEXT)) {
                return X;
            } else if (text.equals(O_TEXT)) {
                return O;
            } else if (text.equals(EMPTY_TEXT)) {
                return EMPTY;
            }

        } catch (InvalidParameterException e) {
            Log.d("GameBoard", "state is neither X, O, or EMPTY");
        }
        return 0;
    }



    //note the index decrement
    private void setButtonState(int index, String text) {
        mButtonList.get(index-1).setText(text);

    }

    //note that the index should be decremented
    private int getButtonState(int index) {
        return textToState(mButtonList.get(index-1).getText().toString());
    }


    public int getGameState() {
        return mGameState;
    }

    public void setGameState(int gameState) {
        mGameState = gameState;
    }

    public int getCurrentPlayer() {
        return mCurrentPlayer;
    }

    public void setCurrentPlayer(int currentPlayer) {
        mCurrentPlayer = currentPlayer;
//        Log.d("LOG", "setCurrentPlayer: " + mCurrentPlayer);
        mTextView.setText("Current player: " + stateToText(mCurrentPlayer));
    }


    private void flipCurrentPlayer() {
        if (mCurrentPlayer == X) {
            setCurrentPlayer(O);
        } else if (mCurrentPlayer == O) {
            setCurrentPlayer(X);
        }
    }

    private boolean isPlaying(){
        if (getGameState() == IS_PLAYING) {
//            Log.d(TAG, "isPlaying returns true");
            return true;
        }
        return false;

    }


    public void playMove(int buttonIndex) {

        if (isLegalMove(buttonIndex)) {
            updateUI(buttonIndex);
            determineGbState();
            if (gameOver()) {
                showResults();
                disableUI();
            } else {
                flipCurrentPlayer();
            }

        } else {
            Toast.makeText(mContext, "Illegal move: " + (buttonIndex),
                    Toast.LENGTH_SHORT).show();

        }
    }


}
