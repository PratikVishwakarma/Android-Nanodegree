package com.example.pratik.popularmovie_2;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class Detail_Movie extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail__movie);
        if(savedInstanceState==null){
            Detail_MovieFragment fragment=new Detail_MovieFragment();
            Bundle temp=new Bundle();
            temp.putParcelable("movie",getIntent().getParcelableExtra("movie"));
            fragment.setArguments(temp);
            getSupportFragmentManager().beginTransaction().
                    add(R.id.detail_layout,fragment).commit();
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
}
