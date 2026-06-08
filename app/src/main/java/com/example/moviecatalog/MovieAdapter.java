package com.example.moviecatalog;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private List<Movie> movieList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Movie movie);
        void onEditClick(Movie movie);
        void onDeleteClick(Movie movie);
    }

    public MovieAdapter(List<Movie> movieList, OnItemClickListener listener) {
        this.movieList = movieList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movieList.get(position);
        holder.titleText.setText(movie.getTitle());
        holder.genreText.setText(movie.getGenre() + " • " + movie.getYear());
        holder.ratingText.setText("★ " + movie.getRating());

        holder.itemView.setOnClickListener(v -> listener.onItemClick(movie));
        holder.editButton.setOnClickListener(v -> listener.onEditClick(movie));
        holder.deleteButton.setOnClickListener(v -> listener.onDeleteClick(movie));
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    static class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView titleText, genreText, ratingText;
        View editButton, deleteButton;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.tvMovieTitle);
            genreText = itemView.findViewById(R.id.tvMovieGenre);
            ratingText = itemView.findViewById(R.id.tvMovieRating);
            editButton = itemView.findViewById(R.id.btnEdit);
            deleteButton = itemView.findViewById(R.id.btnDelete);
        }
    }
}