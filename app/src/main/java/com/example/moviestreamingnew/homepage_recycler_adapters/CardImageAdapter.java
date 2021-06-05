package com.example.moviestreamingnew.homepage_recycler_adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviestreamingnew.CardImageChild;
import com.example.moviestreamingnew.R;

import java.util.List;

public class CardImageAdapter extends RecyclerView.Adapter<CardImageAdapter.CardViewHolder> {

    private List<CardImageChild> itemList;

    public CardImageAdapter(List<CardImageChild> list){
        this.itemList = list;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(
                        R.layout.cardview_scroll_with_genre,
                        parent, false);

        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardImageAdapter.CardViewHolder holder, int position) {
        CardImageChild childItem = itemList.get(position);

        Bitmap bmp = BitmapFactory.decodeByteArray(childItem.getImage(), 0, childItem.getImage().length);

        //image.setImageBitmap(Bitmap.createScaledBitmap(bmp, image.getWidth(), image.getHeight(), false));

        holder.cardImage.setImageBitmap(Bitmap.createScaledBitmap(bmp, bmp.getWidth(), bmp.getHeight(), false));
        //holder.cardImage.setImageResource(R.drawable.bates_motel);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder{
        private ImageView cardImage;

        public CardViewHolder(View itemView){
            super(itemView);
            this.cardImage = itemView.findViewById(R.id.cardView_image);
        }
    }
}
