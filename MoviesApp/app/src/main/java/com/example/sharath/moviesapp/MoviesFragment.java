package com.example.sharath.moviesapp;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MoviesFragment extends Fragment {
    static final String SORT_POPULAR = "popular";
    static final String SORT_RATING = "top_rated";
    static String sort_flag = "rating";

    public MoviesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.moviesfragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_sort_rating) {
            if (sort_flag.equals("rating"))
            {
                Toast.makeText(getActivity(), "Sorted as per Rating", Toast.LENGTH_SHORT).show();
                return true;
            }
            else
            {
                FetchMoviesTask moviesTask = new FetchMoviesTask();
                moviesTask.execute(SORT_RATING);
                sort_flag="rating";
                return true;
            }
        }
        else if (id == R.id.action_sort_popular) {
            if (sort_flag.equals("popular"))
            {
                Toast.makeText(getActivity(), "Sorted as per Popularity", Toast.LENGTH_SHORT).show();
                return true;
            }
            else
            {
                FetchMoviesTask moviesTask = new FetchMoviesTask();
                moviesTask.execute(SORT_POPULAR);
                sort_flag="popular";
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private ImageListAdapter movieadapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        Context context = getActivity();
        movieadapter = new ImageListAdapter(context, new ArrayList<Movie>());

        GridView gridView = (GridView) rootView.findViewById(R.id.grid_view_movies);
        gridView.setAdapter(movieadapter);
        if(sort_flag.equals("rating")) {
            FetchMoviesTask moviesTask = new FetchMoviesTask();
            moviesTask.execute(SORT_RATING);
            sort_flag = "rating";
        }
        else
        {
            FetchMoviesTask moviesTask = new FetchMoviesTask();
            moviesTask.execute(SORT_POPULAR);
            sort_flag = "popular";
        }
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie movie = movieadapter.getItem(position);
                Toast.makeText(getActivity(), "Movie title:" + movie.title+"\nMovie Overview: "+movie.overview, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), DetailActivity.class)
                        .putExtra("key", movie);
               startActivity(intent);
            }

       });
        return rootView;
    }

    public class FetchMoviesTask extends AsyncTask<String, Void, Movie[]> {
        private final String LOG_TAG = FetchMoviesTask.class.getSimpleName();

        private Movie[] getMoviesDataFromJson(String MoviesJsonStr)
                throws JSONException {


            final String OWM_RESULT = "results";
            JSONObject MoviesJson = new JSONObject(MoviesJsonStr);
            JSONArray moviesArray = MoviesJson.getJSONArray(OWM_RESULT);
            String image_url;

            Movie[] resultStrs = new Movie[20];
            for (int i = 0; i < moviesArray.length(); i++) {
                String basic_url = "http://image.tmdb.org/t/p/w185";
                JSONObject singlemovie = moviesArray.getJSONObject(i);
                image_url = singlemovie.getString("poster_path");
                String id = singlemovie.getString("id");
                String title = singlemovie.getString("original_title");
                String release_date = singlemovie.getString("release_date");
                double rating = singlemovie.getDouble("vote_average");
                String overview = singlemovie.getString("overview");
                String background_image_url = singlemovie.getString("backdrop_path");
                String final_background_image_url = basic_url + background_image_url;
                String final_image_url = basic_url+image_url;
                resultStrs[i] = new Movie(id,final_image_url,title,release_date,rating,overview,final_background_image_url);
                Log.v(LOG_TAG, "Image urls:" + resultStrs[i]);
            }
            Log.v(LOG_TAG, "Final Image urls:" + resultStrs);
            return resultStrs;
        }

        @TargetApi(Build.VERSION_CODES.KITKAT)
        @Override
        protected Movie[] doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String api_key = "08eff0c57313416b04009b2ccf767b8d";
            // Will contain the raw JSON response as a string.
            String moviesJsonStr = null;
            String baseUrl = null;
            try {
                // Construct the URL for the Moviesdb query
                if("popular".equals(params[0])) {
                    baseUrl = "http://api.themoviedb.org/3/movie/popular?api_key="+api_key;
                }
                else
                {
                    baseUrl = "http://api.themoviedb.org/3/movie/top_rated?api_key=08eff0c57313416b04009b2ccf767b8d";
                }
                URL url = new URL(baseUrl);

                // Create the request to Moviesdb, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    Log.v(LOG_TAG, "Input stream null");
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    Log.v(LOG_TAG, "Input Length zero");
                    return null;
                }
                moviesJsonStr = buffer.toString();
                Log.v(LOG_TAG, "Movies Json String" + moviesJsonStr);
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }
            try {
                return getMoviesDataFromJson(moviesJsonStr);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Movie[] result) {

            if (result != null) {
                movieadapter.clear();
                for (Movie movie : result) {
                    if (movie != null) {
                        Log.v(LOG_TAG, "POST EXECUTE IMAGE URLS" + movie);
                        movieadapter.add(movie);
                    }
                }
                movieadapter.notifyDataSetChanged();
            }
        }
    }
}
