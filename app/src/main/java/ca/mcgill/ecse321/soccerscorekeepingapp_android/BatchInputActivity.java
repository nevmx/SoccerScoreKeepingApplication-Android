package ca.mcgill.ecse321.soccerscorekeepingapp_android;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ca.mcgill.ecse321.soccerscorekeeping.controller.controller;
import ca.mcgill.ecse321.soccerscorekeeping.model.Manager;
import ca.mcgill.ecse321.soccerscorekeeping.model.Match;
import ca.mcgill.ecse321.soccerscorekeeping.model.Player;

public class BatchInputActivity extends AppCompatActivity {

    Match match;
    String[] teams;
    Object[] teamOnePlayers;
    Object[] teamTwoPlayers;

    String[] teamOnePlayerNames;
    String[] teamTwoPlayerNames;

    String team_inner;
    String player_inner;
    String[] players_inner;
    String card_color_inner;
    boolean penalty_kick_inner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batch_input);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        init();

        // Start the match
        controller c = new controller();
        Intent fromLast = getIntent();

        String teamOne = fromLast.getStringExtra(CreateMatchActivity.TEAM_ONE);
        String teamTwo = fromLast.getStringExtra(CreateMatchActivity.TEAM_TWO);

        teams = new String[2];
        teams[0] = teamOne;
        teams[1] = teamTwo;

        //Get player lists
        Manager m = Manager.getInstance();

        teamOnePlayers = m.getTeam(teams[0]).getPlayers().toArray();
        teamTwoPlayers = m.getTeam(teams[1]).getPlayers().toArray();

        teamOnePlayerNames = new String[teamOnePlayers.length];
        teamTwoPlayerNames = new String[teamTwoPlayers.length];

        for (int i = 0; i < teamOnePlayers.length; i++) {
            teamOnePlayerNames[i] = teamOnePlayers.toString();
        }

        for (int i = 0; i < teamTwoPlayers.length; i++) {
            teamTwoPlayerNames[i] = teamTwoPlayers.toString();
        }

        match = c.createMatch(teamOne, teamTwo);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_batch_input, menu);
        return true;
    }

    public boolean askForTeam() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // get team
        builder.setTitle("Pick a team")
                .setItems(teams, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        team_inner = teams[which];
                        askForPlayer();
                    }
                })
                .create()
                .show();



        return true;
    }

    public boolean askForPlayer() {

        if (team_inner.equals(teams[0])) {
            players_inner = teamOnePlayerNames;
        }
        else {
            players_inner = teamTwoPlayerNames;
        }


        // Get player
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pick a player")
                .setItems(players_inner, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        player_inner = players_inner[which];
                        askForInfraction();
                    }
                })
                .create()
                .show();

        return true;
    }

    public boolean askForInfraction() {

        //get color
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pick a card color")
                .setPositiveButton("Red", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        card_color_inner = "red";
                        askForPenaltyShot();

                    }
                })
                .setNegativeButton("Yellow", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        card_color_inner = "yellow";
                        askForPenaltyShot();
                    }
                })
                .create()
                .show();

        return true;
    }

    public boolean askForPenaltyShot() {
        //get penalty kick

        final controller c = new controller();


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Penalty kick?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        penalty_kick_inner = true;
                        c.createInfraction(player_inner, match, card_color_inner, penalty_kick_inner);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        penalty_kick_inner = false;
                        c.createInfraction(player_inner, match, card_color_inner, penalty_kick_inner);
                    }
                })
                .create()
                .show();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_add_infraction:

                askForTeam();





                return true;

            case R.id.action_add_shot:
                return true;

            case R.id.action_save_data:
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void init() {
        TableLayout stk = (TableLayout) findViewById(R.id.table_main);
        TableRow tbrow0 = new TableRow(this);
        TextView tv0 = new TextView(this);
        tv0.setText(" Sl.No ");
        tv0.setTextColor(Color.WHITE);
        tbrow0.addView(tv0);
        TextView tv1 = new TextView(this);
        tv1.setText(" Product ");
        tv1.setTextColor(Color.WHITE);
        tbrow0.addView(tv1);
        TextView tv2 = new TextView(this);
        tv2.setText(" Unit Price ");
        tv2.setTextColor(Color.WHITE);
        tbrow0.addView(tv2);
        TextView tv3 = new TextView(this);
        tv3.setText(" Stock Remaining ");
        tv3.setTextColor(Color.WHITE);
        tbrow0.addView(tv3);
        stk.addView(tbrow0);
        for (int i = 0; i < 25; i++) {
            TableRow tbrow = new TableRow(this);
            TextView t1v = new TextView(this);
            t1v.setText("" + i);
            t1v.setTextColor(Color.WHITE);
            t1v.setGravity(Gravity.CENTER);
            tbrow.addView(t1v);
            TextView t2v = new TextView(this);
            t2v.setText("Product " + i);
            t2v.setTextColor(Color.WHITE);
            t2v.setGravity(Gravity.CENTER);
            tbrow.addView(t2v);
            TextView t3v = new TextView(this);
            t3v.setText("Rs." + i);
            t3v.setTextColor(Color.WHITE);
            t3v.setGravity(Gravity.CENTER);
            tbrow.addView(t3v);
            TextView t4v = new TextView(this);
            t4v.setText("" + i * 15 / 32 * 10);
            t4v.setTextColor(Color.WHITE);
            t4v.setGravity(Gravity.CENTER);
            tbrow.addView(t4v);
            stk.addView(tbrow);
        }

    }

}
