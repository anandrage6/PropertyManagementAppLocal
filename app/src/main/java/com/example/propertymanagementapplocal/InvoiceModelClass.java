package com.example.propertymanagementapplocal;

public class InvoiceModelClass extends BalancesModel {

    private long invoiceId;
    private String title;
    private String details;
    private String amount;
    private String rent;
    private String invoiceIssued;
    private String paymentDue;
    private String notes;
    private String waterBill;
    private String electricityBill;

    public String getWaterBill() {
        return waterBill;
    }

    public void setWaterBill(String waterBill) {
        this.waterBill = waterBill;
    }

    public String getElectricityBill() {
        return electricityBill;
    }

    public void setElectricityBill(String electricityBill) {
        this.electricityBill = electricityBill;
    }

    public String getMaintenanceCharges() {
        return maintenanceCharges;
    }

    public void setMaintenanceCharges(String maintenanceCharges) {
        this.maintenanceCharges = maintenanceCharges;
    }

    private String maintenanceCharges;
    long tIId;

    public long gettIId() {
        return tIId;
    }

    public void settIId(long tIId) {
        this.tIId = tIId;
    }

    //empty Constructor
    public InvoiceModelClass() {
    }

    //paremetirised constructor
    public InvoiceModelClass(long invoiceId, String title, String details, String amount, String rent,String waterBill, String electricityBill, String maintenanceCharges, String invoiceIssued, String paymentDue, String notes) {
        this.invoiceId = invoiceId;
        this.title = title;
        this.details = details;
        this.amount = amount;
        this.rent = rent;
        this.waterBill = waterBill;
        this.electricityBill = electricityBill;
        this.maintenanceCharges = maintenanceCharges;
        this.invoiceIssued = invoiceIssued;
        this.paymentDue = paymentDue;
        this.notes = notes;
    }

    //geters and setters

    public long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getRent() {
        return rent;
    }

    public void setRent(String rent) {
        this.rent = rent;
    }

    public String getInvoiceIssued() {
        return invoiceIssued;
    }

    public void setInvoiceIssued(String invoiceIssued) {
        this.invoiceIssued = invoiceIssued;
    }

    public String getPaymentDue() {
        return paymentDue;
    }

    public void setPaymentDue(String paymentDue) {
        this.paymentDue = paymentDue;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
