package com.example.moviecatalog;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieAdapter.OnItemClickListener {
    private RecyclerView recyclerView;
    private MovieAdapter adapter;
    private MovieDatabaseHelper dbHelper;
    private TextView emptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new MovieDatabaseHelper(this);
        recyclerView = findViewById(R.id.recyclerViewMovies);
        emptyView = findViewById(R.id.tvEmptyView);
        FloatingActionButton fabAdd = findViewById(R.id.fabAdd);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddEditMovieActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadMovies();
    }

    private void loadMovies() {
        List<Movie> movies = dbHelper.getAllMovies();
        if (movies.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
            adapter = new MovieAdapter(movies, this);
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void onItemClick(Movie movie) {
        Intent intent = new Intent(MainActivity.this, MovieDetailActivity.class);
        intent.putExtra("MOVIE_ID", movie.getId());
        startActivity(intent);
    }

    @Override
    public void onEditClick(Movie movie) {
        Intent intent = new Intent(MainActivity.this, AddEditMovieActivity.class);
        intent.putExtra("MOVIE_ID", movie.getId());
        startActivity(intent);
    }

    @Override
    public void onDeleteClick(Movie movie) {
        new AlertDialog.Builder(this)
                .setTitle("Удаление")
                .setMessage("Вы действительно хотите удалить фильм \"" + movie.getTitle() + "\"?")
                .setPositiveButton("Да", (dialog, which) -> {
                    dbHelper.deleteMovie(movie.getId());
                    loadMovies();
                    Toast.makeText(this, "Фильм удален", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Отмена", null)
                .show();
    }
}