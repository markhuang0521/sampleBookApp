package com.ming.androbook.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.ming.androbook.BookDetailActivity;
import com.ming.androbook.R;
import com.ming.androbook.model.Book;
import com.ming.androbook.utils.BookDbUtils;

import java.util.List;

public class BookRecyclerAdapter extends RecyclerView.Adapter<BookRecyclerAdapter.ViewHolder> {
    private List<Book> bookList;
    private Context context;
    private BookAdapterOnClickLister onClickLister;
    private String parentContext;

    public BookRecyclerAdapter(Context context, String parentContext) {
        this.context = context;
        this.parentContext = parentContext;
    }

    public interface BookAdapterOnClickLister {
        void upArrowClick(Book book, int position);

        void downArrowClick(Book book, int position);

        void itemOnClick(Book book, int position);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Book book = bookList.get(position);
        holder.bookTitle.setText(book.getTitle());
        holder.bookAuthor.setText(book.getAuthor());
        holder.bookShortDesc.setText(book.getShortDesc());
        Glide.with(context).asBitmap().load(book.getImageUrl()).into(holder.bookImage);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, BookDetailActivity.class);
                intent.putExtra(Book.BOOK_OBJ_KEY, book);
                context.startActivity(intent);
            }
        });
        if (book.isExpanded()) {
            if (parentContext.equals(BookDbUtils.ALL_BOOK_KEY)) {
                holder.btnDelete.setVisibility(View.GONE);

            } else if (parentContext.equals(BookDbUtils.CURRENT_BOOKS_KEY)) {
                holder.btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        deleteBtnDialog(context, BookDbUtils.CURRENT_BOOKS_KEY, book);

                    }
                });
            } else if (parentContext.equals(BookDbUtils.FAVORITE_BOOKS_KEY)) {
                holder.btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        deleteBtnDialog(context, BookDbUtils.FAVORITE_BOOKS_KEY, book);


                    }
                });

            } else if (parentContext.equals(BookDbUtils.WISHLIST_KEY)) {
                holder.btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        deleteBtnDialog(context, BookDbUtils.WISHLIST_KEY, book);


                    }
                });

            } else if (parentContext.equals(BookDbUtils.ALREADY_READ_BOOKS_KEY)) {
                holder.btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        deleteBtnDialog(context, BookDbUtils.ALREADY_READ_BOOKS_KEY, book);

                    }
                });

            }

            TransitionManager.beginDelayedTransition(holder.cardView);
            holder.layoutExpanded.setVisibility(View.VISIBLE);
            holder.btnDownArrow.setVisibility(View.GONE);
        } else {
            TransitionManager.beginDelayedTransition(holder.cardView);

            holder.layoutExpanded.setVisibility(View.GONE);
            holder.btnDownArrow.setVisibility(View.VISIBLE);
        }


    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private CardView cardView;
        private ImageView bookImage;
        private TextView bookTitle, bookAuthor, bookShortDesc, btnDelete;
        private ImageView btnUpArrow, btnDownArrow;
        private RelativeLayout layoutExpanded;
        private RelativeLayout layoutCollapse;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            btnDelete = itemView.findViewById(R.id.btn_card_delete);
            cardView = itemView.findViewById(R.id.card_book);
            bookImage = itemView.findViewById(R.id.iv_book_image);
            bookTitle = itemView.findViewById(R.id.tv_book_title);
            btnUpArrow = itemView.findViewById(R.id.btn_card_up_arrow);
            btnDownArrow = itemView.findViewById(R.id.btn_card_down_arrow);
            layoutExpanded = itemView.findViewById(R.id.layout_expanded_card);
            layoutCollapse = itemView.findViewById(R.id.layout_card_collapsed);
            bookAuthor = itemView.findViewById(R.id.tv_book_author);
            bookShortDesc = itemView.findViewById(R.id.tv_book_desc);


            btnUpArrow.setOnClickListener(this);
            btnDownArrow.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Book book = bookList.get(getAdapterPosition());
            if (view.getId() == R.id.btn_card_up_arrow || view.getId() == R.id.btn_card_down_arrow) {

                book.setExpanded(!book.isExpanded());
                notifyItemChanged(position);
            }

        }
    }

    private void deleteBtnDialog(final Context context, final String listName, final Book book) {
        final BookDbUtils utils = BookDbUtils.getInstance(context);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("are you sure to delete this? ");
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (listName) {
                    case BookDbUtils.ALREADY_READ_BOOKS_KEY:
                        if (utils.deleteFromAlreadyRead(book)) {
                            Toast.makeText(context, " book has been deleted", Toast.LENGTH_SHORT).show();
                            notifyDataSetChanged();
                        } else {
                            Toast.makeText(context, "fail to delete", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case BookDbUtils.CURRENT_BOOKS_KEY:
                        if (utils.deleteFromCurrentRead(book)) {
                            Toast.makeText(context, " book has been deleted", Toast.LENGTH_SHORT).show();
                            notifyDataSetChanged();
                        } else {
                            Toast.makeText(context, "fail to delete", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case BookDbUtils.FAVORITE_BOOKS_KEY:
                        if (utils.deleteFromFavorite(book)) {
                            Toast.makeText(context, " book has been deleted", Toast.LENGTH_SHORT).show();
                            notifyDataSetChanged();
                        } else {
                            Toast.makeText(context, "fail to delete", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case BookDbUtils.WISHLIST_KEY:
                        if (utils.deleteFromWishlist(book)) {
                            Toast.makeText(context, " book has been deleted", Toast.LENGTH_SHORT).show();
                            notifyDataSetChanged();
                        } else {
                            Toast.makeText(context, "fail to delete", Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
            }
        }).setNegativeButton("No", null).create().show();
    }

}
