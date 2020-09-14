package com.example.propertymanagementapplocal;

import android.net.Uri;

public class PropertyModelClass {
    private long id;
    private String propertyType, propertyName, ownerName, address, city, state, zipCode, description, image;


    public PropertyModelClass() {
    }

    public PropertyModelClass(long id, String propertyType, String propertyName, String ownerName, String address, String city, String state, String zipCode, String description, String image) {
        this.id = id;
        this.propertyType = propertyType;
        this.propertyName = propertyName;
        this.ownerName = ownerName;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.description = description;
        this.image = image;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
