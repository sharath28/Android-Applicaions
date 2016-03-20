package com.example.sharath.moviesapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.sharath.moviesapp.R;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_detail);
        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            arguments.putParcelable("key", getIntent().getExtras().getParcelable("key"));

            PlaceholderFragment fragment = new PlaceholderFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_detail_container, fragment)
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        private final String LOG_TAG = PlaceholderFragment.class.getSimpleName();
        public static final String ITEM_ID = "item_id";

        public PlaceholderFragment() {
        }
        private Movie movie;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
            Bundle arguments = getArguments();
            if(arguments != null)
            {
                Log.v(LOG_TAG,"Details Page");
                movie = arguments.getParcelable("key");
                ((TextView) rootView.findViewById(R.id.movie_title))
                        .setText(movie.title);
                ImageView iconView = (ImageView) rootView.findViewById(R.id.movie_poster);
                Picasso.with(getContext()).load(movie.image).into(iconView);
                ((RatingBar) rootView.findViewById(R.id.movie_rating)).setRating(movie.rating);
            }
//            Intent intent = getActivity().getIntent();
//            if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
//                String forecastStr = intent.getStringExtra(Intent.EXTRA_TEXT);
//                ((TextView) rootView.findViewById(R.id.detail_text))
//                        .setText(forecastStr);
//            }

            return rootView;
        }
    }
}
