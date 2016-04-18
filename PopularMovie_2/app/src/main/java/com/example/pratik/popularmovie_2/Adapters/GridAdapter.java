package com.example.pratik.popularmovie_2.Adapters;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;

import com.example.pratik.popularmovie_2.MoviesFragment;
import com.example.pratik.popularmovie_2.R;
import com.squareup.picasso.Picasso;

/**
 * Created by pratik on 7/4/16.
 */
public class GridAdapter extends CursorAdapter {

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public GridAdapter(Context context , Cursor cursor , int flags){
        super(context,cursor,flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        String url=cursor.getString(MoviesFragment.MOVIE_PATH);
        ImageView image=(ImageView)view.findViewById(R.id.image1);
        Picasso.with(context).load(url).into(image);
    }
}
