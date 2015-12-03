package ca.mcgill.ecse321.soccerscorekeepingapp_android;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse321.soccerscorekeeping.controller.Controller;
import ca.mcgill.ecse321.soccerscorekeeping.model.Manager;
import ca.mcgill.ecse321.soccerscorekeeping.model.Match;
import ca.mcgill.ecse321.soccerscorekeeping.model.Player;

public class BatchInputActivity extends AppCompatActivity {

    Match match;
    String[] teams;
    List<Player> teamOnePlayers;
    List<Player> teamTwoPlayers;

    String[] teamOnePlayerNames;
    String[] teamTwoPlayerNames;

    ArrayList<String[]> allData;

    /*
        The following variables are for the functions that get user input and set the variables to create
        a match.
     */
    String team_inner;
    String player_inner;
    String[] players_inner;
    String card_color_inner;
    boolean penalty_kick_inner;
    boolean goal_inner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batch_input);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        init();

        // Start the match
        Controller c = new Controller();
        Intent fromLast = getIntent();

        allData = new ArrayList<>();

        String teamOne = fromLast.getStringExtra(CreateMatchActivity.TEAM_ONE);
        String teamTwo = fromLast.getStringExtra(CreateMatchActivity.TEAM_TWO);

        match = c.createMatch(teamOne, teamTwo);

        teams = new String[2];
        teams[0] = teamOne;
        teams[1] = teamTwo;

        //Get player lists
        Manager m = Manager.getInstance();

        teamOnePlayers = m.getTeam(teams[0]).getPlayers();
        teamTwoPlayers = m.getTeam(teams[1]).getPlayers();

        teamOnePlayerNames = new String[teamOnePlayers.size()];
        teamTwoPlayerNames = new String[teamTwoPlayers.size()];

        int i = 0;

        for (Player player : teamOnePlayers) {
            teamOnePlayerNames[i] = player.getName();
            i++;
        }

        i = 0;

        for (Player player : teamTwoPlayers) {
            teamTwoPlayerNames[i] = player.getName();
            i++;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_batch_input, menu);
        return true;
    }

    public boolean askForTeam(final String mode) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // get team
        builder.setTitle("Pick a team")
                .setItems(teams, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        team_inner = teams[which];
                        askForPlayer(mode);
                    }
                })
                .create()
                .show();



        return true;
    }

    public boolean askForPlayer(final String mode) {

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
                        if (mode.equals("i"))
                            askForInfraction();
                        else if (mode.equals("s"))
                            askForGoal();
                        else {
                            // ...
                        }
                    }
                })
                .create()
                .show();

        return true;
    }

    public boolean askForGoal() {
        // get goal or no
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Goal?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        goal_inner = true;
                        addRow(createShotArray(team_inner, player_inner, goal_inner));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        goal_inner = false;
                        addRow(createShotArray(team_inner, player_inner, goal_inner));
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
                        card_color_inner = "Red";
                        askForPenaltyShot();

                    }
                })
                .setNegativeButton("Yellow", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        card_color_inner = "Yellow";
                        askForPenaltyShot();
                    }
                })
                .create()
                .show();

        return true;
    }

    public boolean askForPenaltyShot() {
        //get penalty kick

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Penalty kick?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        penalty_kick_inner = true;
                        addRow(createInfractionArray(team_inner, player_inner, card_color_inner, penalty_kick_inner));

                        // c.createInfraction(player_inner, match, card_color_inner, penalty_kick_inner);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        penalty_kick_inner = false;
                        addRow(createInfractionArray(team_inner, player_inner, card_color_inner, penalty_kick_inner));
                        // c.createInfraction(player_inner, match, card_color_inner, penalty_kick_inner);
                    }
                })
                .create()
                .show();



        return true;
    }

    // Create an array of strings that will be passed to addRow
    public String[] createInfractionArray(String iTeam, String iPlayer, String iCard, boolean iKick) {
        String iKickString;

        if (iKick)
            iKickString = "Yes";
        else
            iKickString = "No";

        String[] infraction = {iTeam, iPlayer, "I", iCard, iKickString, ""};

        allData.add(infraction);

        return infraction;
    }

    // Create an array of strings that will be passed to addRow
    public String[] createShotArray(String gTeam, String gPlayer, boolean gGoal) {
        String gGoalString;

        if (gGoal)
            gGoalString = "Yes";
        else
            gGoalString = "No";

        String[] shot = {gTeam, gPlayer, "S", "", "", gGoalString};

        allData.add(shot);

        return shot;

    }


    /*
        !!! The argument passed must be of length 6 !!!
     */
    public void addRow(String[] strings) {

        TableLayout stk = (TableLayout) findViewById(R.id.table_main);
        TableRow tableRow = new TableRow(this);

        TextView teamtv = new TextView(this);
        teamtv.setText(strings[0]);
        teamtv.setTextColor(Color.WHITE);
        teamtv.setGravity(Gravity.CENTER);
        tableRow.addView(teamtv);

        TextView playertv = new TextView(this);
        playertv.setText(strings[1]);
        playertv.setTextColor(Color.WHITE);
        playertv.setGravity(Gravity.CENTER);
        tableRow.addView(playertv);

        TextView typetv = new TextView(this);
        typetv.setText(strings[2]);
        typetv.setTextColor(Color.WHITE);
        typetv.setGravity(Gravity.CENTER);
        tableRow.addView(typetv);

        TextView cardColortv = new TextView(this);
        cardColortv.setText(strings[3]);
        cardColortv.setTextColor(Color.WHITE);
        cardColortv.setGravity(Gravity.CENTER);
        tableRow.addView(cardColortv);

        TextView penKicktv = new TextView(this);
        penKicktv.setText(strings[4]);
        penKicktv.setTextColor(Color.WHITE);
        penKicktv.setGravity(Gravity.CENTER);
        tableRow.addView(penKicktv);

        TextView goaltv = new TextView(this);
        goaltv.setText(strings[5]);
        goaltv.setTextColor(Color.WHITE);
        goaltv.setGravity(Gravity.CENTER);
        tableRow.addView(goaltv);

        stk.addView(tableRow);

        Toast.makeText(this, strings[2] + " added", Toast.LENGTH_LONG).show();
    }

    public void saveData() {
        Controller c = new Controller();
        for (String[] oneEvent : allData) {
            if (oneEvent[2].equals("I")) {
                boolean penaltyKick;
                penaltyKick = oneEvent[4].equals("Yes");
                c.createInfraction(oneEvent[1], match, oneEvent[3].toUpperCase(), penaltyKick);
            }
            else {
                boolean goal;
                goal = oneEvent[5].equals("Yes");
                c.createShot(oneEvent[1], match, goal);
            }
        }
        c.chooseWinner(match);
        Toast.makeText(this, "Data saved", Toast.LENGTH_LONG).show();
        this.finish();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_add_infraction:

                askForTeam("i");
                return true;

            case R.id.action_add_shot:
                askForTeam("s");
                return true;

            case R.id.action_save_data:
                saveData();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void init() {
        TableLayout stk = (TableLayout) findViewById(R.id.table_main);
        TableRow tbrow0 = new TableRow(this);

        TextView tv0 = new TextView(this);
        tv0.setText(" Team ");
        tv0.setTextColor(Color.WHITE);
        tbrow0.addView(tv0);

        TextView tv1 = new TextView(this);
        tv1.setText(" Player ");
        tv1.setTextColor(Color.WHITE);
        tbrow0.addView(tv1);

        TextView tv11 = new TextView(this);
        tv11.setText(" Type ");
        tv11.setTextColor(Color.WHITE);
        tbrow0.addView(tv11);

        TextView tv2 = new TextView(this);
        tv2.setText(" Card Clr ");
        tv2.setTextColor(Color.WHITE);
        tbrow0.addView(tv2);

        TextView tv3 = new TextView(this);
        tv3.setText(" Pnl Kick ");
        tv3.setTextColor(Color.WHITE);
        tbrow0.addView(tv3);

        TextView tv4 = new TextView(this);
        tv4.setText(" Goal ");
        tv4.setTextColor(Color.WHITE);
        tbrow0.addView(tv4);

        stk.addView(tbrow0);
    }

}
