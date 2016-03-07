package com.example.pratik.popularmovies3;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Movie_Details extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie__details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        TextView title=(TextView)findViewById(R.id.title);
        TextView overview=(TextView)findViewById(R.id.overview);
        TextView release_date=(TextView)findViewById(R.id.release);
        TextView ratings=(TextView)findViewById(R.id.ratings);
        ImageView poster=(ImageView)findViewById(R.id.poster);

        title.setText("Title : "+getIntent().getExtras().getString("title"));
        overview.setText("Overview : "+getIntent().getExtras().getString("overview"));
        release_date.setText("Release Date : " + getIntent().getExtras().getString("release_date"));
        String final_url="http://image.tmdb.org/t/p/w780/"+getIntent().getExtras().getString("url");
        ratings.setText("Vote_avg : " + getIntent().getExtras().getDouble("voteAvg"));
        Picasso.with(this).load(final_url).into(poster);
    }

}
