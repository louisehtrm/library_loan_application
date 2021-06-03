package com.example.louise_hartmann_3064148_ass1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //the list with the book
    ListView list_book;

    //details of the books
    String[] nTitle = {"Pride and Prejudice", "Philosopher's Stone", "Chamber of Secrets", "Prisoner of Azkaban", "Goblet of Fire", "Order of the Phoenix", "Half-Blood Prince", "Deathly Hallows", "L'alliance des Trois", "Malronce", "Le Coeur de la Terre", "Entropia", "Oz", "Neverland", "Genèse"};
    String[] nAuthor = {"Jane Austen", "J.K Rowling", "J.K Rowling", "J.K Rowling", "J.K Rowling", "J.K Rowling", "J.K Rowling", "J.K Rowling", "Maxime Chattam", "Maxime Chattam", "Maxime Chattam", "Maxime Chattam", "Maxime Chattam", "Maxime Chattam","Maxime Chattam"};
    String[] nGenre = {"Romance", "Fantasy", "Fantasy", "Fantasy", "Fantasy", "Fantasy", "Fantasy", "Fantasy", "Fantasy", "Fantasy", "Fantasy", "Fantasy", "Fantasy", "Fantasy", "Fantasy"};
    int[] nImage = {R.drawable.pride_and_prejudice, R.drawable.harry_potter_1, R.drawable.harry_potter_2, R.drawable.harry_potter_3, R.drawable.harry_potter_4, R.drawable.harry_potter_5, R.drawable.harry_potter_6, R.drawable.harry_potter_7, R.drawable.alliance_des_trois_1, R.drawable.malronce_2, R.drawable.le_coeur_de_la_terre_3, R.drawable.entropia_4, R.drawable.oz_5, R.drawable.neverland_6, R.drawable.genese_7};

    //table to know which books has been add to favorite with a counter
    int[] nFav = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    int fav;

    //table to know which books has been loaned with a counter
    int[] nLoan = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    int loanNb = 0;

    //the different button which allows to go from an activity to another
    ImageButton loan_button;
    ImageButton return_button;
    ImageButton favorite_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //retrieval of the different view
        list_book = findViewById(R.id.list_book);
        loan_button = findViewById(R.id.loan_button);
        return_button = findViewById(R.id.return_button);
        favorite_button = findViewById(R.id.favorite_button);

        //test if we already intent value earlier
        //if not (first time we launch the app), don't need to intent any value
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            Intent intent = getIntent();
            nTitle = intent.getStringArrayExtra("title");
            nAuthor = intent.getStringArrayExtra("author");
            nGenre = intent.getStringArrayExtra("genre");
            nImage = intent.getIntArrayExtra("images");
            nLoan = intent.getIntArrayExtra("loan");
            loanNb = intent.getIntExtra("loan_nb", 0);
            nFav = intent.getIntArrayExtra("fav");
            fav = intent.getIntExtra("nb_fav", 0);
        }

        //call MyAdapter which return a view for each object in my ListView list_book
        //it shows when a book has been added to the favorite
        MyAdapter adapter = new MyAdapter(this, nTitle, nAuthor, nGenre, nImage, nLoan, nFav);
        list_book.setAdapter(adapter);

        //handle what happen when the user click on a book of the list_book
        //in here the user can add or remove a book from his favorite
        list_book.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //if the book is already fav, it removes it and refresh the activity to see the changes in the list_book
                if(nFav[position] == 1) {
                    nFav[position] = 0;
                    fav--;

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("title", nTitle);
                    intent.putExtra("author", nAuthor);
                    intent.putExtra("genre", nGenre);
                    intent.putExtra("images", nImage);
                    intent.putExtra("loan", nLoan);
                    intent.putExtra("loan_nb", loanNb);
                    intent.putExtra("nb_fav", fav);
                    intent.putExtra("fav", nFav);

                    startActivity(intent);
                    finish();
                }
                //else we add the book to his favorite and we refresh the activity
                else{
                    nFav[position] = 1;
                    fav++;

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("title", nTitle);
                    intent.putExtra("author", nAuthor);
                    intent.putExtra("genre", nGenre);
                    intent.putExtra("images", nImage);
                    intent.putExtra("loan", nLoan);
                    intent.putExtra("loan_nb", loanNb);
                    intent.putExtra("nb_fav", fav);
                    intent.putExtra("fav", nFav);

                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    //OnClick Button that allows to go into the LoanActivity by saving the main values
    public void LoanButton(View view) {
        Intent LoanActivity = new Intent(getApplicationContext(), LoanActivity.class);

        LoanActivity.putExtra("title", nTitle);
        LoanActivity.putExtra("author", nAuthor);
        LoanActivity.putExtra("genre", nGenre);
        LoanActivity.putExtra("images", nImage);
        LoanActivity.putExtra("loan", nLoan);
        LoanActivity.putExtra("loan_nb", loanNb);
        LoanActivity.putExtra("nb_fav", fav);
        LoanActivity.putExtra("fav", nFav);

        startActivity(LoanActivity);
        finish();
    }

    //OnClick Button that allows to go into the ReturnActivity by saving the main values
    public void ReturnButton(View view) {
        Intent ReturnActivity = new Intent(getApplicationContext(), ReturnActivity.class);

        ReturnActivity.putExtra("title", nTitle);
        ReturnActivity.putExtra("author", nAuthor);
        ReturnActivity.putExtra("genre", nGenre);
        ReturnActivity.putExtra("images", nImage);
        ReturnActivity.putExtra("loan", nLoan);
        ReturnActivity.putExtra("loan_nb", loanNb);
        ReturnActivity.putExtra("nb_fav", fav);
        ReturnActivity.putExtra("fav", nFav);

        startActivity(ReturnActivity);
        finish();
    }

    //OnClick Button that allows to go into the FavoriteActivity by saving the main values
    public void FavoriteButton(View view) {
        Intent FavoriteActivity = new Intent(getApplicationContext(), FavoriteActivity.class);

        FavoriteActivity.putExtra("title", nTitle);
        FavoriteActivity.putExtra("author", nAuthor);
        FavoriteActivity.putExtra("genre", nGenre);
        FavoriteActivity.putExtra("images", nImage);
        FavoriteActivity.putExtra("loan", nLoan);
        FavoriteActivity.putExtra("loan_nb", loanNb);
        FavoriteActivity.putExtra("nb_fav", fav);
        FavoriteActivity.putExtra("fav", nFav);

        startActivity(FavoriteActivity);
        finish();
    }
}
