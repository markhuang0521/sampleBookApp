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

public class AlreadyReadBookActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private BookRecyclerAdapter bookRecyclerAdapter;
    private List<Book> AlreadyReadBookLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_already_read_book);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initRecyclerView();
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.recycler_already_read_book);
        bookRecyclerAdapter = new BookRecyclerAdapter(this, BookDbUtils.ALREADY_READ_BOOKS_KEY);
        recyclerView.setAdapter(bookRecyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        AlreadyReadBookLists = BookDbUtils.getInstance(this).getAlreadyReadBook();

        bookRecyclerAdapter.setBookList(AlreadyReadBookLists);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(AlreadyReadBookActivity.this, MainActivity.class));
        }


        return super.onOptionsItemSelected(item);
    }
}
