package com.ming.androbook;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ming.androbook.model.Book;
import com.ming.androbook.utils.BookDbUtils;

import java.util.ArrayList;
import java.util.List;

public class BookDetailActivity extends AppCompatActivity {
    private TextView tvBookTitle, tvBookAuthor, tvBookDesc, tvBookPage;
    private Button btnWishlist, btnFavorite, btnCurrentRead, btnAlreadyRead;
    private ImageView bookImage;
    private Book currentBook;
    private BookDbUtils bookDbUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        initView();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        bookDbUtils = BookDbUtils.getInstance(this);
        currentBook = getIntent().getParcelableExtra(Book.BOOK_OBJ_KEY);
        assert currentBook != null;
        setData(currentBook);
        wishlistBtnOnclick();
        alreadyReadBtnOnclick();
        favoriteBtnOnclick();
        currentReadBtnOnclick();
        ;


    }

    private void setData(Book book) {
        tvBookTitle.setText(book.getTitle());
        tvBookAuthor.setText(book.getAuthor());
        tvBookDesc.setText(book.getLongDesc());
        Glide.with(this).asBitmap().load(book.getImageUrl()).into(bookImage);
    }

    private void initView() {
        tvBookAuthor = findViewById(R.id.tv_detail_author);
        tvBookDesc = findViewById(R.id.tv_detail_desc);
        tvBookDesc.setMovementMethod(new ScrollingMovementMethod());

        tvBookTitle = findViewById(R.id.tv_detail_title);
        tvBookPage = findViewById(R.id.tv_detail_page);
        btnAlreadyRead = findViewById(R.id.btn_detail_already_read);
        btnWishlist = findViewById(R.id.btn_detail_wish_list);
        btnFavorite = findViewById(R.id.btn_detail_favorite);
        btnCurrentRead = findViewById(R.id.btn_detail_current_reading);
        bookImage = findViewById(R.id.iv_detail_image);
    }

    /**
     * enable and disable wishlist
     * add the book to the wishlist
     */
    private void wishlistBtnOnclick() {
        final ArrayList<Book> wishlist = bookDbUtils.getWishlist();
        Log.d("tag", "wishlistOnclick: " + wishlist.toString());
        if (bookDbUtils.findBook(currentBook, wishlist)) {
            btnWishlist.setEnabled(false);

        } else {
            btnWishlist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (bookDbUtils.addToWishlist(currentBook)) {
                        startActivity(new Intent(BookDetailActivity.this, WishLIstActivity.class));

                    } else {
                        Toast.makeText(BookDetailActivity.this, "adding failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void alreadyReadBtnOnclick() {
        final ArrayList<Book> alreadyReadBooks = bookDbUtils.getAlreadyReadBook();
        Log.d("tag", "wishlistOnclick: " + alreadyReadBooks.toString());
        if (bookDbUtils.findBook(currentBook, alreadyReadBooks)) {
            btnAlreadyRead.setEnabled(false);

        } else {
            btnAlreadyRead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (bookDbUtils.addToALreadyRead(currentBook)) {
                        startActivity(new Intent(BookDetailActivity.this, AlreadyReadBookActivity.class));

                    } else {
                        Toast.makeText(BookDetailActivity.this, "adding failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void favoriteBtnOnclick() {
        final ArrayList<Book> favoriteBooks = bookDbUtils.getFavoriteBooks();
        Log.d("tag", "wishlistOnclick: " + favoriteBooks.toString());
        if (bookDbUtils.findBook(currentBook, favoriteBooks)) {
            btnFavorite.setEnabled(false);

        } else {
            btnFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (bookDbUtils.addToFavorite(currentBook)) {
                        startActivity(new Intent(BookDetailActivity.this, FavoriteBookActivity.class));


                    } else {
                        Toast.makeText(BookDetailActivity.this, "adding failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void currentReadBtnOnclick() {
        final ArrayList<Book> currentBooks = bookDbUtils.getCurrentBooks();
        Log.d("tag", "wishlistOnclick: " + currentBooks.toString());
        if (bookDbUtils.findBook(currentBook, currentBooks)) {
            btnCurrentRead.setEnabled(false);

        } else {
            btnCurrentRead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (bookDbUtils.addToCurrentRead(currentBook)) {
                        startActivity(new Intent(BookDetailActivity.this, CurrentBookActivity.class));

                    } else {
                        Toast.makeText(BookDetailActivity.this, "adding failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(BookDetailActivity.this, MainActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }


}
