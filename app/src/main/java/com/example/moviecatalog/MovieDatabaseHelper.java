package com.example.moviecatalog;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class MovieDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MovieCatalog.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_MOVIES = "movies";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_GENRE = "genre";
    private static final String COLUMN_RATING = "rating";
    private static final String COLUMN_YEAR = "year";
    private static final String COLUMN_DESCRIPTION = "description";

    public MovieDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_MOVIES + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT NOT NULL, " +
                COLUMN_GENRE + " TEXT, " +
                COLUMN_RATING + " REAL, " +
                COLUMN_YEAR + " INTEGER, " +
                COLUMN_DESCRIPTION + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIES);
        onCreate(db);
    }

    // CREATE
    public long insertMovie(Movie movie) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, movie.getTitle());
        values.put(COLUMN_GENRE, movie.getGenre());
        values.put(COLUMN_RATING, movie.getRating());
        values.put(COLUMN_YEAR, movie.getYear());
        values.put(COLUMN_DESCRIPTION, movie.getDescription());
        return db.insert(TABLE_MOVIES, null, values);
    }

    // READ (All)
    public List<Movie> getAllMovies() {
        List<Movie> movieList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_MOVIES, null);
        if (cursor.moveToFirst()) {
            do {
                Movie movie = new Movie(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GENRE)),
                        cursor.getFloat(cursor.getColumnIndexOrThrow(COLUMN_RATING)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_YEAR)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION))
                );
                movieList.add(movie);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return movieList;
    }

    // READ (One)
    public Movie getMovieById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_MOVIES, null, COLUMN_ID + "=?", new String[]{String.valueOf(id)}, null, null, null);
        Movie movie = null;
        if (cursor.moveToFirst()) {
            movie = new Movie(
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GENRE)),
                    cursor.getFloat(cursor.getColumnIndexOrThrow(COLUMN_RATING)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_YEAR)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION))
            );
        }
        cursor.close();
        return movie;
    }

    // UPDATE
    public int updateMovie(Movie movie) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, movie.getTitle());
        values.put(COLUMN_GENRE, movie.getGenre());
        values.put(COLUMN_RATING, movie.getRating());
        values.put(COLUMN_YEAR, movie.getYear());
        values.put(COLUMN_DESCRIPTION, movie.getDescription());
        return db.update(TABLE_MOVIES, values, COLUMN_ID + "=?", new String[]{String.valueOf(movie.getId())});
    }

    // DELETE
    public void deleteMovie(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MOVIES, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }
}