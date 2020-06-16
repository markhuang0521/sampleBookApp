package com.ming.androbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.ming.androbook.model.Book;
import com.ming.androbook.ui.BookRecyclerAdapter;
import com.ming.androbook.utils.BookDbUtils;

import java.util.List;

public class FavoriteBookActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private BookRecyclerAdapter bookRecyclerAdapter;
    private List<Book> favoriteBookLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_book);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initRecyclerView();
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.recycler_favorite_book);
        bookRecyclerAdapter = new BookRecyclerAdapter(this, BookDbUtils.FAVORITE_BOOKS_KEY);
        recyclerView.setAdapter(bookRecyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        favoriteBookLists = BookDbUtils.getInstance(this).getFavoriteBooks();

        bookRecyclerAdapter.setBookList(favoriteBookLists);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(FavoriteBookActivity.this, MainActivity.class));
        }


        return super.onOptionsItemSelected(item);
    }
}
