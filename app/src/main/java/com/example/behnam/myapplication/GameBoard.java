package com.example.behnam.myapplication;

import android.content.Context;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.security.InvalidParameterException;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;

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

    public int getCurrentPlayer() {
        return mCurrentPlayer;
    }

    public void setCurrentPlayer(int currentPlayer) {
        mCurrentPlayer = currentPlayer;
        Log.d("LOG", "setCurrentPlayer: " + mCurrentPlayer);
        mTextView.setText("Current player: " + stateToText(mCurrentPlayer));
    }

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


    public void playMove(int buttonIndex) {

        //if the game is over, we need a restart and should not play the move
        if (gameOver()) {
            return;
        }
        if (isLegal(buttonIndex)) {
            updateUI(buttonIndex);
            determineGbState();
            if (gameOver()) {
                disableUI();
            } else {
                flipCurrentPlayer();
            }

        } else {
            Toast.makeText(mContext, "Illegal move: " + (buttonIndex + 1),
                    Toast.LENGTH_SHORT).show();

        }
    }

    private boolean gameOver() {
        return !isPlaying();
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

    /**
 * Here we check to see if the game had a winner, and then update the gb state accordingly
 *
 * **/
    private void determineGbState() {
        if (    //check rows
                   (getButtonState(0) == getButtonState(1) && getButtonState(1) == getButtonState(2)
                           && getButtonState(0) != EMPTY)
                || (getButtonState(3) == getButtonState(4) && getButtonState(4) == getButtonState(5)
                           && getButtonState(3) != EMPTY)
                || (getButtonState(6) == getButtonState(7) && getButtonState(7) == getButtonState(8)
                           && getButtonState(6) != EMPTY)

                //check columns
                || (getButtonState(0) == getButtonState(3) && getButtonState(3) == getButtonState(6)
                           && getButtonState(0) != EMPTY)
                || (getButtonState(1) == getButtonState(4) && getButtonState(4) == getButtonState(7)
                           && getButtonState(1) != EMPTY)
                || (getButtonState(2) == getButtonState(5) && getButtonState(5) == getButtonState(8)
                           && getButtonState(2) != EMPTY)


                //check diagonals
                || (getButtonState(0) == getButtonState(4) && getButtonState(4) == getButtonState(8)
                           && getButtonState(0) != EMPTY)
                || (getButtonState(2) == getButtonState(4) && getButtonState(4) == getButtonState(6)
                           && getButtonState(2) != EMPTY)
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

    private boolean isBoardFull() {
        for (Button button : mButtonList
                ) {
            if (button.getText().equals(EMPTY_TEXT)) {
                return false;
            }
        }

        return true;
    }

    private void updateUI(int buttonIndex) {
        Log.d("tag", "update UI" + buttonIndex);
        mButtonList.get(buttonIndex).setText(stateToText(mCurrentPlayer));
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



    public boolean isLegal(int buttonIndex) {
        Button clickedButton = mButtonList.get(buttonIndex);
        if (clickedButton.getText().equals(EMPTY_TEXT)) {
            return true;
        } else
            return false;
    }

    private int getButtonState(int index) {
        return textToState(mButtonList.get(index).getText().toString());
    }


    public int getGameState() {
        return mGameState;
    }

    public void setGameState(int gameState) {
        mGameState = gameState;

        if (mGameState == X_WON) {
            mTextView.setText("Player X wins!");
        } else if (mGameState == O_WON) {
            mTextView.setText("Player O wins!");
        } else if(mGameState == DRAW) {
            mTextView.setText("Draw!");
        }
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
            Log.d(TAG, "isPlaying returns true");
            return true;
        }
        return false;

    }

    public void restartGame() {
        initGame();
    }
}
