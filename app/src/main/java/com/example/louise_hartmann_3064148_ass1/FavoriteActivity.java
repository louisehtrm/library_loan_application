package com.example.louise_hartmann_3064148_ass1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FavoriteActivity extends AppCompatActivity {

    ListView list_book;
    TextView no_fav;

    String[] nTitle;
    String[] nAuthor;
    String[] nGenre;
    int[] nImage;

    int[] nFav;
    int fav;

    int[] nLoan;
    int loanNb;

    ImageButton loan_button;
    ImageButton library_button;
    ImageButton return_button;

    private FavoriteActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        list_book = findViewById(R.id.list_book);
        no_fav = findViewById(R.id.no_fav);

        library_button = findViewById(R.id.library_button);
        loan_button = findViewById(R.id.loan_button);
        return_button = findViewById(R.id.return_button);

        this.activity = this;

        //retrieves values when changing activity
        Intent intent = getIntent();
        nTitle = intent.getStringArrayExtra("title");
        nAuthor = intent.getStringArrayExtra("author");
        nGenre = intent.getStringArrayExtra("genre");
        nImage = intent.getIntArrayExtra("images");
        nLoan = intent.getIntArrayExtra("loan");
        loanNb = intent.getIntExtra("loan_nb", 0);
        nFav = intent.getIntArrayExtra("fav");
        fav = intent.getIntExtra("nb_fav", 0);

        //if no books have been add to favorite, don't need to display the list_book
        //else we turn the list to visible and the message to invisible
        if(fav != 0) {

            list_book.setVisibility(View.VISIBLE);
            no_fav.setVisibility(View.INVISIBLE);

            //create new values to store only the details of the book that have been favored so the adapter only display those books
            //they took the size of the number of favored book
            int[] myFav = new int[fav];
            String[] myTitle = new String[fav];
            String[] myAuthor = new String[fav];
            String[] myGenre = new String[fav];
            int[] myImage = new int[fav];

            //fill those table with the nFav table which knows which book has been loaned
            int j = 0;
            for(int i = 0; i < nFav.length; i++){
                if(nFav[i] == 1){
                    myFav[j] = i;
                    myTitle[j] = nTitle[i];
                    myAuthor[j] = nAuthor[i];
                    myGenre[j] = nGenre[i];
                    myImage[j] = nImage[i];
                    j++;
                }
            }

            //call MyReturnAdapter the same used for the return activity
            //instead of showing the loaned books, it show the books that have beend add to favorite
            MyReturnAdapter adapter = new MyReturnAdapter(this, myTitle, myAuthor, myGenre, myImage, 1);
            list_book.setAdapter(adapter);

            //handle what happen when the user click on a book of the list_book
            //in here the user can remove a book from his favorite list
            list_book.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    final AlertDialog.Builder myFavPopup = new AlertDialog.Builder(activity);
                    myFavPopup.setTitle(nTitle[myFav[position]]);
                    myFavPopup.setMessage("You do want to remove this book from your favorites");
                    myFavPopup.setIcon(nImage[myFav[position]]);

                    //Positive Button which remove a book from the favorite and refresh the activity
                    myFavPopup.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            nFav[myFav[position]] = 0;
                            fav--;

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
                    });

                    //Negative Button which don't do anything
                    myFavPopup.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });

                    //display the dialog
                    myFavPopup.show();
                }
            });
        }
    }

    //OnClick Button that allows to go into the MainActivity by saving the main values
    public void LibraryButton(View view) {
        Intent MainActivity = new Intent(getApplicationContext(), MainActivity.class);

        MainActivity.putExtra("title", nTitle);
        MainActivity.putExtra("author", nAuthor);
        MainActivity.putExtra("genre", nGenre);
        MainActivity.putExtra("images", nImage);
        MainActivity.putExtra("loan", nLoan);
        MainActivity.putExtra("loan_nb", loanNb);
        MainActivity.putExtra("nb_fav", fav);
        MainActivity.putExtra("fav", nFav);

        startActivity(MainActivity);
        finish();
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
        Intent intent = new Intent(getApplicationContext(), ReturnActivity.class);

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