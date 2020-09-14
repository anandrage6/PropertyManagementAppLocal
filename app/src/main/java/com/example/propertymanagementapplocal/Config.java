package com.example.propertymanagementapplocal;

public class Config {

    // Database name
    public static final String DATABASE_NAME = "propertyManagement-db";

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
    public static final String COLUMN_FLATS_FLATNO = "flatno";
    public static final String COLUMN_PF_ID = "fk_id";
    public static final String PROPERTY_SUB_CONSTRAINT = "property_sub_unique";

    //colmuns names of tenants table

    public static final String TABLE_TENANTS = "flats";
    public static final String COLUMN_TENANTS_ID = "_id";

    //others for general purpose key-value pair data
    public static final String TITLE = "title";
    public static final String CREATE_PROPERTY = "create_student";
    public static final String UPDATE_PROPERTY = "update_student";
    public static final String CREATE_FLAT = "create_subject";
    public static final String UPDATE_FLAT = "update_subject";
    public static final String PROPERTY_ID = "_id";



}
