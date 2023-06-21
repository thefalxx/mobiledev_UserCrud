package com.sti.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public static final String USER_TABLE = "user";
    public static final String USER_ID = "user_id";
    public static final String USER_USERNAME = "username";
    public static final String USER_PASSWORD = "password";

    private static final String SQL_CREATE_USERS =
            "CREATE TABLE " + USER_TABLE + " (" +
                USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                USER_USERNAME + " TEXT," +
                USER_PASSWORD + " TEXT)";

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "STIDVO.db";

    public final SQLiteDatabase db = this.getWritableDatabase();
    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQL_CREATE_USERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public long InsertUser(Student student){

        ContentValues values = new ContentValues();
        values.put(USER_USERNAME, student.getUsername());
        values.put(USER_PASSWORD, student.getPassword());

        long check = db.insert(USER_TABLE, null, values);

        return check;
    }




    public ArrayList<Student> RetrieveUsers(){
        Cursor users = db.rawQuery("SELECT * FROM "+USER_TABLE, null);
        ArrayList<Student> students = new ArrayList<>();
//        int count = 1;
        Log.d("Retrieve_Users", "Okay 1");
        while(users.moveToNext()) {

            Student student = new Student(Integer.parseInt(users.getString(users.getColumnIndexOrThrow(USER_ID))), users.getString(users.getColumnIndexOrThrow(USER_USERNAME)), users.getString(users.getColumnIndexOrThrow(USER_PASSWORD)));
//                count++;
            students.add(student);
        }

        return students;
    }

    public Student retrieveUserByPosition(int position) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                USER_TABLE,
                null,
                null,
                null,
                null,
                null,
                null
        );

        Student student = null;
        if (cursor.moveToPosition(position)) {
            int id = cursor.getInt(cursor.getColumnIndex(USER_ID));
            String username = cursor.getString(cursor.getColumnIndex(USER_USERNAME));
            String password = cursor.getString(cursor.getColumnIndex(USER_PASSWORD));
            student = new Student(id, username, password);
        }

        cursor.close();
        db.close();

        return student;
    }


    public void UpdateUser(String studID , String newUsername , String newPassword){
        ContentValues values = new ContentValues();
        values.put(USER_USERNAME, newUsername);
        values.put(USER_PASSWORD, newPassword);

        int record = db.update(USER_TABLE, values, USER_ID+" = ?", new String[]{studID});
        Log.d("UPDATE_QUERY", "Record Updated: " + record);
}
}
