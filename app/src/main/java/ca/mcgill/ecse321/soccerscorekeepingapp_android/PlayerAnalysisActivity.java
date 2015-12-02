package ca.mcgill.ecse321.soccerscorekeepingapp_android;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import ca.mcgill.ecse321.soccerscorekeeping.controller.Controller;
import ca.mcgill.ecse321.soccerscorekeeping.model.Player;
import ca.mcgill.ecse321.soccerscorekeeping.model.Team;

public class PlayerAnalysisActivity extends AppCompatActivity {

    // How many columns in table
    final int COLUMNS = 3;

    // Arrays that hold players
    Player[] playerArray;

    // How many players to display ... Default 10
    int displayedPlayers = 10;

    // Manager to get team data
    Controller controller;

    /*
     * MODE: 1. Sort by goals [DEFAULT]
     *       2. Sort by infractions
     */

    int sortingMode = 1;

    // For dialogs
    String[] sortModes = {"Goals", "Infractions"};
    String[] showHowMany = {"5", "10", "20", "50"};

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_league_analysis, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_sort_by:
                askForSortBy();
                return true;
            case R.id.action_change_number:
                askForChangeNumber();
                return true;
            default:
                return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_analysis);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialize table headers
        init();

        // Initialize controller
        controller = new Controller();

        // Nullify array
        playerArray = null;

        // First default display
        updatePlayerArray();
        displayPlayerArray();

    }

    private void askForSortBy() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sort by...")
                .setItems(sortModes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sortingMode = which + 1;
                        updatePlayerArray();
                        clean();
                        init();
                        displayPlayerArray();
                        Toast.makeText(PlayerAnalysisActivity.this, "Table Updated", Toast.LENGTH_LONG).show();
                    }
                })
                .create()
                .show();
    }

    private void askForChangeNumber() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("How many teams?")
                .setItems(showHowMany, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        displayedPlayers = Integer.parseInt(showHowMany[which]);
                        updatePlayerArray();
                        clean();
                        init();
                        displayPlayerArray();
                        Toast.makeText(PlayerAnalysisActivity.this, "Table Updated", Toast.LENGTH_LONG).show();
                    }
                })
                .create()
                .show();
    }

    // Sort, or redisplay the teams
    private void updatePlayerArray() {

        // Get a string - needed because of the nature of the topTeams function
        String sortingModeStr = "Goals";

        switch(sortingMode) {
            case 1:
                sortingModeStr = "Goals";
                break;
            case 2:
                sortingModeStr = "Infractions";
                break;
           default:
                break;
        }

        playerArray = controller.topPlayers(displayedPlayers, sortingModeStr);
    }

    // Display the team array
    private void displayPlayerArray() {
        if (playerArray == null)
            return;

        String playerName = "/";
        String playerGoals = "/";
        String playerPen = "/";

        for (int i = 0; i < playerArray.length; i++) {
            playerName = playerArray[i].getName();
            playerGoals = Integer.toString(playerArray[i].goalsScored());
            playerPen = Integer.toString(playerArray[i].numberOfInfractions());

            String[] passThis = {playerName, playerGoals, playerPen};

            addRow(passThis);
        }

    }

    // Add a row to the table
    private void addRow(String[] rowString) {
        // Let's protect ourselves from IndexOutOfBounds
        if (rowString.length < COLUMNS)
            return;

        TableLayout stk = (TableLayout) findViewById(R.id.table_main);
        TableRow tableRow = new TableRow(this);

        // Team Name
        TextView teamtv = new TextView(this);
        teamtv.setText(rowString[0]);
        teamtv.setTextColor(Color.WHITE);
        teamtv.setGravity(Gravity.CENTER);
        tableRow.addView(teamtv);

        // Goals
        TextView goalstv = new TextView(this);
        goalstv.setText(rowString[1]);
        goalstv.setTextColor(Color.WHITE);
        goalstv.setGravity(Gravity.CENTER);
        tableRow.addView(goalstv);

        // Penalties
        TextView pentv = new TextView(this);
        pentv.setText(rowString[2]);
        pentv.setTextColor(Color.WHITE);
        pentv.setGravity(Gravity.CENTER);
        tableRow.addView(pentv);

        stk.addView(tableRow);

    }

    // Init table headers
    private void init() {
        TableLayout stk = (TableLayout) findViewById(R.id.table_main);
        TableRow tbrow0 = new TableRow(this);

        TextView tv0 = new TextView(this);
        tv0.setText(" Player ");
        tv0.setTextColor(Color.WHITE);
        tbrow0.addView(tv0);

        TextView tv1 = new TextView(this);
        tv1.setText(" Goals ");
        tv1.setTextColor(Color.WHITE);
        tbrow0.addView(tv1);

        TextView tv11 = new TextView(this);
        tv11.setText(" Infractions ");
        tv11.setTextColor(Color.WHITE);
        tbrow0.addView(tv11);

        stk.addView(tbrow0);
    }

    private void clean() {
        TableLayout stk = (TableLayout) findViewById(R.id.table_main);
        stk.removeAllViews();
    }

}
