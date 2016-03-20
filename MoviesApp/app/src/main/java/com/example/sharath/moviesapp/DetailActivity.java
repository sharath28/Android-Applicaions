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
import android.widget.Toolbar;

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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_sort_popular) {
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
                Picasso.with(getContext()).load(movie.poster_image).into(iconView);
//                ImageView iconView1 = (ImageView) rootView.findViewById(R.id.movie_background);
//                Picasso.with(getContext()).load(movie.background_image).into(iconView1);
                ((TextView) rootView.findViewById(R.id.movie_rating_text))
                        .setText("Rating:" + (float) movie.rating + "/ 10");
                ((RatingBar) rootView.findViewById(R.id.movie_rating)).setRating((float) movie.rating);
                ((TextView) rootView.findViewById(R.id.movie_overview_text))
                        .setText("Over View");
                ((TextView) rootView.findViewById(R.id.movie_overview))
                       .setText(movie.overview);
                ((TextView) rootView.findViewById(R.id.movie_release_date_text))
                        .setText("Release Date");
                ((TextView) rootView.findViewById(R.id.movie_release_date))
                        .setText(movie.release_date);
            }
            return rootView;
        }
    }
}
