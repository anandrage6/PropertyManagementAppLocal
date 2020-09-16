package com.example.propertymanagementapplocal;

public class FlatsModelClass {

    private long flatId;
    private String floor;
    private String flaNo;
    private String faltfacing;
    private String noofbedrooms;

    public FlatsModelClass() {
    }

    public FlatsModelClass(long flatId, String floor, String flaNo, String faltfacing, String noofbedrooms) {
        this.flatId = flatId;
        this.floor = floor;
        this.flaNo = flaNo;
        this.faltfacing = faltfacing;
        this.noofbedrooms = noofbedrooms;
    }

    public long getFlatId() {
        return flatId;
    }

    public void setFlatId(long flatId) {
        this.flatId = flatId;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getFlaNo() {
        return flaNo;
    }

    public void setFlaNo(String flaNo) {
        this.flaNo = flaNo;
    }

    public String getFaltfacing() {
        return faltfacing;
    }

    public void setFaltfacing(String faltfacing) {
        this.faltfacing = faltfacing;
    }

    public String getNoofbedrooms() {
        return noofbedrooms;
    }

    public void setNoofbedrooms(String noofbedrooms) {
        this.noofbedrooms = noofbedrooms;
    }
}