package com.example.propertymanagementapplocal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;
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

    //insert property
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

    //get all property (retrive)
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

    //get property by id (update)
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
        contentValues.put(Config.COLUMN_FLATS_FLOOR, flats.getFloor());
        contentValues.put(Config.COLUMN_FLATS_FLATNO, flats.getFlaNo());
        contentValues.put(Config.COLUMN_FLATS_FLATFACING, flats.getFaltfacing());
        contentValues.put(Config.COLUMN_FLATS_NOOFBEDROOMS, flats.getNoofbedrooms());
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

    //get flat by id (update purpose)
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
                String floor = cursor.getString(cursor.getColumnIndex(Config.COLUMN_FLATS_FLOOR));
                String flatNo = cursor.getString(cursor.getColumnIndex(Config.COLUMN_FLATS_FLATNO));
                String flatFacing = cursor.getString(cursor.getColumnIndex(Config.COLUMN_FLATS_FLATFACING));
                String noofBedrooms = cursor.getString(cursor.getColumnIndex(Config.COLUMN_FLATS_NOOFBEDROOMS));

                flats = new FlatsModelClass(flatsId, floor, flatNo, flatFacing, noofBedrooms);
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

    //update flat
    public long updateFlatInfo(FlatsModelClass flat){

        long rowCount = 0;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Config.COLUMN_FLATS_FLOOR, flat.getFloor());
        contentValues.put(Config.COLUMN_FLATS_FLATNO, flat.getFlaNo());
        contentValues.put(Config.COLUMN_FLATS_FLATFACING, flat.getFaltfacing());
        contentValues.put(Config.COLUMN_FLATS_NOOFBEDROOMS, flat.getNoofbedrooms());


        try {
            rowCount = sqLiteDatabase.update(Config.TABLE_FLATS, contentValues,
                    Config.COLUMN_FLATS_ID + " = ? ",
                    new String[] {String.valueOf(flat.getFlatId())});
        } catch (SQLiteException e){
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return rowCount;
    }


    //get All flats by id (retrive)
    public List<FlatsModelClass> getAllFlatsByPFId(long pfId){
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        List<FlatsModelClass> flatsList = new ArrayList<>();
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(Config.TABLE_FLATS,
                    new String[] {Config.COLUMN_FLATS_ID, Config.COLUMN_FLATS_FLOOR, Config.COLUMN_FLATS_FLATNO,
                            Config.COLUMN_FLATS_FLATFACING, Config.COLUMN_FLATS_NOOFBEDROOMS},
                    Config.COLUMN_PF_ID + " = ? ",
                    new String[] {String.valueOf(pfId)},
                    null, null, null);

            if(cursor!=null && cursor.moveToFirst()){
                flatsList = new ArrayList<>();
                do {
                    int id = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_FLATS_ID));
                    String floor = cursor.getString(cursor.getColumnIndex(Config.COLUMN_FLATS_FLOOR));
                    String flatNo = cursor.getString(cursor.getColumnIndex(Config.COLUMN_FLATS_FLATNO));
                    String flatFacing = cursor.getString(cursor.getColumnIndex(Config.COLUMN_FLATS_FLATFACING));
                    String noofBedrooms = cursor.getString(cursor.getColumnIndex(Config.COLUMN_FLATS_NOOFBEDROOMS));

                    flatsList.add(new FlatsModelClass(id, floor, flatNo, flatFacing, noofBedrooms));
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





    //single delete
    public boolean deleteFlatById(long flatId) {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        int row = sqLiteDatabase.delete(Config.TABLE_FLATS,
                Config.COLUMN_FLATS_ID + " = ? ", new String[]{String.valueOf(flatId)});

        return row > 0;
    }

    //insert tenants

    public long insertTenant(TenantModelClass tenant, long flatId) {
        long rowId = -1;

        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Config.COLUMN_TENANTS_NAME, tenant.getTenantName());
        contentValues.put(Config.COLUMN_TENANTS_PHONE, tenant.getTenantphone());
        contentValues.put(Config.COLUMN_TENANTS_EMAIL, tenant.getTenantEmail());
        contentValues.put(Config.COLUMN_TENANTS_LEASESTART, tenant.getLeaseStart());
        contentValues.put(Config.COLUMN_TENANTS_LEASEEND, tenant.getLeaseEnd());
        contentValues.put(Config.COLUMN_TENANTS_RENTISPAID, tenant.getRentIsPaid());
        contentValues.put(Config.COLUMN_TENANTS_TOTALOCCUPANTS, tenant.getTotalOccupants());
        contentValues.put(Config.COLUMN_TENANTS_NOTES, tenant.getNotes());
        contentValues.put(Config.COLUMN_TENANTS_RENT, tenant.getRentAmount());
        contentValues.put(Config.COLUMN_TENANTS_SECURITYDEPOSIT, tenant.getSecurityDeposit());
        //contentValues.put(Config.COLUMN_PT_ID, id);
        contentValues.put(Config.COLUMN_FT_ID, flatId);

        try {
            rowId = sqLiteDatabase.insertOrThrow(Config.TABLE_TENANTS, null, contentValues);
        } catch (SQLiteException e) {
            Logger.d(e);
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return rowId;
    }

    //get All tenants by id
    public List<TenantModelClass> getAllTenantsByFId(long fId){
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        List<TenantModelClass> tenantList = new ArrayList<>();
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(Config.TABLE_TENANTS,
                    new String[] {Config.COLUMN_TENANTS_ID, Config.COLUMN_TENANTS_NAME,
                            Config.COLUMN_TENANTS_PHONE, Config.COLUMN_TENANTS_EMAIL, Config.COLUMN_TENANTS_LEASESTART, Config.COLUMN_TENANTS_LEASEEND, Config.COLUMN_TENANTS_RENTISPAID,
                            Config.COLUMN_TENANTS_TOTALOCCUPANTS, Config.COLUMN_TENANTS_NOTES, Config.COLUMN_TENANTS_RENT, Config.COLUMN_TENANTS_SECURITYDEPOSIT},
                    Config.COLUMN_FT_ID + " = ? ",
                    new String[] {String.valueOf(fId)},
                    null, null, null);

            if(cursor!=null && cursor.moveToFirst()){
                tenantList = new ArrayList<>();
                do {
                    int id = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_TENANTS_ID));
                    String name = cursor.getString(cursor.getColumnIndex(Config.COLUMN_TENANTS_NAME));
                    String phone = cursor.getString(cursor.getColumnIndex(Config.COLUMN_TENANTS_PHONE));
                    String email = cursor.getString(cursor.getColumnIndex(Config.COLUMN_TENANTS_EMAIL));
                    String leaseStart = cursor.getString(cursor.getColumnIndex(Config.COLUMN_TENANTS_LEASESTART));
                    String leaseEnd = cursor.getString(cursor.getColumnIndex(Config.COLUMN_TENANTS_LEASEEND));
                    String rentIsPaid = cursor.getString(cursor.getColumnIndex(Config.COLUMN_TENANTS_RENTISPAID));
                    String totalOccupants = cursor.getString(cursor.getColumnIndex(Config.COLUMN_TENANTS_TOTALOCCUPANTS));
                    String notes = cursor.getString(cursor.getColumnIndex(Config.COLUMN_TENANTS_NOTES));
                    String rent = cursor.getString(cursor.getColumnIndex(Config.COLUMN_TENANTS_RENT));
                    String securityDeposit = cursor.getString(cursor.getColumnIndex(Config.COLUMN_TENANTS_SECURITYDEPOSIT));

                    tenantList.add(new TenantModelClass(id, name, phone, email, leaseStart, leaseEnd, rentIsPaid, totalOccupants, notes, rent, securityDeposit));
                    //Log.d("TotalTenantList : ==> ", String.valueOf(tenantList.size()));
                } while (cursor.moveToNext());
            }
        } catch (SQLiteException e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            if(cursor!=null)
                cursor.close();
            sqLiteDatabase.close();
        }

        return tenantList;

    }


    //insert Invoice

    public long insertInvoice(InvoiceModelClass invoice, long flatId) {
        long rowId = -1;

        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Config.COLUMN_INVOICE_TITLE, invoice.getTitle());
        contentValues.put(Config.COLUMN_INVOICE_DETAILS, invoice.getDetails());
        contentValues.put(Config.COLUMN_INVOICE_AMOUNT, invoice.getAmount());
        contentValues.put(Config.COLUMN_INVOICE_RENT, invoice.getRent());
        contentValues.put(Config.COLUMN_INVOICE_INVOICE_ISSUED, invoice.getInvoiceIssued());
        contentValues.put(Config.COLUMN_INVOICE_PaymentDue, invoice.getPaymentDue());
        contentValues.put(Config.COLUMN_INVOICE_Notes, invoice.getNotes());
        //contentValues.put(Config.COLUMN_PT_ID, id);
        contentValues.put(Config.COLUMN_FI_ID, flatId);

        try {
            rowId = sqLiteDatabase.insertOrThrow(Config.TABLE_INVOICE, null, contentValues);
        } catch (SQLiteException e) {
            Logger.d(e);
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return rowId;
    }



    //get All invoice by id
    public List<InvoiceModelClass> getAllInvoicebyId(long fId){
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        List<InvoiceModelClass> invoiceList = new ArrayList<>();
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(Config.TABLE_INVOICE,
                    new String[] {Config.COLUMN_INVOICE_ID, Config.COLUMN_INVOICE_TITLE,
                            Config.COLUMN_INVOICE_DETAILS, Config.COLUMN_INVOICE_AMOUNT, Config.COLUMN_INVOICE_RENT, Config.COLUMN_INVOICE_INVOICE_ISSUED, Config.COLUMN_INVOICE_PaymentDue,
                            Config.COLUMN_INVOICE_Notes},
                    Config.COLUMN_FI_ID + " = ? ",
                    new String[] {String.valueOf(fId)},
                    null, null, null);

            if(cursor!=null && cursor.moveToFirst()){
                invoiceList = new ArrayList<>();
                do {
                    long id = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_INVOICE_ID));
                    String title = cursor.getString(cursor.getColumnIndex(Config.COLUMN_INVOICE_TITLE));
                    String details = cursor.getString(cursor.getColumnIndex(Config.COLUMN_INVOICE_DETAILS));
                    String amount = cursor.getString(cursor.getColumnIndex(Config.COLUMN_INVOICE_AMOUNT));
                    String rent = cursor.getString(cursor.getColumnIndex(Config.COLUMN_INVOICE_RENT));
                    String invoiceIssued = cursor.getString(cursor.getColumnIndex(Config.COLUMN_INVOICE_INVOICE_ISSUED));
                    String paymentDue = cursor.getString(cursor.getColumnIndex(Config.COLUMN_INVOICE_PaymentDue));
                    String notes = cursor.getString(cursor.getColumnIndex(Config.COLUMN_INVOICE_Notes));

                    invoiceList.add(new InvoiceModelClass(id, title, details, amount, rent, invoiceIssued, paymentDue, notes));
                   // Log.d("TotalInvoiceList : ==> ", String.valueOf(invoiceList.size()));
                } while (cursor.moveToNext());
            }
        } catch (SQLiteException e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            if(cursor!=null)
                cursor.close();
            sqLiteDatabase.close();
        }

        return invoiceList;

    }



    //insert payments

    public long insertPayments(PaymentsModelClass payment, long flatId) {
        long rowId = -1;

        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Config.COLUMN_PAYMENT_AMOUNT, payment.getAmount());
        contentValues.put(Config.COLUMN_PAYMENT_PAIDWITH, payment.getPaidwith());
        contentValues.put(Config.COLUMN_PAYMENT_DATERECEIVED, payment.getDatereceived());
        contentValues.put(Config.COLUMN_PAYMENT_RECEIVEDFROM, payment.getReceivedfrom());
        contentValues.put(Config.COLUMN_PAYMENT_TAXSTATUS, payment.getTaxstatus());
        contentValues.put(Config.COLUMN_PAYMENT_NOTES, payment.getNotes());
        contentValues.put(Config.COLUMN_FPY_ID, flatId);

        try {
            rowId = sqLiteDatabase.insertOrThrow(Config.TABLE_PAYMENTS, null, contentValues);
        } catch (SQLiteException e) {
            Logger.d(e);
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return rowId;
    }


    //get All payments by id
    public List<PaymentsModelClass> getAllPaymentsbyId(long fId){
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        List<PaymentsModelClass> paymentsList = new ArrayList<>();
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(Config.TABLE_PAYMENTS,
                    new String[] {Config.COLUMN_PAYMENT_ID, Config.COLUMN_PAYMENT_AMOUNT,
                            Config.COLUMN_PAYMENT_PAIDWITH, Config.COLUMN_PAYMENT_DATERECEIVED, Config.COLUMN_PAYMENT_RECEIVEDFROM, Config.COLUMN_PAYMENT_TAXSTATUS,
                            Config.COLUMN_PAYMENT_NOTES},
                            Config.COLUMN_FPY_ID + " = ? ",
                            new String[] {String.valueOf(fId)},
                            null, null, null);

            if(cursor!=null && cursor.moveToFirst()){
                paymentsList = new ArrayList<>();
                do {
                    long id = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_PAYMENT_ID));
                    String amount = cursor.getString(cursor.getColumnIndex(Config.COLUMN_PAYMENT_AMOUNT));
                    String paidwith = cursor.getString(cursor.getColumnIndex(Config.COLUMN_PAYMENT_PAIDWITH));
                    String datereceived = cursor.getString(cursor.getColumnIndex(Config.COLUMN_PAYMENT_DATERECEIVED));
                    String receivedfrom = cursor.getString(cursor.getColumnIndex(Config.COLUMN_PAYMENT_RECEIVEDFROM));
                    String taxstatus = cursor.getString(cursor.getColumnIndex(Config.COLUMN_PAYMENT_TAXSTATUS));
                    String notes = cursor.getString(cursor.getColumnIndex(Config.COLUMN_PAYMENT_NOTES));


                    paymentsList.add(new PaymentsModelClass(id, amount, paidwith, datereceived, receivedfrom, taxstatus, notes));
                    // Log.d("TotalInvoiceList : ==> ", String.valueOf(invoiceList.size()));
                } while (cursor.moveToNext());
            }
        } catch (SQLiteException e){
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
            } finally {
                if(cursor!=null)
                    cursor.close();
                sqLiteDatabase.close();
            }

            return paymentsList;

        }

}


