package com.siddhantbhardwaj.movieapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.siddhantbhardwaj.movieapp.model.Movie;
import com.siddhantbhardwaj.movieapp.model.MovieRepository;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {

    private MovieRepository movieRepository;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        this.movieRepository = new MovieRepository(application);
    }

    public LiveData<List<Movie>> getAllMovies(){
        return movieRepository.getMovies();
    }

}
