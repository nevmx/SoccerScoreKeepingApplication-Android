package ca.mcgill.ecse321.soccerscorekeepingapp_android;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse321.soccerscorekeeping.admin.managerTools;
import ca.mcgill.ecse321.soccerscorekeeping.model.Manager;
import ca.mcgill.ecse321.soccerscorekeeping.model.Player;
import ca.mcgill.ecse321.soccerscorekeeping.model.Team;

public class ManagerActivity extends AppCompatActivity {

    private managerTools mt;
    private Manager m;

    private String[] teamNames;
    private String[] playerNames;

    private List<Team> teamList;
    private List<Player> playerList;

    /*
        These variables are for user dialog choices.
     */

    private String teamChoice;
    private String playerChoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Init
//        mt = new managerTools();
        m = Manager.getInstance();
    }

    public void onCreateTeamButtonClick(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);

        builder.setTitle("Enter a team name")
                .setView(input)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        createTeam(input.getText().toString());
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .create()
                .show();
    }

    public void onRemoveTeamButtonClick(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        teamList = m.getTeams();
        teamNames = new String[teamList.size()];
        int i = 0;
        for (Team team : teamList) {
            teamNames[i] = team.getName();
            i++;
        }

        builder.setTitle("Select a team to be removed")
                .setItems(teamNames, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        removeTeam(teamNames[which]);
                    }
                })
                .create()
                .show();


    }

    public void onCreatePlayerButtonClick(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        teamList = m.getTeams();
        teamNames = new String[teamList.size()];
        int i = 0;
        for (Team team : teamList) {
            teamNames[i] = team.getName();
            i++;
        }

        builder.setTitle("Select a team for the new player")
                .setItems(teamNames, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        createPlayer(teamNames[which]);
                    }
                })
                .create()
                .show();
    }

    public void onRemovePlayerButtonClick(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        teamList = m.getTeams();
        teamNames = new String[teamList.size()];
        int i = 0;
        for (Team team : teamList) {
            teamNames[i] = team.getName();
            i++;
        }

        builder.setTitle("Select a team for player removal")
                .setItems(teamNames, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        removePlayer(teamNames[which]);
                    }
                })
                .create()
                .show();
    }

    public void onExitButtonClick(View view) {
        Toast.makeText(this, "Managed Mode Closed", Toast.LENGTH_LONG).show();
        this.finish();
    }

    private void createTeam(String teamName) {
        mt = new managerTools();
        String error = mt.createTeam(teamName);
        if (error != null) {
            Toast.makeText(this, "Error: " + error, Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(this, "Team Created", Toast.LENGTH_LONG).show();
        }
    }

    private void removeTeam(String teamName) {
        mt = new managerTools();
        String error = mt.deleteTeam(teamName);
        if (error != null) {
            Toast.makeText(this, "Error: " + error, Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(this, "Team Removed", Toast.LENGTH_LONG).show();
        }
    }

    private void createPlayer(final String teamName) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);

        builder.setTitle("Enter a player name")
                .setView(input)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mt = new managerTools();
                        String error = mt.createPlayer(input.getText().toString(), teamName);
                        if (error != null) {
                            Toast.makeText(ManagerActivity.this, "Error: " + error, Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(ManagerActivity.this, "Player Created", Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .create()
                .show();
    }

    private void removePlayer(final String teamName) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        playerList = m.getTeam(teamName).getPlayers();

        if (playerList.size() == 0) {
            Toast.makeText(this, "Error: Team is empty", Toast.LENGTH_LONG).show();
            return;
        }

        playerNames = new String[playerList.size()];
        int i = 0;
        for (Player player : playerList) {
            playerNames[i] = player.getName();
            i++;
        }

        builder.setTitle("Select a player to remove")
                .setItems(playerNames, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mt = new managerTools();
                        String error = mt.deletePlayer(playerNames[which], teamName);
                        if (error != null) {
                            Toast.makeText(ManagerActivity.this, "Error: " + error, Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(ManagerActivity.this, "Player Removed", Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .create()
                .show();
    }

}
