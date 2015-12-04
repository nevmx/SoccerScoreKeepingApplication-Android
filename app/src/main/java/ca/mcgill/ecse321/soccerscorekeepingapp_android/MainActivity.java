package ca.mcgill.ecse321.soccerscorekeepingapp_android;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
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
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.ResponseHandler;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.entity.mime.MultipartEntityBuilder;
import cz.msebera.android.httpclient.entity.mime.content.FileBody;
import cz.msebera.android.httpclient.impl.client.BasicResponseHandler;
import cz.msebera.android.httpclient.impl.client.CloseableHttpClient;
import cz.msebera.android.httpclient.impl.client.HttpClientBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import ca.mcgill.ecse321.soccerscorekeeping.admin.authentication;
import ca.mcgill.ecse321.soccerscorekeeping.admin.managerTools;
import ca.mcgill.ecse321.soccerscorekeeping.model.Manager;
import ca.mcgill.ecse321.soccerscorekeeping.persistence.PersistenceSoccerScoreKeeping;

public class MainActivity extends AppCompatActivity {

    public final static String MODE = "ca.mcgill.ecse321.soccerscorekeepingapp-android.MODE";

    // Variables for downloading scores
    String url = "http://cs.mcgill.ca/~jselwy/soccer.xml";
    RequestQueue queue;

    // Vars for uploading scores
    String uploadURL = "http://cs.mcgill.ca/~jselwy/XMLUpload.php";

    // Loading circle
    ProgressDialog pg;


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
        reloadScores();
    }
;
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

        else if (id == R.id.action_download_scores) {
            downloadScores();
        }

        else if (id == R.id.action_upload_scores) {
            new uploadScoresToServer().execute();
        }

        else if (id == R.id.action_clear_data) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            final EditText username = new EditText(this);
            final EditText password = new EditText(this);
            username.setInputType(InputType.TYPE_CLASS_TEXT);
            username.setHint("Username");

            password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            password.setHint("Password");

            LinearLayout login = new LinearLayout(this);
            login.setOrientation(LinearLayout.VERTICAL);
            login.addView(username);
            login.addView(password);

            builder.setTitle("Enter manager password")
                    .setView(login)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            authentication a = new authentication();
                            if (a.authenticate(username.getText().toString(), password.getText().toString().toCharArray())) {
                                Toast.makeText(MainActivity.this, "All Data Cleared", Toast.LENGTH_LONG).show();

                                // Delete data
                                Manager m = Manager.getInstance();
                                m.delete();
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

    /* Download soccerscores */
    private void downloadScores() {
        queue = Volley.newRequestQueue(this);

        // Show a loading screen
        pg = new ProgressDialog(this);
        pg.setTitle("Loading");
        pg.setMessage("Downloading scores...");
        pg.setCancelable(false);
        pg.show();

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        saveScores(response);
                        reloadScores();
                        pg.dismiss();
                        Toast.makeText(MainActivity.this, "Scores Downloaded", Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Download Error, try again later.", Toast.LENGTH_LONG).show();
            }
        });
        queue.add(stringRequest);
    }

    /* Save the scores that were downloaded */
    public void saveScores(String scoreString) {
        try
        {
            File filesDir = getFilesDir();
            File file = new File(filesDir, "soccerscores.xml");
            FileWriter writer = new FileWriter(file);
            writer.write(scoreString);
            writer.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    /* Reload scores */
    private void reloadScores() {
        Manager m = Manager.getInstance();
        m.delete();
        // Reload
        PersistenceSoccerScoreKeeping.loadSoccerScores(this);

    }

    private class uploadScoresToServer extends AsyncTask<Void, Void, Void> {
        private final ProgressDialog pg = new ProgressDialog(MainActivity.this);

        protected void onPreExecute() {
            this.pg.setMessage("Loading...");
            this.pg.setCancelable(false);
            this.pg.show();
        }

        @Override
        protected Void doInBackground(Void... urls) {
            uploadScores(uploadURL);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (pg.isShowing())
                pg.dismiss();
            Toast.makeText(MainActivity.this, "Scores Uploaded", Toast.LENGTH_LONG).show();

        }
    }

    /* Upload the XML score */
    private void uploadScores(String url) {
        CloseableHttpClient httpclient = HttpClientBuilder.create().build();
        HttpPost httppost = new HttpPost(url);

        try {
            // Get the file and build the FileBody object
            File filesDir = getFilesDir();
            File file = new File(filesDir, "soccerscores.xml");
            FileBody bin = new FileBody(file);

            // Build the multipartentity for the multipart/form-data upload
            MultipartEntityBuilder reqEntity = MultipartEntityBuilder.create();

            // "upload" is the value of the field PHP will look for
            reqEntity.addPart("upload", bin);

            // Assign the entity to the HttpPost object
            HttpEntity entity = reqEntity.build();
            httppost.setEntity(entity);

            // This is for debug
//            System.out.println("Requesting : " + httppost.getRequestLine());

            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            // The response
            String responseBody = httpclient.execute(httppost, responseHandler);

            // Debug...
//            System.out.println("responseBody : " + responseBody);

            // close the client
            httpclient.close();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* Called when input mode selected */
    public void authenticateScoreKeeper(final View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        final EditText username = new EditText(this);
        final EditText password = new EditText(this);
        username.setInputType(InputType.TYPE_CLASS_TEXT);
        username.setHint("Username");

        password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        password.setHint("Password");

        LinearLayout login = new LinearLayout(this);
        login.setOrientation(LinearLayout.VERTICAL);
        login.addView(username);
        login.addView(password);

        builder.setTitle("Enter scorekeeper password")
                .setView(login)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        authentication a = new authentication();
                        if (a.authenticateScoreKeeper(username.getText().toString(), password.getText().toString().toCharArray())) {

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

        final EditText username = new EditText(this);
        final EditText password = new EditText(this);
        username.setInputType(InputType.TYPE_CLASS_TEXT);
        username.setHint("Username");

        password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        password.setHint("Password");

        LinearLayout login = new LinearLayout(this);
        login.setOrientation(LinearLayout.VERTICAL);
        login.addView(username);
        login.addView(password);

        builder.setTitle("Manager Login")
                .setView(login)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        authentication a = new authentication();
                        if (a.authenticate(username.getText().toString(), password.getText().toString().toCharArray())) {
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
    public void openLeagueAnalysisMode(View view) {
        Intent intent = new Intent(this, LeagueAnalysisActivity.class);
        startActivity(intent);
    }

    /* Called when player analysis button is pressed */
    public void openPlayerAnalysisMode(View view) {
        Intent intent = new Intent(this, PlayerAnalysisActivity.class);
        startActivity(intent);
    }
}
