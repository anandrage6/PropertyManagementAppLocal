package com.example.propertymanagementapplocal;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper databaseHelper;

    // All Static variables
    private static final int DATABASE_VERSION = 4;

    // Database Name
    private static final String DATABASE_NAME = Config.DATABASE_NAME;

    // Constructor
    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    public static DatabaseHelper getInstance(Context context) {
        if(databaseHelper==null){
            synchronized (DatabaseHelper.class) {
                if(databaseHelper==null)
                    databaseHelper = new DatabaseHelper(context);
            }
        }
        return databaseHelper;
    }

/*
    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


 */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // create properties sql function

        String CREATE_PROPERTIES_TABLE = "CREATE TABLE " + Config.TABLE_PROPERTY + "("
                + Config.COLUMN_PROPERTY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Config.COLUMN_PROPERTY_PROPERTYTYPE + " TEXT NOT NULL, "
                + Config.COLUMN_PROPERTY_PROPERTYNAME + " TEXT NOT NULL, "
                + Config.COLUMN_PROPERTY_OWNERNAME + " TEXT NOT NULL, "
                + Config.COLUMN_PROPERTY_ADDRESS + " TEXT NOT NULL, "
                + Config.COLUMN_PROPERTY_CITY + " TEXT NOT NULL, "
                + Config.COLUMN_PROPERTY_STATE + " TEXT NOT NULL, "
                + Config.COLUMN_PROPERTY_ZIPCODE + " TEXT, "
                + Config.COLUMN_PROPERTY_DESCRIPTION + " TEXT, " //NULLABLE
                + Config.COLUMN_PROPERTY_IMAGE + " TEXT "
                + ")";

        //CREATE FLATS  SQL FUNCTION
        String CREATE_FLAT_TABLE = "CREATE TABLE " + Config.TABLE_FLATS + "("
                + Config.COLUMN_FLATS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Config.COLUMN_FLATS_FLOOR + " TEXT NOT NULL, "
                + Config.COLUMN_FLATS_FLATNO + " TEXT NOT NULL, "
                + Config.COLUMN_FLATS_FLATFACING + " TEXT NOT NULL, "
                + Config.COLUMN_FLATS_NOOFBEDROOMS + " TEXT NOT NULL, "
                + Config.COLUMN_PF_ID + " INTEGER NOT NULL, "
                + "FOREIGN KEY (" + Config.COLUMN_PF_ID + ") REFERENCES " + Config.TABLE_PROPERTY + "(" + Config.COLUMN_PROPERTY_ID + ") ON UPDATE CASCADE ON DELETE CASCADE, "
                + "CONSTRAINT " + Config.PROPERTY_SUB_CONSTRAINT + " UNIQUE (" + Config.COLUMN_FLATS_ID + ")"
                + ")";

        db.execSQL(CREATE_PROPERTIES_TABLE);
        db.execSQL(CREATE_FLAT_TABLE);

        Logger.d("DB created!");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Config.TABLE_PROPERTY);
        db.execSQL("DROP TABLE IF EXISTS " + Config.TABLE_FLATS);

        // Create tables again
        onCreate(db);

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);

        //enable foreign key constraints like ON UPDATE CASCADE, ON DELETE CASCADE
        db.execSQL("PRAGMA foreign_keys=ON;");
    }
}
