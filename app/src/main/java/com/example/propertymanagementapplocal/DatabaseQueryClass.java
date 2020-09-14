package com.example.propertymanagementapplocal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.widget.Toast;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DatabaseQueryClass {
    private Context context;

    public DatabaseQueryClass(Context context) {
        this.context = context;
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    public long insertProperty(PropertyModelClass propertyModel) {
        long id = -1;

        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Config.COLUMN_PROPERTY_PROPERTYTYPE, propertyModel.getPropertyType());
        contentValues.put(Config.COLUMN_PROPERTY_PROPERTYNAME, propertyModel.getPropertyName());
        contentValues.put(Config.COLUMN_PROPERTY_OWNERNAME, propertyModel.getOwnerName());
        contentValues.put(Config.COLUMN_PROPERTY_ADDRESS, propertyModel.getAddress());
        contentValues.put(Config.COLUMN_PROPERTY_CITY, propertyModel.getCity());
        contentValues.put(Config.COLUMN_PROPERTY_STATE, propertyModel.getState());
        contentValues.put(Config.COLUMN_PROPERTY_ZIPCODE, propertyModel.getZipCode());
        contentValues.put(Config.COLUMN_PROPERTY_DESCRIPTION, propertyModel.getDescription());
        contentValues.put(Config.COLUMN_PROPERTY_IMAGE, propertyModel.getImage());

        try {
            id = sqLiteDatabase.insertOrThrow(Config.TABLE_PROPERTY, null, contentValues);
        } catch (SQLiteException e) {
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, "Operation failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return id;
    }

    public List<PropertyModelClass> getAllProperty() {

        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        Cursor cursor = null;
        try {


            /*
            cursor = sqLiteDatabase.query(Config.TABLE_PROPERTY, null, null, null, null, null, null, null);


             */


            String SELECT_QUERY = String.format("SELECT %s, %s, %s, %s, %s, %s, %s, %s, %s, %s FROM %s", Config.COLUMN_PROPERTY_ID, Config.COLUMN_PROPERTY_PROPERTYTYPE, Config.COLUMN_PROPERTY_PROPERTYNAME, Config.COLUMN_PROPERTY_OWNERNAME, Config.COLUMN_PROPERTY_ADDRESS,
                    Config.COLUMN_PROPERTY_CITY, Config.COLUMN_PROPERTY_STATE, Config.COLUMN_PROPERTY_ZIPCODE, Config.COLUMN_PROPERTY_DESCRIPTION, Config.COLUMN_PROPERTY_IMAGE, Config.TABLE_PROPERTY);
            cursor = sqLiteDatabase.rawQuery(SELECT_QUERY, null);


            if (cursor != null)
                if (cursor.moveToFirst()) {
                    List<PropertyModelClass> propertyList = new ArrayList<>();
                    do {
                        long propertyId = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_PROPERTY_ID));
                        String PropertyType = cursor.getString(cursor.getColumnIndex(Config.COLUMN_PROPERTY_PROPERTYTYPE));
                        String propertyName = cursor.getString(cursor.getColumnIndex(Config.COLUMN_PROPERTY_PROPERTYNAME));
                        String ownerName = cursor.getString(cursor.getColumnIndex(Config.COLUMN_PROPERTY_OWNERNAME));
                        String address = cursor.getString(cursor.getColumnIndex(Config.COLUMN_PROPERTY_ADDRESS));
                        String city = cursor.getString(cursor.getColumnIndex(Config.COLUMN_PROPERTY_CITY));
                        String state = cursor.getString(cursor.getColumnIndex(Config.COLUMN_PROPERTY_STATE));
                        String zipcode = cursor.getString(cursor.getColumnIndex(Config.COLUMN_PROPERTY_ZIPCODE));
                        String description = cursor.getString(cursor.getColumnIndex(Config.COLUMN_PROPERTY_DESCRIPTION));
                        String image = cursor.getString(cursor.getColumnIndex(Config.COLUMN_PROPERTY_IMAGE));

                        propertyList.add(new PropertyModelClass(propertyId, PropertyType, propertyName, ownerName, address, city, state, zipcode, description, image));
                    } while (cursor.moveToNext());

                    return propertyList;
                }
        } catch (Exception e) {
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, "Operation failed", Toast.LENGTH_SHORT).show();
        } finally {
            if (cursor != null)
                cursor.close();
            sqLiteDatabase.close();
        }

        return Collections.emptyList();
    }

    public PropertyModelClass getPropertyById(long id) {

        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        Cursor cursor = null;
        PropertyModelClass property = null;
        try {

            /*
            cursor = sqLiteDatabase.query(Config.TABLE_PROPERTY, null,
                    Config.COLUMN_PROPERTY_ID + " = ? ", new String[]{String.valueOf(id)},
                    null, null, null);

             */

            String SELECT_QUERY = String.format("SELECT * FROM %s WHERE %s = %s", Config.TABLE_PROPERTY, Config.COLUMN_PROPERTY_ID, String.valueOf(id));
            cursor = sqLiteDatabase.rawQuery(SELECT_QUERY, null);


            if (cursor.moveToFirst()) {
                int propertyId = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_PROPERTY_ID));
                String PropertyType = cursor.getString(cursor.getColumnIndex(Config.COLUMN_PROPERTY_PROPERTYTYPE));
                String propertyName = cursor.getString(cursor.getColumnIndex(Config.COLUMN_PROPERTY_PROPERTYNAME));
                String ownerName = cursor.getString(cursor.getColumnIndex(Config.COLUMN_PROPERTY_OWNERNAME));
                String address = cursor.getString(cursor.getColumnIndex(Config.COLUMN_PROPERTY_ADDRESS));
                String city = cursor.getString(cursor.getColumnIndex(Config.COLUMN_PROPERTY_CITY));
                String state = cursor.getString(cursor.getColumnIndex(Config.COLUMN_PROPERTY_STATE));
                String zipcode = cursor.getString(cursor.getColumnIndex(Config.COLUMN_PROPERTY_ZIPCODE));
                String description = cursor.getString(cursor.getColumnIndex(Config.COLUMN_PROPERTY_DESCRIPTION));
                String image = cursor.getString(cursor.getColumnIndex(Config.COLUMN_PROPERTY_IMAGE));

                property = new PropertyModelClass(propertyId, PropertyType, propertyName, ownerName, address, city, state, zipcode, description, image);

            }
        } catch (Exception e) {
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, "Operation failed", Toast.LENGTH_SHORT).show();
        } finally {
            if (cursor != null)
                cursor.close();
            sqLiteDatabase.close();
        }

        return property;
    }

    //update proprty
    public long updatePropertyInfo(PropertyModelClass property){

        long rowCount = 0;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Config.COLUMN_PROPERTY_PROPERTYTYPE, property.getPropertyType());
        contentValues.put(Config.COLUMN_PROPERTY_PROPERTYNAME, property.getPropertyName());
        contentValues.put(Config.COLUMN_PROPERTY_OWNERNAME, property.getOwnerName());
        contentValues.put(Config.COLUMN_PROPERTY_ADDRESS, property.getAddress());
        contentValues.put(Config.COLUMN_PROPERTY_CITY, property.getCity());
        contentValues.put(Config.COLUMN_PROPERTY_STATE, property.getState());
        contentValues.put(Config.COLUMN_PROPERTY_ZIPCODE, property.getZipCode());
        contentValues.put(Config.COLUMN_PROPERTY_DESCRIPTION, property.getDescription());
        contentValues.put(Config.COLUMN_PROPERTY_IMAGE, property.getImage());

        try {
            rowCount = sqLiteDatabase.update(Config.TABLE_PROPERTY, contentValues,
                    Config.COLUMN_PROPERTY_ID + " = ? ",
                    new String[] {String.valueOf(property.getId())});
        } catch (SQLiteException e){
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return rowCount;
    }

    // delete property
    public long deletePropertyById(long id) {
        long deletedRowCount = -1;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        try {
            deletedRowCount = sqLiteDatabase.delete(Config.TABLE_PROPERTY,
                    Config.COLUMN_PROPERTY_ID + " = ? ",
                    new String[]{ String.valueOf(id)});
        } catch (SQLiteException e){
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return deletedRowCount;
    }


    //flats part

    //insert flats

    public long insertFlats(FlatsModelClass flats, long id) {
        long rowId = -1;

        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Config.COLUMN_FLATS_FLATNO, flats.getFlaNo());
        contentValues.put(Config.COLUMN_PF_ID, id);

        try {
            rowId = sqLiteDatabase.insertOrThrow(Config.TABLE_FLATS, null, contentValues);
        } catch (SQLiteException e) {
            Logger.d(e);
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return rowId;
    }

    //get flat by id
    public FlatsModelClass getFlatsById(long flatsId) {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        FlatsModelClass flats = null;

        Cursor cursor = null;

        try {
            cursor = sqLiteDatabase.query(Config.TABLE_FLATS, null,
                    Config.COLUMN_FLATS_ID + " = ? ", new String[]{String.valueOf(flatsId)},
                    null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                String FlatNo = cursor.getString(cursor.getColumnIndex(Config.COLUMN_FLATS_FLATNO));

                flats = new FlatsModelClass(flatsId, FlatNo);
            }
        } catch (SQLiteException e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            if (cursor != null)
                cursor.close();
            sqLiteDatabase.close();
        }

        return flats;
    }


    //get All subjects by id
    public List<FlatsModelClass> getAllFlatsByPFId(long pfId){
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        List<FlatsModelClass> flatsList = new ArrayList<>();
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(Config.TABLE_FLATS,
                    new String[] {Config.COLUMN_FLATS_ID, Config.COLUMN_FLATS_FLATNO},
                    Config.COLUMN_PF_ID + " = ? ",
                    new String[] {String.valueOf(pfId)},
                    null, null, null);

            if(cursor!=null && cursor.moveToFirst()){
                flatsList = new ArrayList<>();
                do {
                    int id = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_FLATS_ID));
                    String flatNo = cursor.getString(cursor.getColumnIndex(Config.COLUMN_FLATS_FLATNO));


                    flatsList.add(new FlatsModelClass(id, flatNo));
                } while (cursor.moveToNext());
            }
        } catch (SQLiteException e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            if(cursor!=null)
                cursor.close();
            sqLiteDatabase.close();
        }

        return flatsList;
    }

    public boolean deleteFlatById(long flatId) {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        int row = sqLiteDatabase.delete(Config.TABLE_FLATS,
                Config.COLUMN_FLATS_ID + " = ? ", new String[]{String.valueOf(flatId)});

        return row > 0;
    }

}


