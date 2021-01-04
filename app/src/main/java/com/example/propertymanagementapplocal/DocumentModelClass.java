package com.example.propertymanagementapplocal;

import android.graphics.Bitmap;

import java.util.List;

public class DocumentModelClass {
    private Long Id;
    private String Doc;

    public DocumentModelClass(int i, List<Bitmap> bitmaps) {
    }

    public DocumentModelClass(Long id, String doc) {
        Id = id;
        Doc = doc;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getDoc() {
        return Doc;
    }

    public void setDoc(String doc) {
        Doc = doc;
    }
}
