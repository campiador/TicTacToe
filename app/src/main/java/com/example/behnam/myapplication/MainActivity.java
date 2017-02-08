package com.example.behnam.myapplication;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.view.View.*;

public class MainActivity extends AppCompatActivity {

    private Button b1;
    private Button b2;
    private Button b3;
    private Button b4;
    private Button b5;
    private Button b6;
    private Button b7;
    private Button b8;
    private Button b9;

    private Button bRestart;

    private OnClickListener mListener;

    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1 = (Button) findViewById(R.id.button1);
        b2 = (Button) findViewById(R.id.button2);
        b3 = (Button) findViewById(R.id.button3);
        b4 = (Button) findViewById(R.id.button4);
        b5 = (Button) findViewById(R.id.button5);
        b6 = (Button) findViewById(R.id.button6);
        b7 = (Button) findViewById(R.id.button7);
        b8 = (Button) findViewById(R.id.button8);
        b9 = (Button) findViewById(R.id.button9);

        bRestart = (Button) findViewById(R.id.button_restart);

        tvResult = (TextView) findViewById(R.id.textView);

        final ArrayList<Button> buttons = new ArrayList<>();
        buttons.add(b1);
        buttons.add(b2);
        buttons.add(b3);
        buttons.add(b4);
        buttons.add(b5);
        buttons.add(b6);
        buttons.add(b7);
        buttons.add(b8);
        buttons.add(b9);

        final GameBoard gb = new GameBoard(this, buttons, tvResult);

        mListener= new OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = -1;

                if (v.getId() == R.id.button_restart) {
                    gb.restartGame();
                    return;
                }

                switch (v.getId()) {
                    case R.id.button1:
                        index = 1;

                        break;

                    case R.id.button2:
                        index = 2;
                        break;

                    case R.id.button3:
                        index = 3;
                        break;

                    case R.id.button4:
                        index = 4;

                        break;

                    case R.id.button5:
                        index = 5;

                        break;

                    case R.id.button6:
                        index = 6;

                        break;

                    case R.id.button7:
                        index = 7;

                        break;

                    case R.id.button8:
                        index = 8;

                        break;

                    case R.id.button9:
                        index = 9;

                        break;

                }

                gb.playMove(index);
            }
        };

        b1.setOnClickListener(mListener);
        b2.setOnClickListener(mListener);
        b3.setOnClickListener(mListener);
        b4.setOnClickListener(mListener);
        b5.setOnClickListener(mListener);
        b6.setOnClickListener(mListener);
        b7.setOnClickListener(mListener);
        b8.setOnClickListener(mListener);
        b9.setOnClickListener(mListener);

        bRestart.setOnClickListener(mListener);

    }
}
