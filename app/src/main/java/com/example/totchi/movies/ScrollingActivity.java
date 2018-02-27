package com.example.totchi.movies;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.totchi.movies.Adapters.TrailerAdapter;
import com.example.totchi.movies.Model.Movie;
import com.example.totchi.movies.Model.Trailer;
import com.example.totchi.movies.Model.Values;
import com.example.totchi.movies.Presenter.TrailersPresenter;
import com.example.totchi.movies.View.MainView;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ScrollingActivity extends AppCompatActivity implements MainView {

    //Get views
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    MaterialFavoriteButton fab;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.movieImage)
    ImageView imageView;
    @BindView(R.id.rate)
    TextView rateTextView;
    @BindView(R.id.year)
    TextView year;
    @BindView(R.id.lang)
    TextView lang;
    @BindView(R.id.overView)
    TextView overview;
    @BindView(R.id.trailers)
    RecyclerView trailers;
    @BindView(R.id.tabs)
    TabLayout tabLayout;
    @BindView(R.id.dp)
    ProgressBar pd;

    //trailers presenter object
    TrailersPresenter presenter;

    //
//    //favourite movie
//    Movie favorite;
    //received intent
    Intent intent;
    //received objects m intent
    String name, yearD, language, rate, movieoverview, imagepath;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //receive coming intent and data
        intent = getIntent();
        id = intent.getExtras().getInt("id");
        name = intent.getStringExtra("name");
        yearD = intent.getStringExtra("year").substring(0, 4);
        language = intent.getStringExtra("language");
        rate = intent.getStringExtra("rate");
        movieoverview = intent.getStringExtra("overView");
        imagepath = intent.getStringExtra("image");
        presenter = new TrailersPresenter(ScrollingActivity.this, this, intent);

        /**
         *TrailerPrsenter takes 3 parameters
         * @param context
         * @param recyclerView
         * @param network interface
         *  @param id
         */


        //load image into image view
        Picasso.with(this)
                .load(new Values().IMAGE_URL_BASE_PATH + imagepath)
                .placeholder(R.drawable.loading)
                .error(R.drawable.ic_star_border)
                .into(imageView);

        collapsingToolbarLayout.setTitle(name);
        year.setText(yearD);
        lang.setText(language);
        rateTextView.setText(rate);
        overview.setText(movieoverview);

        //favourite button listener
        fab.setOnFavoriteChangeListener(
                new MaterialFavoriteButton.OnFavoriteChangeListener() {
                    @Override
                    public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {

                        if (favorite) {
                            //if user clicked favourite movie save it in database
                            presenter.Save(intent);
                            fab.setImageResource(R.drawable.ic_star);
                            Snackbar.make(buttonView, "Added to Favorite list", Snackbar.LENGTH_SHORT).show();
                        } else {
                            //if user clicked not favourite resource uncheck the star
                            //and delete the movie from favourite list (database)
                            fab.setImageResource(R.drawable.ic_star_border);
                            presenter.delete();
                            Snackbar.make(buttonView, "Removed from Favorite",
                                    Snackbar.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getText().toString().equalsIgnoreCase(getString(R.string.overView))) {
                    overview.setVisibility(View.VISIBLE);
                    trailers.setVisibility(View.GONE);
                } else {
                    overview.setVisibility(View.GONE);
                    trailers.setVisibility(View.VISIBLE);
                    trailers.setLayoutManager(new LinearLayoutManager(ScrollingActivity.this));
                    presenter.connectToGetMovies(getString(R.string.overView));
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }


    @Override
    public void onFail() {
        trailers.setVisibility(View.GONE);
        Snackbar.make(trailers, "Error fetching data.Check internet connection", Snackbar.LENGTH_LONG)
                .setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(ScrollingActivity.this, ScrollingActivity.class));
                    }
                }).show();
    }

    @Override
    public void showProgress() {
        pd.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        pd.setVisibility(View.GONE);
    }

    @Override
    public void showMoviesList(List<Movie> movies) {
    }

    @Override
    public void showTrailsList(List<Trailer> list) {
        trailers.setAdapter(new TrailerAdapter(list, this));

    }

}
