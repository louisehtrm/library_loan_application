package com.example.louise_hartmann_3064148_ass1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ReturnActivity extends AppCompatActivity {

    ListView list_book;
    TextView no_book;

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
    ImageButton favorite_button;

    private ReturnActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return);

        list_book = findViewById(R.id.list_book);
        no_book = findViewById(R.id.no_book_text);

        library_button = findViewById(R.id.library_button);
        loan_button = findViewById(R.id.loan_button);
        favorite_button = findViewById(R.id.favorite_button);

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

        //if no books are loaned, don't need to display the list_book
        //else we turn the list to visible and the message to invisible
        if(loanNb != 0) {

            //create new values to store only the details of the book that have been loaned so the adapter only display those books
            //they took the size of the number of loaned book
            int[] myLoan = new int[loanNb];
            String[] myTitle = new String[loanNb];
            String[] myAuthor = new String[loanNb];
            String[] myGenre = new String[loanNb];
            int[] myImage = new int[loanNb];

            //fill those table with the nLoan table which knows which book has been loaned
            int j = 0;
            for(int i = 0; i < nLoan.length; i++){
                if(nLoan[i] == 1){
                    myLoan[j] = i;
                    myTitle[j] = nTitle[i];
                    myAuthor[j] = nAuthor[i];
                    myGenre[j] = nGenre[i];
                    myImage[j] = nImage[i];
                    j++;
                }
            }

            list_book.setVisibility(View.VISIBLE);
            no_book.setVisibility(View.INVISIBLE);

            //call MyReturnAdapter which return a view for each object in my ListView list_book
            //it only shows the loaned books
            MyReturnAdapter adapter = new MyReturnAdapter(this, myTitle, myAuthor, myGenre, myImage, 0);
            list_book.setAdapter(adapter);

            //handle what happen when the user click on a book of the list_book
            //in here the user can return a book
            list_book.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    final AlertDialog.Builder myReturnPopup = new AlertDialog.Builder(activity);
                    myReturnPopup.setTitle(nTitle[myLoan[position]]);
                    myReturnPopup.setMessage("Do you want to return this book ?");
                    myReturnPopup.setIcon(nImage[myLoan[position]]);

                    //Positive Button to return a book which will refresh the page to remove the book from the screen
                    myReturnPopup.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            nLoan[myLoan[position]] = 0;
                            loanNb--;

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
                    });

                    //Negative Button which don't do anything
                    myReturnPopup.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });

                    //display the dialog
                    myReturnPopup.show();
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