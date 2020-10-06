package com.example.propertymanagementapplocal;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.Nullable;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper databaseHelper;

    // All Static variables
    private static final int DATABASE_VERSION = 2;

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

        //CREATE TENANTS SQL FUNCTION
        String CREATE_TENANT_TABLE = "CREATE TABLE " + Config.TABLE_TENANTS + "("
                + Config.COLUMN_TENANTS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Config.COLUMN_TENANTS_NAME + " TEXT, "
                + Config.COLUMN_TENANTS_PHONE + " TEXT, "
                + Config.COLUMN_TENANTS_EMAIL + " TEXT, "
                + Config.COLUMN_TENANTS_LEASESTART + " TEXT, "
                + Config.COLUMN_TENANTS_LEASEEND + " TEXT, "
                + Config.COLUMN_TENANTS_RENTISPAID + " TEXT, "
                + Config.COLUMN_TENANTS_TOTALOCCUPANTS + " TEXT, "
                + Config.COLUMN_TENANTS_NOTES + " TEXT, "
                + Config.COLUMN_TENANTS_RENT + " TEXT, "
                + Config.COLUMN_TENANTS_SECURITYDEPOSIT + " TEXT, "
                //+ Config.COLUMN_PT_ID + " INTEGER NOT NULL, "
                + Config.COLUMN_FT_ID + " INTEGER NOT NULL, "
                //+ "FOREIGN KEY (" + Config.COLUMN_PT_ID + ") REFERENCES " + Config.TABLE_PROPERTY + "(" + Config.COLUMN_PROPERTY_ID + ") ON UPDATE CASCADE ON DELETE CASCADE, "
                + "FOREIGN KEY (" + Config.COLUMN_FT_ID + ") REFERENCES " + Config.TABLE_FLATS + "(" + Config.COLUMN_FLATS_ID + ") ON UPDATE CASCADE ON DELETE CASCADE, "
                + "CONSTRAINT " + Config.FLAT_SUB_CONSTRAINT + " UNIQUE (" + Config.COLUMN_TENANTS_ID + ")"
                + ")";

        //CREATE TABLE INVOICE
        String CREATE_INVOICE_TABLE = "CREATE TABLE " + Config.TABLE_INVOICE + "("
                + Config.COLUMN_INVOICE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Config.COLUMN_INVOICE_TITLE + " TEXT, "
                + Config.COLUMN_INVOICE_DETAILS + " TEXT, "
                + Config.COLUMN_INVOICE_AMOUNT + " TEXT, "
                + Config.COLUMN_INVOICE_RENT + " TEXT, "
                + Config.COLUMN_INVOICE_INVOICE_ISSUED + " TEXT, "
                + Config.COLUMN_INVOICE_PaymentDue + " TEXT, "
                + Config.COLUMN_INVOICE_Notes + " TEXT, "
                + Config.COLUMN_FI_ID + " INTEGER NOT NULL, "
                + "FOREIGN KEY (" + Config.COLUMN_FI_ID + ") REFERENCES " + Config.TABLE_FLATS + "(" + Config.COLUMN_FLATS_ID + ") ON UPDATE CASCADE ON DELETE CASCADE, "
                + "CONSTRAINT " + Config.INVOICE_SUB_CONSTRAINT + " UNIQUE (" + Config.COLUMN_INVOICE_ID + ")"
                + ")";

        //CREATE TABLE PAYMENTS
        String CREATE_PAYMENTS_TABLE = "CREATE TABLE " + Config.TABLE_PAYMENTS + "("
                + Config.COLUMN_PAYMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Config.COLUMN_PAYMENT_AMOUNT + " TEXT, "
                + Config.COLUMN_PAYMENT_PAIDWITH + " TEXT, "
                + Config.COLUMN_PAYMENT_DATERECEIVED + " TEXT, "
                + Config.COLUMN_PAYMENT_RECEIVEDFROM + " TEXT, "
                + Config.COLUMN_PAYMENT_TAXSTATUS + " TEXT, "
                + Config.COLUMN_PAYMENT_NOTES + " TEXT, "
                + Config.COLUMN_FPY_ID + " INTEGER NOT NULL, "
                + "FOREIGN KEY (" + Config.COLUMN_FPY_ID + ") REFERENCES " + Config.TABLE_FLATS + "(" + Config.COLUMN_FLATS_ID + ") ON UPDATE CASCADE ON DELETE CASCADE, "
                + "CONSTRAINT " + Config.PAYMENTS_SUB_CONSTRAINT + " UNIQUE (" + Config.COLUMN_PAYMENT_ID + ")"
                + ")";



        db.execSQL(CREATE_PROPERTIES_TABLE);
        db.execSQL(CREATE_FLAT_TABLE);
        db.execSQL(CREATE_TENANT_TABLE);
        db.execSQL(CREATE_INVOICE_TABLE);
        db.execSQL(CREATE_PAYMENTS_TABLE);


        Logger.d("DB created!");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Config.TABLE_PROPERTY);
        db.execSQL("DROP TABLE IF EXISTS " + Config.TABLE_FLATS);
        db.execSQL("DROP TABLE IF EXISTS " + Config.TABLE_TENANTS);
        db.execSQL("DROP TABLE IF EXISTS " + Config.TABLE_INVOICE);
        db.execSQL("DROP TABLE IF EXISTS " + Config.TABLE_PAYMENTS);


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
