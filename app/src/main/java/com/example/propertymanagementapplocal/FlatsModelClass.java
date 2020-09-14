package com.example.propertymanagementapplocal;

public class FlatsModelClass {

    private long flatId;
    private String flaNo;

    public FlatsModelClass() {
    }

    public FlatsModelClass(long flatId, String flaNo) {
        this.flatId = flatId;
        this.flaNo = flaNo;
    }

    public long getFlatId() {
        return flatId;
    }

    public void setFlatId(long flatId) {
        this.flatId = flatId;
    }

    public String getFlaNo() {
        return flaNo;
    }

    public void setFlaNo(String flaNo) {
        this.flaNo = flaNo;
    }
}