package com.example.moviecatalog;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddEditMovieActivity extends AppCompatActivity {
    private EditText etTitle, etGenre, etRating, etYear, etDescription;
    private Button btnSave;
    private MovieDatabaseHelper dbHelper;
    private int movieId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_movie);

        dbHelper = new MovieDatabaseHelper(this);
        etTitle = findViewById(R.id.etTitle);
        etGenre = findViewById(R.id.etGenre);
        etRating = findViewById(R.id.etRating);
        etYear = findViewById(R.id.etYear);
        etDescription = findViewById(R.id.etDescription);
        btnSave = findViewById(R.id.btnSave);

        if (getIntent().hasExtra("MOVIE_ID")) {
            movieId = getIntent().getIntExtra("MOVIE_ID", -1);
            setTitle("Редактировать фильм");
            loadMovieData();
        } else {
            setTitle("Добавить фильм");
        }

        btnSave.setOnClickListener(v -> saveMovie());
    }

    private void loadMovieData() {
        Movie movie = dbHelper.getMovieById(movieId);
        if (movie != null) {
            etTitle.setText(movie.getTitle());
            etGenre.setText(movie.getGenre());
            etRating.setText(String.valueOf(movie.getRating()));
            etYear.setText(String.valueOf(movie.getYear()));
            etDescription.setText(movie.getDescription());
        }
    }

    private void saveMovie() {
        String title = etTitle.getText().toString().trim();
        String genre = etGenre.getText().toString().trim();
        String ratingStr = etRating.getText().toString().trim();
        String yearStr = etYear.getText().toString().trim();
        String description = etDescription.getText().toString().trim();

        // Проверка пользовательского ввода (обязательное требование)
        if (TextUtils.isEmpty(title)) {
            etTitle.setError("Введите название фильма (обязательно)");
            return;
        }

        float rating = 0;
        if (!TextUtils.isEmpty(ratingStr)) {
            try { rating = Float.parseFloat(ratingStr); } catch (NumberFormatException e) { rating = 0; }
        }

        int year = 0;
        if (!TextUtils.isEmpty(yearStr)) {
            try { year = Integer.parseInt(yearStr); } catch (NumberFormatException e) { year = 0; }
        }

        Movie movie = new Movie(movieId, title, genre, rating, year, description);

        if (movieId == -1) {
            dbHelper.insertMovie(movie);
            Toast.makeText(this, "Фильм добавлен", Toast.LENGTH_SHORT).show();
        } else {
            dbHelper.updateMovie(movie);
            Toast.makeText(this, "Фильм обновлен", Toast.LENGTH_SHORT).show();
        }
        finish();
    }
}