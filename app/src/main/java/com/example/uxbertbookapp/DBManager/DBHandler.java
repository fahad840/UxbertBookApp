package com.example.uxbertbookapp.DBManager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.uxbertbookapp.Model.Book;
import com.example.uxbertbookapp.Model.User;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "BooksDB";

    //Tables
    private static final String TABLE_BOOKS = "Books";
    private static final String TABLE_USERS = "Users";

    //Book Enteries
    private static final String BOOK_ID = "id";
    private static final String BOOK_NAME = "name";
    private static final String BOOK_AURTHOR = "author";
    private static final String BOOK_PAGES = "pages";
    private static final String BOOK_STATUS = "status";
    private static final String BOOK_NOTIFIABLE = "notifiable";

    //User Enteries
    private static final String USER_ID = "id";
    private static final String FULL_NAME = "name";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";


    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_bookS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_BOOKS + "("
                + BOOK_ID + " INTEGER PRIMARY KEY," + BOOK_NAME + " TEXT,"
                + BOOK_AURTHOR + " TEXT," + BOOK_PAGES + " INTEGER," + BOOK_STATUS + " TEXT," + BOOK_NOTIFIABLE + " INTEGER" + ")";
        db.execSQL(CREATE_bookS_TABLE);

        String CREATE_USERS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_USERS + "("
                + USER_ID + " INTEGER PRIMARY KEY," + FULL_NAME + " TEXT,"
                + EMAIL + " TEXT," + PASSWORD + " TEXT" + ")";
        db.execSQL(CREATE_USERS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKS);

        // Create tables again
        onCreate(db);
    }

    // code to add the new book
    public void addBook(Book book) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(BOOK_NAME, book.getName()); // Book Name
        values.put(BOOK_AURTHOR, book.getAurthor()); // Book Aurthor
        values.put(BOOK_PAGES, book.getPages()); // Book pages
        values.put(BOOK_STATUS, book.getStatus()); // Book status
        values.put(BOOK_NOTIFIABLE, book.getNotifiable()); // Book notifiable

        // Inserting Row
        db.insert(TABLE_BOOKS, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    // code to get the single book
    public Book getBook(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_BOOKS, new String[]{BOOK_ID,
                        BOOK_NAME, BOOK_AURTHOR, BOOK_PAGES, BOOK_STATUS, BOOK_NOTIFIABLE}, BOOK_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Book book = new Book(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), Integer.parseInt(cursor.getString(3)), cursor.getString(4), Integer.parseInt(cursor.getString(5)));
        // return book
        return book;
    }

    // code to get all book in a list view
    public List<Book> getNewBooks() {
        List<Book> bookList = new ArrayList<Book>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_BOOKS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_BOOKS, new String[]{BOOK_ID,
                        BOOK_NAME, BOOK_AURTHOR, BOOK_PAGES, BOOK_STATUS, BOOK_NOTIFIABLE}, BOOK_STATUS + "=?",
                new String[]{"new"}, null, null, null, null);

        // looping through all rows and adding to list
        if (cursor.getCount() <= 0) {
            return null;
        } else {
            if (cursor.moveToFirst()) {
                do {
                    Book book = new Book();
                    book.setId(Integer.parseInt(cursor.getString(0)));
                    book.setName(cursor.getString(1));
                    book.setAurthor(cursor.getString(2));
                    book.setPages(Integer.valueOf(cursor.getString(3)));
                    book.setStatus(cursor.getString(4));
                    book.setNotifiable(Integer.valueOf(cursor.getString(5)));
                    // Adding book to list
                    bookList.add(book);
                } while (cursor.moveToNext());
            }
        }

        // return book list
        return bookList;
    }


    public List<Book> getOldBooks() {
        List<Book> bookList = new ArrayList<Book>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_BOOKS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Book book = new Book();
                book.setId(Integer.parseInt(cursor.getString(0)));
                book.setName(cursor.getString(1));
                book.setAurthor(cursor.getString(2));
                book.setPages(Integer.valueOf(cursor.getString(3)));
                book.setStatus(cursor.getString(4));
                book.setNotifiable(Integer.valueOf(cursor.getString(5)));
                // Adding book to list
                bookList.add(book);
            } while (cursor.moveToNext());
        }

        // return book list
        return bookList;
    }


    public List<Book> getUpcomingBooks() {
        List<Book> bookList = new ArrayList<Book>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_BOOKS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_BOOKS, new String[]{BOOK_ID,
                        BOOK_NAME, BOOK_AURTHOR, BOOK_PAGES, BOOK_STATUS, BOOK_NOTIFIABLE}, BOOK_STATUS + "=?",
                new String[]{"upcoming"}, null, null, null, null);

        // looping through all rows and adding to list
        if (cursor.getCount() <= 0) {
            return null;
        } else {
            if (cursor.moveToFirst()) {
                do {
                    Book book = new Book();
                    book.setId(Integer.parseInt(cursor.getString(0)));
                    book.setName(cursor.getString(1));
                    book.setAurthor(cursor.getString(2));
                    book.setPages(Integer.valueOf(cursor.getString(3)));
                    book.setStatus(cursor.getString(4));
                    book.setNotifiable(Integer.valueOf(cursor.getString(5)));
                    // Adding book to list
                    bookList.add(book);
                } while (cursor.moveToNext());
            }
        }

        // return book list
        return bookList;
    }


    // code to update the single book
    public int updatebook(Book book) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(BOOK_NAME, book.getName());
        values.put(BOOK_AURTHOR, book.getAurthor());
        values.put(BOOK_PAGES, book.getPages());
        values.put(BOOK_STATUS, book.getStatus());
        values.put(BOOK_NOTIFIABLE, book.getNotifiable());

        // updating row
        return db.update(TABLE_BOOKS, values, BOOK_ID + " = ?",
                new String[]{String.valueOf(book.getId())});
    }

    // Deleting single Book
    public void deletebook(Book book) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_BOOKS, BOOK_ID + " = ?",
                new String[]{String.valueOf(book.getId())});
        db.close();
    }


    public int relaseBook(Book book) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(BOOK_STATUS, book.getStatus());
        // updating row
        return db.update(TABLE_BOOKS, values, BOOK_ID + " = ?",
                new String[]{String.valueOf(book.getId())});
    }

    // Getting book Count
    public int getbooksCount() {
        String countQuery = "SELECT  * FROM " + TABLE_BOOKS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }


    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FULL_NAME, user.getName()); // User Name
        values.put(EMAIL, user.getEmail()); // User Email
        values.put(PASSWORD, user.getPassword()); // User pass

        // Inserting Row
        db.insert(TABLE_USERS, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    // code to get the single user
    public User getUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USERS, new String[]{USER_ID,
                        FULL_NAME, EMAIL, PASSWORD}, EMAIL + "=?" + " AND " + PASSWORD + "=?",
                new String[]{email, password}, null, null, null, null);
        if (cursor.getCount() <= 0) {
            return null;
        } else {
            cursor.moveToFirst();

            User user = new User(Integer.valueOf(cursor.getString(0)),
                    cursor.getString(1), cursor.getString(2), cursor.getString(3));
            // return user
            return user;
        }
    }

    public boolean getUserCheck(String email) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USERS, new String[]{USER_ID,
                        FULL_NAME, EMAIL, PASSWORD}, EMAIL + "=?",
                new String[]{email}, null, null, null, null);
        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
        // return user
    }


}
