package ca.mcgill.ecse321.soccerscorekeepingapp_android;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import ca.mcgill.ecse321.soccerscorekeeping.admin.managerTools;

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

        managerTools mt = new managerTools();

        mt.createTeam("Team1");
        mt.createTeam("Team2");
        mt.createTeam("Team3");
        mt.createTeam("Team4");
        mt.createTeam("Team5");
        mt.createTeam("Team6");

        mt.createPlayer("PlayerOne", "Team1");
        mt.createPlayer("PlayerTwo", "Team1");
        mt.createPlayer("PlayerThree", "Team1");
        mt.createPlayer("PlayerOneOne", "Team2");
        mt.createPlayer("PlayerTwoTwo", "Team2");
        mt.createPlayer("PlayerThreeThree", "Team2");
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

        return super.onOptionsItemSelected(item);
    }

    /* Called when batch input mode button is pressed */
    public void openCreateMatchPageForBatch(View view) {
        Intent intent = new Intent(this, CreateMatchActivity.class);
        String mode = "batch";
        intent.putExtra(MODE, mode);
        startActivity(intent);
    }

    /* Called when live input mode button is pressed */
    public void openCreateMatchPageForLive(View view) {
        Intent intent = new Intent(this, CreateMatchActivity.class);
        String mode = "live";
        intent.putExtra(MODE, mode);
        startActivity(intent);
    }

    /* Called when manager button is pressed */
    public void openManagerActivity(View view) {
        Intent intent = new Intent(this, ManagerActivity.class);
        startActivity(intent);
    }
}
