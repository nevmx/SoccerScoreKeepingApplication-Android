package ca.mcgill.ecse321.soccerscorekeepingapp_android;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import ca.mcgill.ecse321.soccerscorekeeping.admin.authentication;
import ca.mcgill.ecse321.soccerscorekeeping.admin.managerTools;
import ca.mcgill.ecse321.soccerscorekeeping.persistence.PersistenceSoccerScoreKeeping;

public class MainActivity extends AppCompatActivity {

    public final static String MODE = "ca.mcgill.ecse321.soccerscorekeepingapp-android.MODE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "SOCCER SCORE KEEPER APP 0.0.1", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // Load soccer score XML file
        PersistenceSoccerScoreKeeping.loadSoccerScores();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        else if (id == R.id.action_clear_data) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            final EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

            builder.setTitle("Enter manager password")
                    .setView(input)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (authentication.authenticate(input.getText().toString().toCharArray())) {
                                Toast.makeText(MainActivity.this, "All Data Cleared", Toast.LENGTH_LONG).show();

                                // Delete data
                                // TODO: Provide the ability to clear all score data
                            }
                            else {
                                Toast.makeText(MainActivity.this, "Wrong password. Please try again.", Toast.LENGTH_LONG).show();
                            }
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    })
                    .create().show();
        }

        return super.onOptionsItemSelected(item);
    }

    /* Called when input mode selected */
    public void authenticateScoreKeeper(final View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        builder.setTitle("Enter scorekeeper password")
                .setView(input)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (authentication.authenticateScoreKeeper(input.getText().toString().toCharArray())) {

                            Toast.makeText(MainActivity.this, "Scorekeeper Mode Active", Toast.LENGTH_LONG).show();

                            switch (view.getId()) {
                                case R.id.liveInputButton:
                                    openCreateMatchPageForLive();
                                    break;
                                case R.id.batchInputButton:
                                    openCreateMatchPageForBatch();
                                    break;
                                default:
                                    Toast.makeText(MainActivity.this, "Unexpected Error", Toast.LENGTH_LONG).show();
                                    break;
                            }
                        }
                        else {
                            Toast.makeText(MainActivity.this, "Wrong password. Please try again.", Toast.LENGTH_LONG).show();
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

    private void openCreateMatchPageForBatch() {
        Intent intent = new Intent(this, CreateMatchActivity.class);
        String mode = "batch";
        intent.putExtra(MODE, mode);
        startActivity(intent);
    }

    private void openCreateMatchPageForLive() {
        Intent intent = new Intent(this, CreateMatchActivity.class);
        String mode = "live";
        intent.putExtra(MODE, mode);
        startActivity(intent);
    }

    /* Called when manager button is pressed */
    public void authenticateManager(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        builder.setTitle("Enter manager password")
                .setView(input)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (authentication.authenticate(input.getText().toString().toCharArray())) {
                            Toast.makeText(MainActivity.this, "Manager Mode Active", Toast.LENGTH_LONG).show();
                            openManagerActivity();
                        }
                        else {
                            Toast.makeText(MainActivity.this, "Wrong password. Please try again.", Toast.LENGTH_LONG).show();
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

    private void openManagerActivity() {
        Intent intent = new Intent(this, ManagerActivity.class);
        startActivity(intent);
    }

    /* Called when league analysis button is pressed */
    private void openLeagueAnalysisMode(View view) {
        Intent intent = new Intent(this, LeagueAnalysisActivity.class);
        startActivity(intent);
    }

    /* Called when player analysis button is pressed */
    private void openPlayerAnalysisMode(View view) {
        Intent intent = new Intent(this, PlayerAnalysisActivity.class);
        startActivity(intent);
    }
}
