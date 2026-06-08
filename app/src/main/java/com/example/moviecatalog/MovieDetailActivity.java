package com.example.moviecatalog;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MovieDetailActivity extends AppCompatActivity {
    private TextView tvTitle, tvGenre, tvRating, tvYear, tvDescription;
    private Button btnEdit, btnDelete;
    private MovieDatabaseHelper dbHelper;
    private int movieId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        dbHelper = new MovieDatabaseHelper(this);
        tvTitle = findViewById(R.id.tvDetailTitle);
        tvGenre = findViewById(R.id.tvDetailGenre);
        tvRating = findViewById(R.id.tvDetailRating);
        tvYear = findViewById(R.id.tvDetailYear);
        tvDescription = findViewById(R.id.tvDetailDescription);
        btnEdit = findViewById(R.id.btnDetailEdit);
        btnDelete = findViewById(R.id.btnDetailDelete);

        movieId = getIntent().getIntExtra("MOVIE_ID", -1);
        loadMovieDetails();

        btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(MovieDetailActivity.this, AddEditMovieActivity.class);
            intent.putExtra("MOVIE_ID", movieId);
            startActivity(intent);
            finish();
        });

        btnDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Удаление")
                    .setMessage("Вы действительно хотите удалить этот фильм?")
                    .setPositiveButton("Да", (dialog, which) -> {
                        dbHelper.deleteMovie(movieId);
                        Toast.makeText(this, "Фильм удален", Toast.LENGTH_SHORT).show();
                        finish();
                    })
                    .setNegativeButton("Отмена", null)
                    .show();
        });
    }

    private void loadMovieDetails() {
        Movie movie = dbHelper.getMovieById(movieId);
        if (movie != null) {
            tvTitle.setText(movie.getTitle());
            tvGenre.setText("Жанр: " + (movie.getGenre().isEmpty() ? "Не указан" : movie.getGenre()));
            tvRating.setText("Рейтинг: " + (movie.getRating() > 0 ? movie.getRating() : "Не указан"));
            tvYear.setText("Год: " + (movie.getYear() > 0 ? movie.getYear() : "Не указан"));
            tvDescription.setText(movie.getDescription().isEmpty() ? "Описание отсутствует" : movie.getDescription());
        }
    }
}