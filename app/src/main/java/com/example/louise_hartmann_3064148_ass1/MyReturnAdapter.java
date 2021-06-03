package com.example.louise_hartmann_3064148_ass1;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyReturnAdapter extends ArrayAdapter<String> {
    Context context;
    String[] rTitle;
    String[] rAuthor;
    String[] rGenre;
    int[] rImgs;
    int rType;

    MyReturnAdapter (Context c, String[] title, String[] author, String[] genre, int[] imgs, int type) {
        super(c, R.layout.book_item, R.id.title, title);
        this.context = c;
        this.rTitle = title;
        this.rAuthor = author;
        this.rGenre = genre;
        this.rImgs = imgs;
        this.rType = type;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View book_item = layoutInflater.inflate(R.layout.book_item, parent, false);
        ImageView images = book_item.findViewById(R.id.image_book);
        TextView myTitle = book_item.findViewById(R.id.title);
        TextView myAuthor = book_item.findViewById(R.id.author);
        TextView myGenre = book_item.findViewById(R.id.genre);
        TextView available = book_item.findViewById(R.id.available);

        images.setImageResource(rImgs[position]);
        myTitle.setText(rTitle[position]);
        myAuthor.setText(rAuthor[position]);
        myGenre.setText(rGenre[position]);

        //if we use this adapter to return a book, we display "Return" in a blue color
        if(rType == 0){
            available.setVisibility(View.VISIBLE);
            available.setTextColor( Color.parseColor("#133C6C"));
            available.setText("Return");
        }

        //else if it's for the favorite activity, we display "Favorite" in a red color
        else{
            available.setVisibility(View.VISIBLE);
            available.setTextColor(Color.parseColor("#8B0000"));
            available.setText("Favorite");
        }

        return book_item;
    }
}
