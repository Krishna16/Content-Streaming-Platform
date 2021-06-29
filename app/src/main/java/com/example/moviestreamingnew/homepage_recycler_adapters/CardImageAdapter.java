package com.example.moviestreamingnew.homepage_recycler_adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.moviestreamingnew.CardImageChild;
import com.example.moviestreamingnew.R;
import com.example.moviestreamingnew.caching.ImageLoader;
import com.example.moviestreamingnew.repository.ShowsDatabase;
import com.example.moviestreamingnew.ui.description.DescriptionFragment;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CardImageAdapter extends RecyclerView.Adapter<CardImageAdapter.CardViewHolder> {

    private List<CardImageChild> itemList;
    private Context context;
    private ImageLoader imageLoader;

    public CardImageAdapter(List<CardImageChild> list, Context context){
        this.itemList = list;
        this.context = context;
        this.imageLoader = new ImageLoader(context);
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

        //Bitmap bmp = BitmapFactory.decodeByteArray(childItem.getImage(), 0, childItem.getImage().length);

        //image.setImageBitmap(Bitmap.createScaledBitmap(bmp, image.getWidth(), image.getHeight(), false));

        //holder.cardImage.setImageBitmap(Bitmap.createScaledBitmap(bmp, bmp.getWidth(), bmp.getHeight(), false));
        //holder.cardImage.setImageResource(R.drawable.bates_motel);

        /*Glide.with(context)
                .asBitmap()
                .load(childItem.getImage())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(holder.cardImage);*/

        Glide.with(context)
                .load(childItem.getImage())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(holder.cardImage);

        //imageLoader.DisplayImage(childItem.getImage(), holder.cardImage);

        holder.cardImage.setTag(childItem.getImage());

        holder.cardImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String [] elements = childItem.getPath().split("/");

                String show = elements[elements.length - 1];
                String industry = elements[elements.length - 2];
                String genre = elements[elements.length - 3];

                if (show.indexOf(".") > 0)
                    show = show.substring(0, show.lastIndexOf("."));

                ShowsDatabase showsDatabase = new ShowsDatabase(context);

                //description fragment will open from this method
                showsDatabase.getDetails(show, industry, genre, childItem.getImage());
            }
        });
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
