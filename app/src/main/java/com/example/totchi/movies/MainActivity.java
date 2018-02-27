package com.example.totchi.movies;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.totchi.movies.Adapters.MoviesAdapter;
import com.example.totchi.movies.Model.Favouites.FavoriteDbHelper;
import com.example.totchi.movies.Model.Movie;
import com.example.totchi.movies.Model.Trailer;
import com.example.totchi.movies.Presenter.MainPresenter;
import com.example.totchi.movies.Presenter.PresenterInterface;
import com.example.totchi.movies.View.MainView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements MainView {

    //Find views
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.progressbar)
    ProgressBar pb;
    @BindView(R.id.error)
    TextView errortext;
    @BindView(R.id.refresh)
    Button refresh;

    PresenterInterface presenter;
    FavoriteDbHelper favoriteDbHelper;
    List<Movie> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        presenter = new MainPresenter(MainActivity.this, this);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        presenter.connectToGetMovies(getString(R.string.popular));
    }

    /**
     * Initialize the contents of the Activity's standard options menu.
     * This is only called once, the first time the options menu is displayed.
     *
     * @param menu the options menu in which we place our items.
     * @return You must return true for the menu to be displayed; if you return false it will not be shown.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    /**
     * This hook is called whenever an item in our options menu is selected.
     *
     * @param item The menu item that was selected.
     * @return Return false to allow normal menu processing to proceed, true to consume it here.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            /*Get popular Movies*/
            case R.id.popular: {
                presenter.connectToGetMovies(getString(R.string.popular));
            }
            break;
            /* Get top rated Movies*/
            case R.id.topRated: {
                getSupportActionBar().setTitle(getString(R.string.toprated));
                presenter.connectToGetMovies(getString(R.string.toprated));
            }
            break;
            /* Get my favourite Movies*/
            case R.id.fav: {
                getSupportActionBar().setTitle(getString(R.string.fav));
                favoriteDbHelper = new FavoriteDbHelper(this);
                List<Movie> list = favoriteDbHelper.getAllFavorite();
                recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
                recyclerView.setAdapter(new MoviesAdapter(list, this));
            }
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFail() {
        recyclerView.setVisibility(View.GONE);
        errortext.setVisibility(View.VISIBLE);
        refresh.setVisibility(View.VISIBLE);
        errortext.setText("Check your internet connection and try again");

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh();
            }
        });

        Snackbar.make(recyclerView, "Error fetching data", Snackbar.LENGTH_LONG)
                .setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        refresh();
                    }
                }).show();
    }

    void refresh() {
        startActivity(new Intent(MainActivity.this, MainActivity.class));
    }

    @Override
    public void showProgress() {
        pb.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        pb.setVisibility(View.GONE);
    }

    @Override
    public void showMoviesList(List<Movie> list) {
        recyclerView.setAdapter(new MoviesAdapter(list, MainActivity.this));
    }

    @Override
    public void showTrailsList(List<Trailer> list) {

    }


}
