package com.seimun.logintwo.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.seimun.logintwo.model.Summary;

import java.util.HashMap;

public class SQLiteHandler extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();

    // Database version
    private static final int DATABASE_VERSION = 1;
    // Database name
    private static final String DATABASE_NAME = "android_api";
    // Login table name
    private static final String TABLE_USER = "user";
    // Login table column name
    private static final String KEY_USER_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_MOBILE = "mobile";
    private static final String KEY_IDENTITY = "identity";
    private static final String KEY_UID = "resident_id";
    private static final String KEY_CREATED_AT = "created_at";

    // Service summary table name
    private static final String TABLE_SUMMARY = "summary";
    // Service summary column name
    private static final String KEY_SUMMARY_ID = "id";
    private static final String KEY_RECORD_ID = "record_id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_CLINIC = "clinic";
    private static final String KEY_PROVIDER = "provider";
    private static final String KEY_SERVICE_TIME = "service_time";
    private static final String KEY_TYPE_ALIAS = "type_alias";
    private static final String KEY_ITEM_ALIAS = "item_alias";


    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + KEY_USER_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT, "
                + KEY_MOBILE + " TEXT UNIQUE," + KEY_IDENTITY + " TEXT UNIQUE,"
                + KEY_UID + " TEXT," + KEY_CREATED_AT + " TEXT" + ")";
        db.execSQL(CREATE_LOGIN_TABLE);

        String CREATE_SUMMARY_TABLE = "CREATE TABLE " + TABLE_SUMMARY + "("
                + KEY_SUMMARY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_RECORD_ID + " TEXT," + KEY_TITLE + " TEXT, "
                + KEY_CLINIC + " TEXT," + KEY_PROVIDER + " TEXT,"
                + KEY_SERVICE_TIME + " TEXT," + KEY_TYPE_ALIAS + " TEXT,"
                + KEY_ITEM_ALIAS + " TEXT" + ")";
        db.execSQL(CREATE_SUMMARY_TABLE);

        Log.d(TAG, "SQLite数据库被创建");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }

    public void addUser(String name, String mobile, String identity,
                        String resident_id, String created_at) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name);
        values.put(KEY_MOBILE, mobile);
        values.put(KEY_IDENTITY, identity);
        values.put(KEY_UID, resident_id);
        values.put(KEY_CREATED_AT, created_at);

        // Inserting Row
        long id = db.insert(TABLE_USER, null, values);
        db.close();

        Log.d(TAG, "New user inserted into SQLite: " + id);
    }

    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        String selectQuery = "SELECT * FROM " + TABLE_USER;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            user.put("name", cursor.getString(1));
            user.put("mobile", cursor.getString(2));
            user.put("identity", cursor.getString(3));
            user.put("resident_id", cursor.getString(4));
            user.put("created_at", cursor.getString(5));
        }
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching user from Sqlite: " + user.toString());

        return user;
    }

    public void deleteUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USER, null, null);
        db.close();
        Log.d(TAG, "Deleted all user info from sqlite");
    }

    public void addSummary(Integer record_id, String title, String clinic,
                           String provider, String service_time,
                           String type_alias, String item_alias) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_RECORD_ID, record_id);
        values.put(KEY_TITLE, title);
        values.put(KEY_CLINIC, clinic);
        values.put(KEY_PROVIDER, provider);
        values.put(KEY_SERVICE_TIME, service_time);
        values.put(KEY_TYPE_ALIAS, type_alias);
        values.put(KEY_ITEM_ALIAS, item_alias);

        // Inserting Row
        long id = db.insert(TABLE_SUMMARY, null, values);
        db.close();

        Log.d(TAG, "New summary inserted into SQLite: " + id);
    }

    public void addSummary(Summary summary) {
        addSummary(summary.getRecordId(), summary.getTitle(), summary.getClinic(),
                summary.getProvider(), summary.getServiceTime(), summary.getTypeAlias(),
                summary.getItemAlias());
    }

    public Summary getSummaryDetails(int id) {
        Summary summary = new Summary();
        String selectQuery = "SELECT * FROM " + TABLE_SUMMARY + " WHERE id = " + id;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            summary.setRecordId(cursor.getInt(1));
            summary.setTitle(cursor.getString(2));
            summary.setClinic(cursor.getString(3));
            summary.setProvider(cursor.getString(4));
            summary.setServiceTime(cursor.getString(5));
            summary.setTypeAlias(cursor.getString(6));
            summary.setItemAlias(cursor.getString(7));
        }
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching summary from SQLite: " + summary.toString());

        return summary;
    }

    public void deleteSummaries() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SUMMARY, null, null);
        db.close();
        Log.d(TAG, "Deleted all summaries info from SQLite");
    }
}
