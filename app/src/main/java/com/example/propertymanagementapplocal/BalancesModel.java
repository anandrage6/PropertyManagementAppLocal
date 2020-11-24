package com.example.propertymanagementapplocal;

import java.util.Date;

public class BalancesModel {
    private int type;
    private Date EntryDate;
    private String title;
    private String titleAmount;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleAmount() {
        return titleAmount;
    }

    public void setTitleAmount(String titleAmount) {
        this.titleAmount = titleAmount;
    }
}
