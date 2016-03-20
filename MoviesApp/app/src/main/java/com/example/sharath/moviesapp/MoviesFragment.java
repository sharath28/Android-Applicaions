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


/**
 * Encapsulates fetching the forecast and displaying it as a {@link ListView} layout.
 */
public class MoviesFragment extends Fragment {
    static final String SORT_POPULAR = "popular";
    static final String SORT_RATING = "top_rated";
    public interface Callback {
        /**
         * DetailFragmentCallback for when an item has been selected.
         */
        public void onItemSelected(Movie movie);
    }

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
            FetchMoviesTask moviesTask = new FetchMoviesTask();
            moviesTask.execute(SORT_RATING);
            return true;
        } else if (id == R.id.action_sort_popular) {
            FetchMoviesTask moviesTask = new FetchMoviesTask();
            moviesTask.execute(SORT_POPULAR);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private ImageListAdapter movieadapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final String[] MoviesList = {
                "http://image.tmdb.org/t/p/w185/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg",
                "http://image.tmdb.org/t/p/w185/811DjJTon9gD6hZ8nCjSitaIXFQ.jpg",
                "http://image.tmdb.org/t/p/w185/inVq3FRqcYIRl2la8iZikYYxFNR.jpg",
                "http://image.tmdb.org/t/p/w185/kqjL17yufvn9OVLyXYpvtyrFfak.jpg",
                "http://image.tmdb.org/t/p/w342/sM33SANp9z6rXW8Itn7NnG1GOEs.jpg",
                "http://image.tmdb.org/t/p/w342/hE24GYddaxB9MVZl1CaiI86M3kp.jpg",
                "http://image.tmdb.org/t/p/w342/nN4cEJMHJHbJBsp3vvvhtNWLGqg.jpg",
                "http://image.tmdb.org/t/p/w185/kdXCb1Km4r7Om2G2uvYZPQJy4wG.jpg",
                "http://image.tmdb.org/t/p/w185/y31QB9kn3XSudA15tV7UWQ9XLuW.jpg",

        };

//        ArrayList<Movie[]> mlist = new ArrayList<Movie>(Arrays.<Movie[]>asList(mlist));
//        Movie[] mlist = {
//                new Movie("244786","http://image.tmdb.org/t/p/w185/lIv1QinFqz4dlp5U4lQ6HaiskOZ.jpg","Whiplash","2014-10-10","8.36","Under the direction of a ruthless instructor, a talented young drummer begins to pursue perfection at any cost, even his humanity.")
//        };
//        Log.v(null,"Initial url string"+MoviesList);
//        List<String> finalmovieslist = new ArrayList<String>(Arrays.asList(MoviesList));
//        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
//        Context context = getActivity();
//        GridView gridView = (GridView) rootView.findViewById(R.id.grid_view_movies);
//        movieadapter = new ImageListAdapter(context, (ArrayList<String>) finalmovieslist);
//        gridView.setAdapter(movieadapter);
//
//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String forecast = (String) movieadapter.getItem(position);
//               Toast.makeText(getActivity(), "Toast message"+forecast, Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(getActivity(), DetailActivity.class)
//                        .putExtra(Intent.EXTRA_TEXT, forecast);
//                startActivity(intent);
//            }
//
//        });
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        Context context = getActivity();
        movieadapter = new ImageListAdapter(context, new ArrayList<Movie>());

        // Get a reference to the GridView, and attach this adapter to it.
        GridView gridView = (GridView) rootView.findViewById(R.id.grid_view_movies);
        gridView.setAdapter(movieadapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie movie = movieadapter.getItem(position);
                Toast.makeText(getActivity(), "Movie title:" + movie.title+" Movie Overview: "+movie.overview, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity(), DetailActivity.class)
                        .putExtra("key", movie);
               startActivity(intent);
//                ((Callback) getActivity())
//                        .onItemSelected(movie);
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
                float rating = (float) singlemovie.get("vote_average");
                String overview = singlemovie.getString("overview");
                String final_image_url = basic_url+image_url;
                resultStrs[i] = new Movie(id,final_image_url,title,release_date,rating,overview);
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

            // Will contain the raw JSON response as a string.
            String moviesJsonStr = null;
            String baseUrl = null;
            try {
                // Construct the URL for the Moviesdb query
                if("popular".equals(params[0])) {
                    baseUrl = "http://api.themoviedb.org/3/movie/popular?api_key=08eff0c57313416b04009b2ccf767b8d";
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
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    Log.v(LOG_TAG, "Input Length zero");
                    return null;
                }
                moviesJsonStr = buffer.toString();
                Log.v(LOG_TAG, "Movies Json String" + moviesJsonStr);
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
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
//                Log.v(LOG_TAG, "POST EXECUTE" + result.toString());
//                movieadapter.addAll(Arrays.asList(result));
//                }
//                for (String movie : result) {
//                    if (movie!=null) {
//                        Log.v(LOG_TAG, "POST EXECUTE IMAGE URLS" + movie);
//                        movieadapter.clear();
//                        movieadapter.add(movie);
//                    }
            if (result != null) {
                movieadapter.clear();
//                Log.v(LOG_TAG,"Result url string"+result);
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
