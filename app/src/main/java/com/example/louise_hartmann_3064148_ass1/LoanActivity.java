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

public class LoanActivity extends AppCompatActivity {

    ListView list_book;

    String[] nTitle;
    String[] nAuthor;
    String[] nGenre;
    int[] nImage;

    int[] nFav;
    int fav;

    int[] nLoan;
    int loanNb;

    ImageButton library_button;
    ImageButton return_button;
    ImageButton favorite_button;

    private LoanActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan);

        list_book = findViewById(R.id.list_book);
        library_button = findViewById(R.id.library_button);
        return_button = findViewById(R.id.return_button);
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

        //call MyLoanAdapter which return a view for each object in my ListView list_book
        //it changes the view to show when a book is no longer available
        MyLoanAdapter adapter = new MyLoanAdapter(this, nTitle, nAuthor, nGenre, nImage, nLoan);
        list_book.setAdapter(adapter);

        //handle what happen when the user click on a book of the list_book
        //in here the user can loan a book
        list_book.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //if the book has already been loaned, the user can't re-loan it
                if (nLoan[position] == 1) {
                    //Popup to alert the user that he can't loan it once again
                    final AlertDialog.Builder myLoanFailPopup = new AlertDialog.Builder(activity);
                    myLoanFailPopup.setTitle(nTitle[position]);
                    myLoanFailPopup.setMessage("You already loaned this book");
                    myLoanFailPopup.setIcon(nImage[position]);
                    myLoanFailPopup.show();
                }

                else {
                    //if he has loaned less than 4 four, the user can loan a new one
                    if (loanNb < 4) {
                        final AlertDialog.Builder myLoanPopup = new AlertDialog.Builder(activity);
                        myLoanPopup.setTitle(nTitle[position]);
                        myLoanPopup.setMessage("Do you want to loan this book?");
                        myLoanPopup.setIcon(nImage[position]);

                        //Positive button which loan the book
                        myLoanPopup.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                nLoan[position] = 1;
                                loanNb += 1;

                                //refresh the activity to see the changes
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
                        });

                        //tNegative Button which don't do anything
                        myLoanPopup.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                        //display the dialog
                        myLoanPopup.show();
                    }

                    //he can't loan a new book if he has already loaned four
                    else {
                        final AlertDialog.Builder myLoanReturnPopup = new AlertDialog.Builder(activity);
                        myLoanReturnPopup.setTitle("You already loaned 4 books");
                        myLoanReturnPopup.setMessage("Do you want to return one ?");
                        myLoanReturnPopup.setIcon(R.drawable.warning_icon);

                        //if he want, he can return another book to loan the book he want to loan
                        myLoanReturnPopup.setPositiveButton("Return", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                //change to the ReturnActivity to return a book
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
                        });

                        //just do anything
                        myLoanReturnPopup.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });

                        //display the dialog
                        myLoanReturnPopup.show();
                    }
                }
            }
        });
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