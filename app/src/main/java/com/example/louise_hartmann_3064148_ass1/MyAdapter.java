package com.example.louise_hartmann_3064148_ass1;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyAdapter extends ArrayAdapter<String> {

    Context context;
    String[] rTitle;
    String[] rAuthor;
    String[] rGenre;
    int[] rImgs;
    int[] rLoan;
    int[] rFav;

    MyAdapter (Context c, String[] title, String[] author, String[] genre, int[] imgs, int[] loan, int[]fav) {
        super(c, R.layout.book_item, R.id.title, title);
        this.context = c;
        this.rTitle = title;
        this.rAuthor = author;
        this.rGenre = genre;
        this.rImgs = imgs;
        this.rLoan = loan;
        this.rFav = fav;
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
        ImageView fav = book_item.findViewById(R.id.fav_button);

        images.setImageResource(rImgs[position]);
        myTitle.setText(rTitle[position]);
        myAuthor.setText(rAuthor[position]);
        myGenre.setText(rGenre[position]);

        //if a book has been loaned, display "Loaned" in a blue color
        if(rLoan[position] == 1){
            available.setVisibility(View.VISIBLE);
            available.setTextColor(Color.parseColor("#133C6C"));
            available.setTextSize(14);
            available.setText("Loaned");
        }
        //else display nothing else
        else{
            available.setVisibility(View.INVISIBLE);
        }

        //if a book has been favored, display the full heart
        if(rFav[position] == 1)
            fav.setImageResource(R.drawable.unfav_button);
        //else display the empty heart
        else
            fav.setImageResource(R.drawable.fav_button);

        return book_item;
    }
}
