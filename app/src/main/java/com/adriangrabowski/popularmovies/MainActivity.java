package com.adriangrabowski.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private Context context;

    private JSONArray jsonArray;

    private MoviesAdapter moviesAdapter;
    private RecyclerView recyclerView;

    private List<Movie> movieList;

    private String sortingType;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);


        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setHasFixedSize(true);

        movieList = new ArrayList<>();

        moviesAdapter = new MoviesAdapter(movieList);

        recyclerView.setAdapter(moviesAdapter);





        context = getApplicationContext();


        sortingType = "popular";  //default

        populateList();


    }

    private void populateList() {

        if (isInternetConnectionAvailable()){

            movieList.clear();

            URL url = NetworkUtils.buildURL(this, sortingType );



            new MovieDBQueryTask().execute(url);

        }

        else {
            showToastMessageNoInternetConnection();

        }

    }

    private boolean isInternetConnectionAvailable(){

        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        return isConnected;

    }

    private void showToastMessageNoInternetConnection() {
        Toast toast = Toast.makeText(context, R.string.no_internet_connection_error_message, Toast.LENGTH_LONG);
        toast.show();
    }









    private class MovieDBQueryTask extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... urls) {
            URL searchUrl = urls[0];
            String searchResults = null;


            try {
                searchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);

            } catch (IOException e) {
                e.printStackTrace();
            }




            return searchResults;
        }



        @Override
        protected void onPostExecute(String s) {

            if (s != null && !s.equals("")) {

                try {
                    jsonArray = JSONMovieUtils.makeJSONObjectArray(s);


                } catch (JSONException e) {
                    e.printStackTrace();
                }



                for (int i = 0; i < jsonArray.length(); i++) {

                    try {
                        JSONObject movieJson = jsonArray.getJSONObject(i);
                        movieList.add(new Movie(movieJson, context));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }



                }


                moviesAdapter.notifyDataSetChanged();

            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.sort_by_popularity: sortingType = "popular"; populateList(); return true;
            case R.id.sort_by_rating: sortingType = "top_rated"; populateList(); return true;
            case R.id.show_favs: startShowFavouriteMoviesActivity(); return true;
            default: return super.onOptionsItemSelected(item);
        }


    }

    private void startShowFavouriteMoviesActivity() {

        Intent intent = new Intent(context, FavouriteMovies.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

        startActivity(intent);

    }

}
