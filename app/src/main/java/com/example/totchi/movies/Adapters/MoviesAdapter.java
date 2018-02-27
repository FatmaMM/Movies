package com.example.totchi.movies.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.totchi.movies.Presenter.MainPresenter;
import com.example.totchi.movies.Model.Movie;
import com.example.totchi.movies.Presenter.PresenterInterface;
import com.example.totchi.movies.R;
import com.example.totchi.movies.ScrollingActivity;
import com.example.totchi.movies.Model.Values;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/*
*Movie adapter. Used to load movies data in the movies list.
 */
public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.mViewHolder> {
    List<Movie> list;
    private Context context;
    PresenterInterface presenter;

    /**
     * Movies Adapter constructor
     *
     * @param movies  list
     * @param context
     */
    public MoviesAdapter(List<Movie> movies, Context context) {
        this.context = context;
        this.list = movies;
    }


    @Override
    public mViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        mViewHolder viewHolder = new mViewHolder(view);
        return viewHolder;
    }

    /**
     * Bind a View that displays the data at the specified position in the data set.
     *
     * @param position The position of the item within the adapter's data set of the item whose view we want.
     * @param holder   view to reuse, if possible.
     */

    @Override
    public void onBindViewHolder(final mViewHolder holder, final int position) {
        holder.mItem = list.get(position);
        /**
         * picasso library to load the image from uri path to image view
         */
        Picasso.with(context)
                .load(new Values().IMAGE_URL_BASE_PATH + holder.mItem.getPosterPath())
                .placeholder(R.drawable.loading)
                .error(R.drawable.ic_star_border)
                .into(holder.imageView);
        holder.textView.setText(holder.mItem.getTitle());
        holder.rate.setText(String.valueOf(holder.mItem.getVoteAverage()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class mViewHolder extends RecyclerView.ViewHolder {
        /**
         * Defines cast recycler view row elements.
         */
        public Movie mItem;

        @BindView(R.id.item_image)
        ImageView imageView;
        @BindView(R.id.item_name)
        TextView textView;
        @BindView(R.id.item_ranting)
        TextView rate;

        public mViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            /** open details activity and pass values using intent
             */
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ScrollingActivity.class);
                    presenter = new MainPresenter(context);
                    presenter.onItemClicked(intent, mItem);
                }
            });
        }
    }

}
