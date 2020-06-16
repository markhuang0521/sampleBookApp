package com.ming.androbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnallBooks, btnWishlist, btnFavoriteBooks, btnCurrentBoks, btnAlreadyReadBook, btnAbout;
    public final static int PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        requestUserPermission();

        btnallBooks.setOnClickListener(this);
        btnAlreadyReadBook.setOnClickListener(this);
        btnCurrentBoks.setOnClickListener(this);
        btnFavoriteBooks.setOnClickListener(this);
        btnWishlist.setOnClickListener(this);
        btnAbout.setOnClickListener(this);
    }

    private void initViews() {
        btnallBooks = findViewById(R.id.btn_main_all_book);
        btnWishlist = findViewById(R.id.btn_main_wishlist);
        btnFavoriteBooks = findViewById(R.id.btn_main_favorite);
        btnCurrentBoks = findViewById(R.id.btn_main_current_book);
        btnAlreadyReadBook = findViewById(R.id.btn_main_already_read_book);
        btnAbout = findViewById(R.id.btn_app_about);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_main_all_book:
                startActivity(new Intent(MainActivity.this, AllBooksActivity.class));
                break;
            case R.id.btn_main_already_read_book:
                startActivity(new Intent(MainActivity.this, AlreadyReadBookActivity.class));
                break;
            case R.id.btn_main_current_book:
                startActivity(new Intent(MainActivity.this, CurrentBookActivity.class));
                break;
            case R.id.btn_main_favorite:
                startActivity(new Intent(MainActivity.this, FavoriteBookActivity.class));
                break;
            case R.id.btn_main_wishlist:
                startActivity(new Intent(MainActivity.this, WishLIstActivity.class));
                break;
            case R.id.btn_app_about:
                btnAboutOnclick();
                break;
        }
    }

    private void btnAboutOnclick() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setTitle(getString(R.string.app_name));
        builder.setMessage("Design and developed by Mark Huang Checkout my website for more Info! ");
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(MainActivity.this, WebsiteActvity.class);
                intent.putExtra(WebsiteActvity.WEBSITE_URL_KEY, "https://www.google.com/");

                startActivity(intent);

            }
        }).setNegativeButton("NO", null).create().show();
        builder.setCancelable(true);
    }

    private void requestUserPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            requestPermissions(new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CODE);

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    Toast.makeText(this, "premission granted", Toast.LENGTH_LONG).show();
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                        if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                            showMessageOKCancel("You need to allow access to both the permissions",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions(new String[]{Manifest.permission.CAMERA},
                                                        PERMISSION_REQUEST_CODE);
                                            }
                                        }
                                    });
                            return;
                        }

                    }
                }
        }

    }


    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(MainActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
}
