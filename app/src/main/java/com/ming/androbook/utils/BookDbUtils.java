package com.ming.androbook.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ming.androbook.model.Book;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class BookDbUtils {
    private static BookDbUtils instance;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Gson gson = new Gson();


    public static final String SHARE_PREFERENCES_KEY = "share preference";
    public static final String FAVORITE_BOOKS_KEY = "favoritebooks";
    public static final String CURRENT_BOOKS_KEY = "currentbooks";
    public static final String ALREADY_READ_BOOKS_KEY = "alreadyreadbooks";
    public static final String WISHLIST_KEY = "wishlist";
    public static final String ALL_BOOK_KEY = "allbooks";

    private BookDbUtils(Context context) {
        sharedPreferences = context.getSharedPreferences(BookDbUtils.SHARE_PREFERENCES_KEY, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        if (null == getAllBooks()) {
            initData();
        }
        if (null == getAlreadyReadBook()) {
            editor.putString(ALREADY_READ_BOOKS_KEY, gson.toJson(new ArrayList<Book>()));
            editor.apply();

        }
        if (null == getCurrentBooks()) {
            editor.putString(CURRENT_BOOKS_KEY, gson.toJson(new ArrayList<Book>()));
            editor.apply();

        }
        if (null == getFavoriteBooks()) {
            editor.putString(FAVORITE_BOOKS_KEY, gson.toJson(new ArrayList<Book>()));
            editor.apply();

        }

        if (null == getWishlist()) {
            editor.putString(WISHLIST_KEY, gson.toJson(new ArrayList<Book>()));
            editor.apply();

        }


    }

    private void initData() {
        ArrayList<Book> bookArrayList = new ArrayList<>();

        bookArrayList.add(new Book(1, "IQ84", "Mark", 100,
                "https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1483103331i/10357575._UY453_SS453_.jpg",
                "a puzzled adventure",
                "A young woman named Aomame notice puzzling  sdasdasdsadasdsa discrepancies in the world around her sadsadsadsdasddsfdsafdsfafadfsafdsafdsafdffdasfdsdafadsfasfdasfdfadsafdsafasdfdsfdasfdsafdsfdsafsfsfsdfdssadasdsadsadadasdadasdasdasdasdasdadasdadsdsad."));

        bookArrayList.add(new Book(2, "swipe to unlock", "Mark", 100,
                "https://images-eu.ssl-images-amazon.com/images/I/417XIiSoqkL.jpg",
                "how to become tech PM",
                "EZ"));
        editor = sharedPreferences.edit();
        editor.putString(ALL_BOOK_KEY, gson.toJson(bookArrayList));
        editor.apply();

    }

    public static BookDbUtils getInstance(Context context) {
        if (null != instance) {
            return instance;

        } else {
            instance = new BookDbUtils(context);
            return instance;

        }
    }


    public ArrayList<Book> getAllBooks() {
        Type type = new TypeToken<ArrayList<Book>>() {
        }.getType();
        return gson.fromJson(sharedPreferences.getString(ALL_BOOK_KEY, null), type);
    }


    public ArrayList<Book> getAlreadyReadBook() {
        Type type = new TypeToken<ArrayList<Book>>() {
        }.getType();
        return gson.fromJson(sharedPreferences.getString(ALREADY_READ_BOOKS_KEY, null), type);
    }


    public ArrayList<Book> getWishlist() {

        Type type = new TypeToken<ArrayList<Book>>() {
        }.getType();
        return gson.fromJson(sharedPreferences.getString(WISHLIST_KEY, null), type);
    }


    public ArrayList<Book> getCurrentBooks() {

        Type type = new TypeToken<ArrayList<Book>>() {
        }.getType();
        return gson.fromJson(sharedPreferences.getString(CURRENT_BOOKS_KEY, null), type);
    }


    public ArrayList<Book> getFavoriteBooks() {

        Type type = new TypeToken<ArrayList<Book>>() {
        }.getType();
        return gson.fromJson(sharedPreferences.getString(FAVORITE_BOOKS_KEY, null), type);
    }

    public boolean findBook(Book book, List<Book> books) {
        if (books != null) {
            for (Book b : books) {
                if (b.getUid() == book.getUid()) {
                    return true;

                }

            }
        }

        return false;
    }

    public boolean addToWishlist(Book book) {
        ArrayList<Book> books = getWishlist();
        if (books != null) {
            if (books.add(book)) {
                editor.putString(WISHLIST_KEY, gson.toJson(books));
                editor.apply();
                return true;
            }
        }
        return false;
    }

    public boolean addToFavorite(Book book) {

        ArrayList<Book> books = getFavoriteBooks();
        if (books != null) {
            if (books.add(book)) {
                editor.putString(FAVORITE_BOOKS_KEY, gson.toJson(books));
                editor.apply();
                return true;
            }
        }
        return false;
    }

    public boolean addToALreadyRead(Book book) {
        ArrayList<Book> books = getAlreadyReadBook();
        if (books != null) {
            if (books.add(book)) {
                editor.putString(ALREADY_READ_BOOKS_KEY, gson.toJson(books));
                editor.apply();
                return true;
            }
        }
        return false;
    }

    public boolean addToCurrentRead(Book book) {

        ArrayList<Book> books = getCurrentBooks();
        if (books != null) {
            if (books.add(book)) {
                editor.putString(CURRENT_BOOKS_KEY, gson.toJson(books));
                editor.apply();
                return true;
            }
        }
        return false;
    }

    public boolean deleteFromAlreadyRead(Book book) {
        ArrayList<Book> books = getAlreadyReadBook();
        if (books != null) {
            if (books.remove(book)) {
                editor.putString(ALREADY_READ_BOOKS_KEY, gson.toJson(books));
                editor.apply();
                return true;
            }
        }
        return false;
    }

    public boolean deleteFromCurrentRead(Book book) {

        ArrayList<Book> books = getCurrentBooks();
        if (books != null) {
            for (Book b : books) {
                if (b.getUid() == book.getUid()) {
                    books.remove(b);
                    editor.putString(CURRENT_BOOKS_KEY, gson.toJson(books));
                    editor.apply();
                    return true;
                }
            }

        }
        return false;
    }

    public boolean deleteFromFavorite(Book book) {

        ArrayList<Book> books = getFavoriteBooks();
        if (books != null) {
            if (books.remove(book)) {
                editor.putString(FAVORITE_BOOKS_KEY, gson.toJson(books));
                editor.apply();
                return true;
            }
        }
        return false;
    }

    public boolean deleteFromWishlist(Book book) {
        ArrayList<Book> books = getWishlist();

        if (books != null) {
            if (books.remove(book)) {
                editor.putString(WISHLIST_KEY, gson.toJson(books));
                editor.apply();
                return true;
            }
        }
        return false;
    }


}
