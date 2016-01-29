package com.example.sharath.myappportfolio.app;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button_spotify_streamer,button_score_app,button_library_builder,button_build_it_bigger,button_xyz_reader,button_capstone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        button_spotify_streamer = (Button) findViewById(R.id.spotify_streamer);
        button_score_app = (Button) findViewById(R.id.scores_app);
        button_library_builder = (Button) findViewById(R.id.library_builder);
        button_build_it_bigger = (Button) findViewById(R.id.build_it_bigger);
        button_xyz_reader = (Button) findViewById(R.id.xyz_reader);
        button_capstone = (Button) findViewById(R.id.capstone);

        button_spotify_streamer.setOnClickListener(this);
        button_score_app.setOnClickListener(this);
        button_library_builder.setOnClickListener(this);
        button_build_it_bigger.setOnClickListener(this);
        button_xyz_reader.setOnClickListener(this);
        button_capstone.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()){
            case R.id.spotify_streamer:Toast.makeText(getApplicationContext(),"This button will launch my Spotify Streamer app", Toast.LENGTH_LONG).show();
                break;
            case R.id.scores_app:Toast.makeText(getApplicationContext(),"This button will launch my Scores app", Toast.LENGTH_LONG).show();
                break;
            case R.id.library_builder:Toast.makeText(getApplicationContext(),"This button will launch my Library Builder app", Toast.LENGTH_LONG).show();
                break;
            case R.id.build_it_bigger:Toast.makeText(getApplicationContext(),"This button will launch my Build it bigger app", Toast.LENGTH_LONG).show();
                break;
            case R.id.xyz_reader:Toast.makeText(getApplicationContext(),"This button will launch my XYZ Reader app", Toast.LENGTH_LONG).show();
                break;
            case R.id.capstone:Toast.makeText(getApplicationContext(),"This button will launch my Capstone app", Toast.LENGTH_LONG).show();
                break;
        }
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
}
