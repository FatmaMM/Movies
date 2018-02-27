package com.example.totchi.movies.Adapters;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.totchi.movies.Model.Trailer;
import com.example.totchi.movies.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
/*
*Trailer adapter. Used to load trailers data in the Trailer list.
 */
public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.mViewHolder> {

    List<Trailer> trailers;
    Context context;
    /**
     * Trailer Adapter constructor
     *
     * @param trailer_list
     * @param context
     */
    public TrailerAdapter(List<Trailer> trailer_list, Context context) {
        this.trailers = trailer_list;
        this.context = context;
    }

    @Override
    public mViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_item, parent, false);
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
    public void onBindViewHolder(mViewHolder holder, int position) {
        holder.mItem = trailers.get(position);
        holder.textView.setText(holder.mItem.getName());
    }

    @Override
    public int getItemCount() {
        return trailers.size();
    }

    public class mViewHolder extends RecyclerView.ViewHolder {
        /**
         * Defines cast recycler view row elements.
         */
        public Trailer mItem;
        @BindView(R.id.tr_name)
        TextView textView;

        public mViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            //open trailers with youtube
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + mItem.getKey()));
                    intent.putExtra("video_id", mItem.getKey());
                    context.startActivity(intent);
                }
            });
        }
    }
}
