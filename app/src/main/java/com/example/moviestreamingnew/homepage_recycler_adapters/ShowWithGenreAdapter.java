package com.example.moviestreamingnew.homepage_recycler_adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviestreamingnew.R;
import com.example.moviestreamingnew.ShowWithGenreParent;

import java.util.ArrayList;

public class ShowWithGenreAdapter extends RecyclerView.Adapter<ShowWithGenreAdapter.ShowViewHolder> {

    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();

    private ArrayList<ShowWithGenreParent> itemList;
    private Context context;

    public ShowWithGenreAdapter(ArrayList<ShowWithGenreParent> list, Context context){
        this.itemList = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(
                        R.layout.show_with_genre_recyclerview_layout,
                        parent, false);

        return new ShowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowWithGenreAdapter.ShowViewHolder holder, int position) {
        ShowWithGenreParent parentItem = itemList.get(position);

        holder.genreTitle.setText(parentItem.getTitle());

        Log.d("ShowWithGenreAdapter", "Title Set!! " + parentItem.getTitle());

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(
                holder
                        .cardView_scroll
                        .getContext(),
                LinearLayoutManager.HORIZONTAL,
                false);

        layoutManager
                .setInitialPrefetchItemCount(
                        parentItem
                                .getCards()
                                .size());

        CardImageAdapter childItemAdapter = new CardImageAdapter(parentItem.getCards(), context);

        holder.cardView_scroll.setLayoutManager(layoutManager);
        holder.cardView_scroll.setAdapter(childItemAdapter);
        holder.cardView_scroll.setRecycledViewPool(viewPool);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ShowViewHolder extends RecyclerView.ViewHolder {
        private TextView genreTitle;
        private RecyclerView cardView_scroll;

        public ShowViewHolder(final View itemView)
        {
            super(itemView);

            genreTitle
                    = itemView
                    .findViewById(
                            R.id.genre);
            cardView_scroll
                    = itemView
                    .findViewById(
                            R.id.cardView_scroll);
        }
    }
}
