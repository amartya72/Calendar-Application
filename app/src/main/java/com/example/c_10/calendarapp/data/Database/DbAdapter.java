package com.example.c_10.calendarapp.data.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DbAdapter {
    protected static final String TAG = "DataAdapter";

    private final Context mContext;
    private SQLiteDatabase mDb;
    private DatabaseHandler mDbHelper;

    @SuppressWarnings("unused")

    public DbAdapter(Context context) {
        this.mContext = context;
        mDbHelper = new DatabaseHandler(mContext);
    }

    public DbAdapter createDatabase() throws SQLException {
        try {
            mDbHelper.createDataBase();
        } catch (IOException mIOException) {
            Log.e(TAG, mIOException.toString() + "  UnableToCreateDatabase");
            throw new Error("UnableToCreateDatabase");
        }
        return this;
    }

    public DbAdapter open() throws SQLException {
        try {
            mDbHelper.openDataBase();
            mDbHelper.close();
            mDb = mDbHelper.getReadableDatabase();
        } catch (SQLException mSQLException) {
            Log.e(TAG, "open >>" + mSQLException.toString());
            throw mSQLException;
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        return this;
    }

    public void close() {
        mDbHelper.close();
    }


    public Cursor selectQuery(String query) {
        Cursor c1 = null;
        try {

            if (mDb.isOpen()) {
                mDb.close();

            }
            mDb = mDbHelper.getWritableDatabase();
            c1 = mDb.rawQuery(query, null);

        } catch (Exception e) {

            System.out.println("DATABASE ERROR " + e);

        }
        return c1;

    }

    public void executeQuery(String query) {
        try {

            if (mDb.isOpen()) {
                mDb.close();
            }

            mDb = mDbHelper.getWritableDatabase();
            mDb.execSQL(query);

        } catch (Exception e) {

            System.out.println("DATABASE ERROR " + e);
        }
    }

    public void deleteQuery(String query) {
        try {

            if (mDb.isOpen()) {
                mDb.close();
            }

            mDb = mDbHelper.getWritableDatabase();
            mDb.execSQL(query);

        } catch (Exception e) {

            System.out.println("DATABASE ERROR " + e);
        }

    }

    public void updateQuery(String query) {
        try {

            if (mDb.isOpen()) {
                mDb.close();
            }

            mDb = mDbHelper.getWritableDatabase();
            mDb.execSQL(query);

        } catch (Exception e) {

            System.out.println("DATABASE ERROR " + e);
        }

    }

    public boolean SaveEmployee(String name) {
        try {
            ContentValues cv = new ContentValues();
            cv.put("category_name", name);
            // cv.put("Email", email);

            mDb.insert("category", null, cv);

            Log.d("Save category", "informationsaved");
            return true;

        } catch (Exception ex) {
            Log.d("SaveCategory", ex.toString());
            return false;
        }
    }

    public List<String> getAllLabels() {
        List<String> labels = new ArrayList<String>();
        String selectQuery = "SELECT  * FROM  category";
        mDb = mDbHelper.getWritableDatabase();
        Cursor cursor = mDb.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        mDb.close();

        // returning lables
        return labels;
    }

    public Cursor getMrpData(String id) {
        try {
            String sql = "select product_mrp,product_quantity from product where product_id='" + id + "'";
            Cursor mCur = mDb.rawQuery(sql, null);
            if (mCur != null) {
                mCur.moveToNext();

            }
            return mCur;
        } catch (SQLException mSQLException) {
            Log.e(TAG, "getTestData >>" + mSQLException.toString());
            throw mSQLException;
        }
    }


}
