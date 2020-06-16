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

public class AllBooksActivity extends AppCompatActivity {
    private RecyclerView recyclerViewAllBooks;
    private BookRecyclerAdapter bookRecyclerAdapter;
    private List<Book> AllBookLists;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_books);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initRecyclerView();

    }

    private void initRecyclerView() {
        recyclerViewAllBooks = findViewById(R.id.recycler_all_books);
        bookRecyclerAdapter = new BookRecyclerAdapter(this, BookDbUtils.ALL_BOOK_KEY);
        recyclerViewAllBooks.setAdapter(bookRecyclerAdapter);
        recyclerViewAllBooks.setLayoutManager(new LinearLayoutManager(this));
        AllBookLists = BookDbUtils.getInstance(this).getAllBooks();

        bookRecyclerAdapter.setBookList(AllBookLists);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(AllBooksActivity.this, MainActivity.class));
        }


        return super.onOptionsItemSelected(item);
    }

}
