package com.example.propertymanagementapplocal;

import java.util.Date;

public class BalancesModel {
    private int type;
    private Date EntryDate;

    public Date getEntryDate() {
        return EntryDate;
    }

    public void setEntryDate(Date entryDate) {
        EntryDate = entryDate;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
