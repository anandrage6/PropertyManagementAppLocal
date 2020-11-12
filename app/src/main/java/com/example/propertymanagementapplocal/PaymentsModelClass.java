package com.example.propertymanagementapplocal;

public class PaymentsModelClass {

    private long paymentId;
    private String amount, paidwith, datereceived, receivedfrom, taxstatus, notes;

    long tPId;

    public PaymentsModelClass(long paymentId) {
        this.paymentId = paymentId;
    }

    public long gettPId() {
        return tPId;
    }

    public void settPId(long tPId) {
        this.tPId = tPId;
    }

    public PaymentsModelClass() {
    }

    public PaymentsModelClass(long paymentId, String amount, String paidwith, String datereceived, String receivedfrom, String taxstatus, String notes) {
        this.paymentId = paymentId;
        this.amount = amount;
        this.paidwith = paidwith;
        this.datereceived = datereceived;
        this.receivedfrom = receivedfrom;
        this.taxstatus = taxstatus;
        this.notes = notes;
    }

    public long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(long paymentId) {
        this.paymentId = paymentId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPaidwith() {
        return paidwith;
    }

    public void setPaidwith(String paidwith) {
        this.paidwith = paidwith;
    }

    public String getDatereceived() {
        return datereceived;
    }

    public void setDatereceived(String datereceived) {
        this.datereceived = datereceived;
    }

    public String getReceivedfrom() {
        return receivedfrom;
    }

    public void setReceivedfrom(String receivedfrom) {
        this.receivedfrom = receivedfrom;
    }

    public String getTaxstatus() {
        return taxstatus;
    }

    public void setTaxstatus(String taxstatus) {
        this.taxstatus = taxstatus;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

}
