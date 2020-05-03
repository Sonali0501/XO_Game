package com.example.lionortiger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    enum Player{

        ONE,TWO,NO;
    }

    Player currentPLayer = Player.ONE;

    Player[] playerChoices = new Player[9];

    int[][] winnerRowsColumns = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};

    private int c=0;
    private boolean gameOver= false;

    private Button btnReset;
    private TextView Player1 , Player2;
    private GridLayout mGridLayout;
    private ImageView imageView;
    private ImageView turnImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        settingToNo();
        turnImg=findViewById(R.id.turnImg);
        turnImg.setImageResource(R.drawable.lion);

        Player1=findViewById(R.id.Player1);
        Player2=findViewById(R.id.Player2);
        btnReset= findViewById(R.id.btnReset);
        mGridLayout=findViewById(R.id.gridLayout);

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                resetTheGame();
            }
        });
    }
    public void imageIsClicked(View imageView) {

        ImageView tappedImageView = (ImageView) imageView;
        int tiTag = Integer.parseInt(tappedImageView.getTag().toString());

        if (playerChoices[tiTag] == Player.NO && gameOver == false) {

            tappedImageView.setTranslationX(-1000);
            anim(tappedImageView);
            playerChoices[tiTag] = currentPLayer;

            if (currentPLayer == Player.ONE) {

                tappedImageView.setImageResource(R.drawable.lion);
                currentPLayer = Player.TWO;
                turnImg.setImageResource(R.drawable.tiger);
            }
            else if (currentPLayer == Player.TWO) {

                tappedImageView.setImageResource(R.drawable.tiger);
                currentPLayer = Player.ONE;
                turnImg.setImageResource(R.drawable.lion);
            }
            winner();

        }
    }

    private void resetTheGame(){

        for (int index=0; index < mGridLayout.getChildCount(); index++) {

            ImageView imageView = (ImageView) mGridLayout.getChildAt(index);
            imageView.setImageDrawable(null);
            imageView.setAlpha(0.4f);
        }

            currentPLayer = Player.ONE;
            settingToNo();
            turnImg.setImageResource(R.drawable.lion);
            turnImg.setVisibility(View.VISIBLE);
            Player1.setBackgroundColor(Color.parseColor("#D0EDF1"));
            Player2.setBackgroundColor(Color.parseColor("#D0EDF1"));
            gameOver = false;
            btnReset.setVisibility(View.INVISIBLE);
    }

    private void settingToNo(){

        for (int i=0; i<playerChoices.length; i++) {

            playerChoices[i] = Player.NO;
        }
    }

    private void anim(ImageView x){

        x.animate().translationXBy(1000).alpha(1).setDuration(1000);
    }

    private void winner(){
        String Winner="";
        c++;
        for (int[] winnerColumns : winnerRowsColumns) {

            if (playerChoices[winnerColumns[0]] == playerChoices[winnerColumns[1]]
                    && playerChoices[winnerColumns[1]] == playerChoices[winnerColumns[2]]
                    && playerChoices[winnerColumns[0]] != Player.NO ) {

                btnReset.setVisibility(View.VISIBLE);
                gameOver = true;
                turnImg.setVisibility(View.INVISIBLE);

                if (currentPLayer == Player.ONE) {

                    Winner = "Tiger";
                    Player2.setBackgroundColor(Color.parseColor("#1E90FF"));
                    Player1.setBackgroundColor(Color.parseColor("#FF6347"));
                }
                else if (currentPLayer == Player.TWO) {

                    Winner = "Lion";
                    Player1.setBackgroundColor(Color.parseColor("#1E90FF"));
                    Player2.setBackgroundColor(Color.parseColor("#FF6347"));
                }
                c=0;
                Toast.makeText(this, Winner + " is the Winner", Toast.LENGTH_SHORT).show();
            }
        }
        if (Winner.equals("") && c==9) {

            btnReset.setVisibility(View.VISIBLE);
            gameOver = true;
            turnImg.setVisibility(View.INVISIBLE);
            Winner = "Nobody";
            c=0;
            Toast.makeText(this, Winner + " is the Winner", Toast.LENGTH_SHORT).show();
        }
    }

}
