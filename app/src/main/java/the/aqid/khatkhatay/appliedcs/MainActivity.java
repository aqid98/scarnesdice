package the.aqid.khatkhatay.appliedcs;



import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;
import java.lang.*;


import android.util.Log;


public class MainActivity extends AppCompatActivity {
    ImageView imageView;
    TextView userscore, computerscore, tscore;
    private int oUser=0, oComp=0, cUser=0, cComp = 0;
    Button roll, hold, reset;
    private Random random = new Random();
    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {

        @Override
        public void run() {
            long millis = System.currentTimeMillis();
            int seconds = (int) (millis / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;
            timerHandler.postDelayed(this, 500);
        }
    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        roll = findViewById(R.id.roll);
        hold = findViewById(R.id.hold);
        reset = findViewById(R.id.reset);
        imageView = findViewById(R.id.imageView);
        userscore = findViewById(R.id.userscore);
        computerscore = findViewById(R.id.computerscore);
        tscore = findViewById(R.id.tscore);




        roll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rollDice(1);
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View view) {
                                         cComp = 0;
                                         cUser = 0;
                                         oComp = 0;
                                         oUser = 0;
                                         userscore.setText("Your Score: "+oUser);
                                         computerscore.setText("Computer Score: "+cComp);
                                         tscore.setText("Turn Score: 0");

                                     }
                                 }
        );
        hold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hold(1);
                computerTurn();
            }
        });
    }

    private void hold(int turn) {
        if(turn==1)
        {
            oUser=oUser+cUser;
            tscore.setText("Turn Score "+cUser);
            cUser=0;
            userscore.setText("Your Score "+oUser);

        }
        else
        {
            oComp=cComp+oComp;
            tscore.setText("Turn Score "+cComp);
            cComp=0;
            computerscore.setText("Computer Score: "+oComp);
        }
        if (oUser >100) {
            Toast.makeText(this, "YOU WIN", Toast.LENGTH_SHORT).show();

        }
        if (oComp > 100) {
            Toast.makeText(this, "COMPUTER WIN", Toast.LENGTH_SHORT).show();

        }


    }


    private int rollDice(int turn) {
        Random rand = new Random();
        int num = rand.nextInt(6) + 1;
        switch (num) {
            case 1:
                imageView.setImageResource(R.drawable.dice1);
                break;
            case 2:
                imageView.setImageResource(R.drawable.dice2);
                break;
            case 3:
                imageView.setImageResource(R.drawable.dice3);
                break;
            case 4:
                imageView.setImageResource(R.drawable.dice4);
                break;
            case 5:
                imageView.setImageResource(R.drawable.dice5);
                break;
            case 6:
                imageView.setImageResource(R.drawable.dice6);
                break;
        }
        if (num != 1) {
            if (turn == 1) {
                cUser = num + cUser;
                tscore.setText("Turn Score: " + cUser);
            } else {
                cComp = num + cComp;
                tscore.setText("Turn score: " + cComp);
            }
            Log.d("SCORE", "User rolled " + num);

        } else {
            if (turn == 1) {
                cUser = 0;
                hold(1);
                computerTurn();
            } else {
                cComp = 0;
                hold(2);

            }
            Log.d("SCORE", "Comp rolled " + num);
        }


        return num;
    }


    private void computerTurn() {
        roll.setEnabled(false);
        hold.setEnabled(false);
            if(rollDice(2)!=1&&cComp<20)
            {
                new Thread(){
                    @Override
                    public void run() {
                        try {
                            sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                computerTurn();
                            }
                        });

                    }
                }.start();
            }
            else
            {
                hold(2);
                roll.setEnabled(true);
                hold.setEnabled(true);
            }
        }



    }








