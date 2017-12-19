package com.adriangrabowski.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by Adrian on 04/10/2017.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {

    private List<Movie> movieList;
    private Context context;

    public MoviesAdapter(List<Movie> mL) {

        movieList = mL;


    }


    @Override
    public MoviesAdapter.MoviesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        int layoutIdListItem = R.layout.recyclerview_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdListItem, parent, false);
        MoviesViewHolder moviesViewHolder = new MoviesViewHolder(view);


        return moviesViewHolder;
    }

    @Override
    public void onBindViewHolder(MoviesAdapter.MoviesViewHolder holder, int position) {
        Movie movie = movieList.get(position);


        Picasso.with(context).load(movie.getPosterUrl())
                .into(holder.posterImageView);

        holder.posterImageView.setTag(movie);

    }

    @Override
    public int getItemCount() {

        if (movieList == null) {
            return 0;
        }

        return movieList.size();
    }

    public class MoviesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView posterImageView;

        public MoviesViewHolder(View itemView) {
            super(itemView);
            posterImageView = (ImageView) itemView.findViewById(R.id.poster_image_view);
            posterImageView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            Movie movie = null;

            movie = (Movie) view.getTag();


            Intent intent = new Intent(context, DetailActivity.class);

            intent.putExtra("MOVIE", movie);

            context.startActivity(intent);


        }
    }
}


