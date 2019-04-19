package com.example.poker101;

import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.poker101.date.Comanda;
import com.example.poker101.date.Pariu;
import com.example.poker101.date.Tura;

import org.json.JSONException;

import java.io.IOException;

public class GameActivity extends AppCompatActivity {
    public static TextView chips_total_text;
    public static TextView chips_played_text;
    public static TextView opponent_chips_played_text;
    public static TextView raise_text;
    public static TextView turn_action;
    public static TextView turn_player;
    public static ImageView card1, card2;
    public static ImageView dealerCard1, dealerCard2, dealerCard3, dealerCard4, dealerCard5, opponentCard1, opponentCard2;

    public static Button btn_check;
    public static Button btn_fold;
    public static Button btn_call;
    public static Button btn_raise;
    public static Button raise_increment;
    public static Button raise_decrement;
    public static String last_action = "";
    public static int tura_curenta = 0;

    public static void endTurn() {
        User.yourTurn = false;
        btn_call.setEnabled(false);
        btn_raise.setEnabled(false);
        btn_fold.setEnabled(false);
        btn_check.setEnabled(false);
        turn_player.setText(R.string.opponent_turn);
    }

    public static void updateGame(Tura tura) {
        turn_action.setText(("Opponent " + tura.opponent_action));
        User.yourTurn = true;
        turn_player.setText(R.string.your_turn);
        Resources res = User.context.getResources();
        if (tura.tura_curenta != GameActivity.tura_curenta) {
            GameActivity.tura_curenta = tura.tura_curenta;
            GameActivity.last_action = "";
            btn_call.setEnabled(true);
            btn_raise.setEnabled(true);
            btn_check.setEnabled(true);
            btn_fold.setEnabled(true);
            for (int i = 0; i < tura.carti.size(); i++) {
                if (tura.carti.get(i) != null) {
                    String cardName = "_" + tura.carti.get(i).getNumar() + tura.carti.get(i).getTip() + "";
                    int resID1 = res.getIdentifier(cardName, "drawable", User.context.getPackageName());
                    if (i == 0) {
                        dealerCard1.setImageResource(resID1);
                    } else if (i == 1) {
                        dealerCard2.setImageResource(resID1);
                    } else if (i == 2) {
                        dealerCard3.setImageResource(resID1);
                    } else if (i == 3) {
                        dealerCard4.setImageResource(resID1);
                    } else if (i == 4) {
                        dealerCard5.setImageResource(resID1);
                    }
                } else {
                    break;
                }
            }
        }
        if (GameActivity.last_action.equals("raise") && tura.opponent_action.equals("raised")) {
            btn_raise.setEnabled(false);
        } else {
            btn_raise.setEnabled(true);
        }
        if (!tura.opponent_action.equals("raise") && !tura.opponent_action.equals("call")) {
            btn_check.setEnabled(true);
        } else {
            btn_check.setEnabled(false);
        }
        btn_call.setEnabled(true);
        btn_raise.setEnabled(true);
        btn_fold.setEnabled(true);
        opponent_chips_played_text.setText(Integer.toString(tura.opponent_bani));
        int pariati1 = Integer.parseInt(chips_played_text.getText().toString());
        int total = Integer.parseInt(chips_total_text.getText().toString());
        int pariati2 = Integer.parseInt(chips_played_text.getText().toString());
        if ((total - (pariati2 - pariati1)) < 0) {
            btn_call.setText(R.string.all_in);
        }
        if (tura.opponent_carte1 != null) {

            String card1Name = "_" + tura.opponent_carte1.getNumar() + tura.opponent_carte1.getTip() + "";
            int resID1 = res.getIdentifier(card1Name, "drawable", User.context.getPackageName());
            opponentCard1.setImageResource(resID1);

            String card2Name = "_" + tura.opponent_carte2.getNumar() + tura.opponent_carte2.getTip() + "";
            int resID2 = res.getIdentifier(card2Name, "drawable", User.context.getPackageName());
            opponentCard2.setImageResource(resID2);

            try {
                if (tura.winner.equals(User.user.getString("id"))) {
                    turn_action.setText(R.string.you_win);
                } else if (tura.winner.equals(User.currentOpponent)) {
                    turn_action.setText(R.string.opponent_win);
                } else {
                    turn_action.setText(R.string.draw);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        chips_total_text = findViewById(R.id.chips_total_text);
        chips_total_text.setText(Integer.toString(User.player_info.bani_totali));

        chips_played_text = findViewById(R.id.chips_played_text);
        chips_played_text.setText("0");

        opponent_chips_played_text = findViewById(R.id.opponent_chips_played_text);
        opponent_chips_played_text.setText("0");

        raise_text = findViewById(R.id.raise_text);
        raise_text.setText("1");

        turn_action = findViewById(R.id.turn_action);
        turn_action.setText("");

        turn_player = findViewById(R.id.turn_player);
        if (User.yourTurn) {
            turn_player.setText(R.string.your_turn);
        } else {
            turn_player.setText(R.string.opponent_turn);
        }

        Resources res = getResources();

        card1 = findViewById(R.id.player_card1);
        String card1Name = "_" + User.player_info.carte1.getNumar() + User.player_info.carte1.getTip() + "";
        int resID1 = res.getIdentifier(card1Name, "drawable", getPackageName());
        card1.setImageResource(resID1);

        card2 = findViewById(R.id.player_card2);
        String card2Name = "_" + User.player_info.carte2.getNumar() + User.player_info.carte2.getTip() + "";
        int resID2 = res.getIdentifier(card2Name, "drawable", getPackageName());
        card2.setImageResource(resID2);

        dealerCard1 = findViewById(R.id.dealer_card1);
        dealerCard2 = findViewById(R.id.dealer_card2);
        dealerCard3 = findViewById(R.id.dealer_card3);
        dealerCard4 = findViewById(R.id.dealer_card4);
        dealerCard5 = findViewById(R.id.dealer_card5);
        opponentCard1 = findViewById(R.id.opponent_card1);
        opponentCard2 = findViewById(R.id.opponent_card2);

        if (User.card_id == 0) {
            dealerCard1.setImageResource(R.drawable.card_back_default);
            dealerCard2.setImageResource(R.drawable.card_back_default);
            dealerCard3.setImageResource(R.drawable.card_back_default);
            dealerCard4.setImageResource(R.drawable.card_back_default);
            dealerCard5.setImageResource(R.drawable.card_back_default);
            opponentCard1.setImageResource(R.drawable.card_back_default);
            opponentCard2.setImageResource(R.drawable.card_back_default);
        } else if (User.card_id == 1) {
            dealerCard1.setImageResource(R.drawable.card_back1);
            dealerCard2.setImageResource(R.drawable.card_back1);
            dealerCard3.setImageResource(R.drawable.card_back1);
            dealerCard4.setImageResource(R.drawable.card_back1);
            dealerCard5.setImageResource(R.drawable.card_back1);
            opponentCard1.setImageResource(R.drawable.card_back1);
            opponentCard2.setImageResource(R.drawable.card_back1);
        } else if (User.card_id == 2) {
            dealerCard1.setImageResource(R.drawable.card_back2);
            dealerCard2.setImageResource(R.drawable.card_back2);
            dealerCard3.setImageResource(R.drawable.card_back2);
            dealerCard4.setImageResource(R.drawable.card_back2);
            dealerCard5.setImageResource(R.drawable.card_back2);
            opponentCard1.setImageResource(R.drawable.card_back2);
            opponentCard2.setImageResource(R.drawable.card_back2);
        }

        btn_check = findViewById(R.id.btn_check);
        btn_call = findViewById(R.id.btn_call);
        btn_raise = findViewById(R.id.btn_raise);
        btn_fold = findViewById(R.id.btn_fold);
        raise_increment = findViewById(R.id.raise_increment);
        raise_decrement = findViewById(R.id.raise_decrement);

        if (!User.yourTurn) {
            btn_check.setEnabled(false);
            btn_call.setEnabled(false);
            btn_raise.setEnabled(false);
            btn_fold.setEnabled(false);
        }

        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    sendCommand(new Comanda("check", User.user.getString("id")));
                    turn_action.setText(R.string.you_checked);
                    last_action = "check";
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        btn_fold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    sendCommand(new Comanda("fold", User.user.getString("id")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        btn_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    int pariati1 = Integer.parseInt(chips_played_text.getText().toString());
                    chips_played_text.setText(opponent_chips_played_text.getText());
                    int total = Integer.parseInt(chips_total_text.getText().toString());
                    int pariati2 = Integer.parseInt(chips_played_text.getText().toString());
                    if ((total - (pariati2 - pariati1)) < 0) {
                        chips_total_text.setText("0");
                    } else {
                        chips_total_text.setText(Integer.toString(total - (pariati2 - pariati1)));
                    }
                    sendCommand(new Comanda("call", User.user.getString("id")));
                    User.player_info.bani_pariati = Integer.parseInt(chips_played_text.getText().toString());
                    User.player_info.bani_totali = Integer.parseInt(chips_total_text.getText().toString());
                    turn_action.setText(R.string.you_called);
                    last_action = "call";
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        btn_raise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    int pariati1 = Integer.parseInt(chips_played_text.getText().toString());
                    int total = Integer.parseInt(chips_total_text.getText().toString());
                    int pariati2 = Integer.parseInt(opponent_chips_played_text.getText().toString());
                    int raised = Integer.parseInt(raise_text.getText().toString());
                    if (raised + (pariati2 - pariati1) > total) {
                        Toast.makeText(getApplicationContext(), "Insufficient funds!", Toast.LENGTH_LONG).show();
                        return;
                    }
                    chips_total_text.setText(Integer.toString(total - (pariati2 - pariati1) - raised));
                    chips_played_text.setText(Integer.toString(pariati2 + raised));
                    Pariu pariu = new Pariu();
                    pariu.id_facebook = User.user.getString("id");
                    pariu.bani = raised + (pariati2 - pariati1);
                    sendCommand(new Comanda("raise", pariu));
                    User.player_info.bani_pariati = Integer.parseInt(chips_played_text.getText().toString());
                    User.player_info.bani_totali = Integer.parseInt(chips_total_text.getText().toString());
                    turn_action.setText(R.string.you_raised);
                    last_action = "raise";
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        raise_increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int value = Integer.parseInt(raise_text.getText().toString());
                int total = Integer.parseInt(chips_total_text.getText().toString());
                if (!(value * 2 > total)) {
                    value *= 2;
                    raise_text.setText(Integer.toString(value));
                }
            }
        });

        raise_decrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int value = Integer.parseInt(raise_text.getText().toString());
                if (value / 2 >= 1) {
                    value /= 2;
                    raise_text.setText(Integer.toString(value));
                }
            }
        });
    }

    void sendCommand(final Comanda comanda) {
        endTurn();
        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
                try {
                    User.oos.writeObject(comanda);

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception exp) {
                    exp.printStackTrace();
                }

            }


        };
        Thread myThread = new Thread(myRunnable);
        myThread.start();
        try {
            myThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        User.context = getApplicationContext();
        User.getMessage=true;
    }

    @Override
    protected void onPause() {
        User.getMessage=false;
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}
