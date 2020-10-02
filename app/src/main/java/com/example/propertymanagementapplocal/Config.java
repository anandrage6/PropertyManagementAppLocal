package com.example.propertymanagementapplocal;

public class Config {

    // Database name
    public static final String DATABASE_NAME = "propertyManagement-db2";

    //column names of student table
    public static final String TABLE_PROPERTY = "properties";
    public static final String COLUMN_PROPERTY_ID = "_id";
    public static final String COLUMN_PROPERTY_PROPERTYTYPE = "proprtyType";
    public static final String COLUMN_PROPERTY_PROPERTYNAME = "propertyName";
    public static final String COLUMN_PROPERTY_OWNERNAME = "ownerName";
    public static final String COLUMN_PROPERTY_ADDRESS = "address";
    public static final String COLUMN_PROPERTY_CITY = "city";
    public static final String COLUMN_PROPERTY_STATE = "state";
    public static final String COLUMN_PROPERTY_ZIPCODE = "zipCode";
    public static final String COLUMN_PROPERTY_DESCRIPTION = "description";
    public static final String COLUMN_PROPERTY_IMAGE = "image";

    //column names of flats table
    public static final String TABLE_FLATS = "flats";
    public static final String COLUMN_FLATS_ID = "_id";
    public static final String COLUMN_FLATS_FLOOR = "floor";
    public static final String COLUMN_FLATS_FLATNO = "flatno";
    public static final String COLUMN_FLATS_FLATFACING = "flatfacing";
    public static final String COLUMN_FLATS_NOOFBEDROOMS = "noofbedrooms";
    public static final String COLUMN_PF_ID = "fk_id";
    public static final String PROPERTY_SUB_CONSTRAINT = "property_sub_unique";

    //colmuns names of tenants table

    public static final String TABLE_TENANTS = "Tenants";
    public static final String COLUMN_TENANTS_ID = "_id";
    public static final String COLUMN_TENANTS_NAME = "Name";
    public static final String COLUMN_TENANTS_PHONE = "Phone";
    public static final String COLUMN_TENANTS_EMAIL = "Email";
    public static final String COLUMN_TENANTS_LEASESTART = "LeaseStart";
    public static final String COLUMN_TENANTS_LEASEEND = "LeaseEnd";
    public static final String COLUMN_TENANTS_RENTISPAID = "RentisPaid";
    public static final String COLUMN_TENANTS_TOTALOCCUPANTS = "TotalOccupants";
    public static final String COLUMN_TENANTS_NOTES= "Notes";
    public static final String COLUMN_TENANTS_RENT = "Rent";
    public static final String COLUMN_TENANTS_SECURITYDEPOSIT = "SecurityDeposit";
    //public static final String COLUMN_PT_ID = "property_fk_id";
    public static final String COLUMN_FT_ID = "flat_fk_id";
    public static final String FLAT_SUB_CONSTRAINT = "flat_sub_unique";

    //Columns names of invoices
    public static final String TABLE_INVOICE = "Invoice";
    public static final String COLUMN_INVOICE_ID = "_id";
    public static final String COLUMN_INVOICE_TITLE = "Title";
    public static final String COLUMN_INVOICE_DETAILS = "Details";
    public static final String COLUMN_INVOICE_AMOUNT = "Amount";
    public static final String COLUMN_INVOICE_RENT = "Rent";
    public static final String COLUMN_INVOICE_INVOICE_ISSUED = "Invoice_issued";
    public static final String COLUMN_INVOICE_PaymentDue = "Payment_due";
    public static final String COLUMN_INVOICE_Notes = "Notes";
    public static final String COLUMN_FI_ID = "flat_fk_id";
    public static final String INVOICE_SUB_CONSTRAINT = "flat_sub_unique";



    //others for general purpose key-value pair data
    public static final String TITLE = "title";
    public static final String CREATE_PROPERTY = "create_property";
    public static final String UPDATE_PROPERTY = "update_property";
    public static final String CREATE_FLAT = "create_flat";
    public static final String UPDATE_FLAT = "update_flat";
    public static final String PROPERTY_ID = "_id";
    public static final String CREATE_TENANT = "create_tenant";
    public static final String UPDATE_TENANT = "update_tenant";
    public static final String CREATE_INVOICE = "create_invoice";


}
