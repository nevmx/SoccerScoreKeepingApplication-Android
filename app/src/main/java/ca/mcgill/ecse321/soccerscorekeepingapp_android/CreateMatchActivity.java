package ca.mcgill.ecse321.soccerscorekeepingapp_android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ca.mcgill.ecse321.soccerscorekeeping.model.Manager;
import ca.mcgill.ecse321.soccerscorekeeping.model.Team;

public class CreateMatchActivity extends AppCompatActivity {

    public final static String TEAM_ONE = "ca.mcgill.ecse321.soccerscorekeepingapp-android.TEAM_ONE";
    public final static String TEAM_TWO = "ca.mcgill.ecse321.soccerscorekeepingapp-android.TEAM_TWO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_match);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();

        String mode = intent.getStringExtra(MainActivity.MODE);

        /* DEBUG
        TextView t = (TextView) findViewById(R.id.pageTitle);
        t.setText(mode);
        */

        // Populate the spinners
        Spinner teamOne = (Spinner) findViewById(R.id.spinner);
        Spinner teamTwo = (Spinner) findViewById(R.id.spinner2);

        Manager m = Manager.getInstance();

        Iterator<Team> i = m.getTeams().iterator();
        List<String> teams = new ArrayList<String>();

        while (i.hasNext()) {
            Team t = i.next();
            teams.add(t.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, teams);
        teamOne.setAdapter(adapter);
        teamTwo.setAdapter(adapter);



    }

    public void goToBatchInput(View view) {
        Spinner teamOneSpinner = (Spinner) findViewById(R.id.spinner);
        Spinner teamTwoSpinner = (Spinner) findViewById(R.id.spinner2);

        String teamOne = teamOneSpinner.getSelectedItem().toString();
        String teamTwo = teamTwoSpinner.getSelectedItem().toString();

        if (teamOne.equals(teamTwo)) {
            // Error same team
            Toast.makeText(this, "Please select two different teams", Toast.LENGTH_LONG).show();
        }
        else {
            Intent fromLast = getIntent();
            String mode = fromLast.getStringExtra(MainActivity.MODE);

            if (mode.equals("batch")) {
                // ok - pass to teams to the batch input page
                Intent intent = new Intent(this, BatchInputActivity.class);
                intent.putExtra(TEAM_ONE, teamOne);
                intent.putExtra(TEAM_TWO, teamTwo);
                startActivity(intent);
                this.finish();
            }
            else if (mode.equals("live")) {
                // ok - pass to teams to the live input page
                Intent intent = new Intent(this, LiveInputActivity.class);
                intent.putExtra(TEAM_ONE, teamOne);
                intent.putExtra(TEAM_TWO, teamTwo);
                startActivity(intent);
                this.finish();
            }
            else {
                /* ???? */
            }
        }
    }


}
