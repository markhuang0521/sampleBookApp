package com.ming.androbook.model;


import android.os.Parcel;
import android.os.Parcelable;

public class Book implements Parcelable {
    private int uid;
    private String title;
    private String author;
    private int pages;
    private String imageUrl;
    private String shortDesc;
    private String longDesc;
    private boolean expanded;
    public final static String BOOK_OBJ_KEY = "book key";

    protected Book(Parcel in) {
        uid = in.readInt();
        title = in.readString();
        author = in.readString();
        pages = in.readInt();
        imageUrl = in.readString();
        shortDesc = in.readString();
        longDesc = in.readString();
        expanded = in.readByte() != 0;
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Book(int uid, String title, String author, int pages, String imageUrl, String shortDesc, String longDesc) {
        this.uid = uid;
        this.title = title;
        this.author = author;
        this.pages = pages;
        this.imageUrl = imageUrl;
        this.shortDesc = shortDesc;
        this.longDesc = longDesc;
        expanded = false;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getLongDesc() {
        return longDesc;
    }

    public void setLongDesc(String longDesc) {
        this.longDesc = longDesc;
    }

    @Override
    public String toString() {
        return "Book{" +
                "uid=" + uid +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", pages=" + pages +
                ", imageUrl='" + imageUrl + '\'' +
                ", shortDesc='" + shortDesc + '\'' +
                ", longDesc='" + longDesc + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(uid);
        parcel.writeString(title);
        parcel.writeString(author);
        parcel.writeInt(pages);
        parcel.writeString(imageUrl);
        parcel.writeString(shortDesc);
        parcel.writeString(longDesc);
        parcel.writeByte((byte) (expanded ? 1 : 0));
    }
}

