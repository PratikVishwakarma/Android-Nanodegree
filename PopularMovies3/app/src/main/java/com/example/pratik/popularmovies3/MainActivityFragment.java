package com.example.pratik.popularmovies3;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    String sortType = "popularity";
    ArrayList<Movie> movie;
    MoviesAdapter madapter;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        movie = new ArrayList<>();
        madapter = new MoviesAdapter(getActivity(), R.layout.grid_item, new ArrayList<Movie>());

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        GridView gridView = (GridView) rootView.findViewById(R.id.gridView_movies);
        gridView.setAdapter(madapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie item = (Movie) parent.getItemAtPosition(position);
                Intent intent = new Intent(getContext(), Movie_Details.class);
                intent.putExtra("title", item.getTitle());
                intent.putExtra("url",item.image_url);
                intent.putExtra("voteAvg",item.getRatings());
                intent.putExtra("release_date",item.getRelease_date());
                intent.putExtra("overview",item.getOverview());
                startActivity(intent);
            }
        });

        updateMovieList(sortType);
        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_items, menu);
    }

    private void updateMovieList(String sortType) {
        FetchPopularMovies fetchPopularMovies = new FetchPopularMovies();
        fetchPopularMovies.execute(sortType);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.sort_by_popularity) {
            sortType = "popularity";
            updateMovieList(sortType);
            return true;
        }
        if (item.getItemId() == R.id.sort_by_ratings) {
            sortType = "vote_average";
            updateMovieList(sortType);
        }
        return super.onOptionsItemSelected(item);
    }

    public void onStart() {
        super.onStart();
        updateMovieList("popularity");
    }


    public class FetchPopularMovies extends AsyncTask<String,Void,Movie[]> {
        HttpURLConnection urlConnection;
        BufferedReader bufferedReader;
        String movies_detail=null;
        @Override
        protected Movie[] doInBackground(String... params) {

            final String API_KEY="YOUR_KEY_HERE";
            String movieurl = "http://api.themoviedb.org/3/discover/movie?sort_by="+params[0]+".desc&api_key="+API_KEY;

            try{
                URL url=new URL(movieurl);
                urlConnection=(HttpURLConnection)url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                InputStream inputStream=urlConnection.getInputStream();
                if(inputStream==null){
                    return  null;
                }
                bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
                StringBuffer buffer=new StringBuffer();
                String line;
                while((line=bufferedReader.readLine())!=null){
                    buffer.append(line);
                }
                if(buffer.length()==0){
                    return null;
                }
                movies_detail=buffer.toString();
            }catch(Exception e){
                Log.e("MainAct",e.getMessage());
            }finally {
                if(urlConnection!=null){
                    urlConnection.disconnect();
                }
                try{
                    if(bufferedReader!=null){
                        bufferedReader.close();
                    }
                }catch (Exception e){
                    Log.e("MainAct",e.getMessage());
                }
            }
            return getMovieData(movies_detail);
        }
        private Movie[] getMovieData(String movies_detail) {
            final String RESULTS = "results";
            final String TITLE = "original_title";
            final String OVER_VIEW = "overview";
            final String POSTER_PATH = "poster_path";
            final String RELEASE_DATE = "release_date";
            final String RATINGS = "vote_average";
            Movie[] movies=null;
            try {
                JSONObject movie_json = new JSONObject(movies_detail);
                JSONArray movieArray=movie_json.getJSONArray(RESULTS);
                movies=new Movie[movieArray.length()];
                for(int i=0;i<movieArray.length();i++){
                    JSONObject movieObject=movieArray.getJSONObject(i);
                    Movie temp_movie=new Movie();
                    temp_movie.setTitle(movieObject.getString(TITLE));
                    temp_movie.setImage_base_url(movieObject.getString(POSTER_PATH));
                    temp_movie.setOverview(movieObject.getString(OVER_VIEW));
                    temp_movie.setRatings(movieObject.getDouble(RATINGS));
                    temp_movie.setRelease_date(movieObject.getString(RELEASE_DATE));
                    movies[i]=temp_movie;
                }
            }catch (Exception e){
                Log.e("main",e.getMessage());
            }
            return movies;
        }
        @Override
        protected void onPostExecute(Movie[] all_movies) {
            if(all_movies!=null) {
                movie.clear();
                madapter.clear();
                for(int i=0;i<all_movies.length;i++) {
                    movie.add(all_movies[i]);
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    madapter.addAll(movie);
                }
                madapter.notifyDataSetChanged();
            }
        }

    }
}
