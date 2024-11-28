package com.siddhantbhardwaj.movieapp;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.siddhantbhardwaj.movieapp.databinding.ActivityMainBinding;
import com.siddhantbhardwaj.movieapp.model.Movie;
import com.siddhantbhardwaj.movieapp.view.MovieAdapter;
import com.siddhantbhardwaj.movieapp.viewmodel.MainActivityViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Movie> movieArrayList;
    private MovieAdapter movieAdapter;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ActivityMainBinding activityMainBinding;
    private MainActivityViewModel viewModel;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.swipeLayout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        activityMainBinding = DataBindingUtil.setContentView(
                this,
                R.layout.activity_main
        );
        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        getPopularMovies();

    }

    private void getPopularMovies() {

        viewModel.getAllMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                movieArrayList = (ArrayList<Movie>) movies;
                displayMoviesInRecylerView();
            }
        });

    }

    @SuppressLint("ResourceAsColor")
    private void displayMoviesInRecylerView() {

        recyclerView = activityMainBinding.recyclerView;
        movieAdapter = new MovieAdapter(this,movieArrayList);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(movieAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        movieAdapter.notifyDataSetChanged();
        swipeRefreshLayout = activityMainBinding.swipeLayout;
        swipeRefreshLayout.setColorSchemeColors(R.color.black);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPopularMovies();
            }
        });


    }
}