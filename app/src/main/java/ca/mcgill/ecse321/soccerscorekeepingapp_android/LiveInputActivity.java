package ca.mcgill.ecse321.soccerscorekeepingapp_android;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ca.mcgill.ecse321.soccerscorekeeping.controller.Controller;
import ca.mcgill.ecse321.soccerscorekeeping.model.Manager;
import ca.mcgill.ecse321.soccerscorekeeping.model.Match;
import ca.mcgill.ecse321.soccerscorekeeping.model.Player;

public class LiveInputActivity extends AppCompatActivity {

    private Controller c;

    private Match match;

    private String teamOne;
    private String teamTwo;

    private int teamOneScore;
    private int teamTwoScore;

    private String[] teams;
    private String[] teamOnePlayerNames;
    private String[] teamTwoPlayerNames;

    private List<Player> teamOnePlayers;
    private List<Player> teamTwoPlayers;

    private Manager m;

    /*
        The following variables are for choices during dialogs.
     */

    private String teamChoice;
    private String playerChoice;
    private boolean goalChoice;
    private String colorChoice;
    private boolean penaltyKickChoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_input);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Init
        c = new Controller();
        m = Manager.getInstance();

        Intent fromLast = getIntent();

        teamOne = fromLast.getStringExtra(CreateMatchActivity.TEAM_ONE);
        teamTwo = fromLast.getStringExtra(CreateMatchActivity.TEAM_TWO);

        teams = new String[2];

        teams[0] = teamOne;
        teams[1] = teamTwo;

        teamOnePlayers = m.getTeam(teamOne).getPlayers();
        teamTwoPlayers = m.getTeam(teamTwo).getPlayers();

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

        teamOneScore = 0;
        teamTwoScore = 0;


    }

    /*
        BUTTON CLICKS
     */

    public void onStartMatchClick(View view) {
        // Start match
        match = c.createMatch(teamOne, teamTwo);

        // Disable and enable different buttons
        Button button = (Button) findViewById(R.id.addInfractionButton);
        button.setEnabled(true);

        button = (Button) findViewById(R.id.addShotButton);
        button.setEnabled(true);

        button = (Button) findViewById(R.id.endGameButton);
        button.setEnabled(true);

        button = (Button) findViewById(R.id.startMatchButton);
        button.setEnabled(false);

        Chronometer chrono = (Chronometer) findViewById(R.id.chronometer);
        chrono.setFormat("%s");
        chrono.setBase(SystemClock.elapsedRealtime());
        chrono.start();

        // set score to 0 - 0
        TextView score = (TextView) findViewById(R.id.score);
        score.setTextColor(Color.parseColor("#40aa43"));
        score.setText(teamOneScore + " " + teamOne + " vs " + teamTwo + " " + teamTwoScore);

        Toast.makeText(this, "Match started", Toast.LENGTH_LONG).show();

    }

    public void onAddShotClick(View view) {
        // Add a shot
        // Start the dialog sequence
        askForTeam("s");
    }

    public void onAddInfractionClick(View view) {
        // Add an infraction
        // Start the dialog sequence
        askForTeam("i");
    }

    public void onEndGameClick(View view) {
        // End the game
        c.chooseWinner(match);

        Toast.makeText(this, "Match ended", Toast.LENGTH_LONG).show();

        this.finish();
    }

    private void askForTeam(final String mode) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // get team
        builder.setTitle("Pick a team")
                .setItems(teams, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        teamChoice = teams[which];
                        askForPlayer(mode);
                    }
                })
                .create()
                .show();
    }

    private void askForPlayer(final String mode) {

        final String[] playerList;

        if (teamChoice.equals(teams[0])) {
            playerList = teamOnePlayerNames;
        }
        else {
            playerList = teamTwoPlayerNames;
        }


        // Get player
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pick a player")
                .setItems(playerList, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        playerChoice = playerList[which];
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
    }

    private void askForInfraction() {
        //get color
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pick a card color")
                .setPositiveButton("Red", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        colorChoice = "Red";
                        askForPenaltyShot();

                    }
                })
                .setNegativeButton("Yellow", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        colorChoice = "Yellow";
                        askForPenaltyShot();
                    }
                })
                .create()
                .show();
    }

    public void askForPenaltyShot() {
        //get penalty kick

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Penalty kick?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        penaltyKickChoice = true;
                        saveInfraction();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        penaltyKickChoice = false;
                        saveInfraction();
                    }
                })
                .create()
                .show();
    }

    private void askForGoal() {
        // get goal or no
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Goal?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        goalChoice = true;
                        saveShot();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        goalChoice = false;
                        saveShot();
                    }
                })
                .create()
                .show();
    }


    /*
        Saving methods
     */

    private boolean saveShot() {
        TextView score = (TextView) findViewById(R.id.score);

        if (teamChoice.equals(teamOne) && goalChoice)
            teamOneScore++;
        if (teamChoice.equals(teamTwo) && goalChoice)
            teamTwoScore++;

        c.createShot(playerChoice, match, goalChoice);
        score.setText(teamOneScore + " " + teamOne + " vs " + teamTwo + " " + teamTwoScore);
        Toast.makeText(this, "Shot Added", Toast.LENGTH_LONG);
        clearSelections();
        return true;
    }

    private boolean saveInfraction() {
        c.createInfraction(playerChoice, match, colorChoice, penaltyKickChoice);
        Toast.makeText(this, "Infraction Added", Toast.LENGTH_LONG);
        clearSelections();
        return true;
    }

    /*
        Clear all choice selections for next input
     */

    private void clearSelections() {
        teamChoice = null;
        playerChoice = null;
        colorChoice = null;
    }

}
