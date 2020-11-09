package com.example.propertymanagementapplocal;

public class TenantModelClass {
    private long tenantId;
    private String tenantName, tenantphone, tenantEmail, leaseStart, leaseEnd, rentIsPaid, totalOccupants, notes, rentAmount, securityDeposit;
    long fTId;


    public TenantModelClass() {
    }

    public TenantModelClass(long fTId) {
        this.fTId = fTId;
    }

    public long getfTId() {
        return fTId;
    }

    public void setfTId(long fTId) {
        this.fTId = fTId;
    }

    public TenantModelClass(long tenantId, String tenantName, String tenantphone, String tenantEmail, String leaseStart, String leaseEnd, String rentIsPaid, String totalOccupants, String notes, String rentAmount, String securityDeposit) {
        this.tenantId = tenantId;
        this.tenantName = tenantName;
        this.tenantphone = tenantphone;
        this.tenantEmail = tenantEmail;
        this.leaseStart = leaseStart;
        this.leaseEnd = leaseEnd;
        this.rentIsPaid = rentIsPaid;
        this.totalOccupants = totalOccupants;
        this.notes = notes;
        this.rentAmount = rentAmount;
        this.securityDeposit = securityDeposit;
    }

    public long getTenantId() {
        return tenantId;
    }

    public void setTenantId(long tenantId) {
        this.tenantId = tenantId;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getTenantphone() {
        return tenantphone;
    }

    public void setTenantphone(String tenantphone) {
        this.tenantphone = tenantphone;
    }

    public String getTenantEmail() {
        return tenantEmail;
    }

    public void setTenantEmail(String tenantEmail) {
        this.tenantEmail = tenantEmail;
    }

    public String getLeaseStart() {
        return leaseStart;
    }

    public void setLeaseStart(String leaseStart) {
        this.leaseStart = leaseStart;
    }

    public String getLeaseEnd() {
        return leaseEnd;
    }

    public void setLeaseEnd(String leaseEnd) {
        this.leaseEnd = leaseEnd;
    }

    public String getRentIsPaid() {
        return rentIsPaid;
    }

    public void setRentIsPaid(String rentIsPaid) {
        this.rentIsPaid = rentIsPaid;
    }

    public String getTotalOccupants() {
        return totalOccupants;
    }

    public void setTotalOccupants(String totalOccupants) {
        this.totalOccupants = totalOccupants;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getRentAmount() {
        return rentAmount;
    }

    public void setRentAmount(String rentAmount) {
        this.rentAmount = rentAmount;
    }

    public String getSecurityDeposit() {
        return securityDeposit;
    }

    public void setSecurityDeposit(String securityDeposit) {
        this.securityDeposit = securityDeposit;
    }
}
