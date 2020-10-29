package com.example.propertymanagementapplocal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.TextView;

public class TenantFullDetails extends AppCompatActivity {

    TextView name,email,phone,leaseStart,leaseEnd,rentIsPaid,totalOccupants,notes,rentAmount,securityDeposit;
    String strName, strPhone, strEmail, strleaseStart, strLeaseEnd, strRentIsPaid, strTotalOccupants, strNotes, strRentAmount, strSecurityDeposit;

    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenant_full_details);


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Tenant Details");




        //id's
        name = findViewById(R.id.tenantNameTV);
        email = findViewById(R.id.tenantEmailTV);
        phone = findViewById(R.id.tenantPhoneTV);
        leaseStart = findViewById(R.id.leaseStartTextView);
        leaseEnd = findViewById(R.id.leaseEndTextView);
        rentIsPaid = findViewById(R.id.rentIsPaidTextView);
        totalOccupants = findViewById(R.id.totaloccupantsTextView);
        notes = findViewById(R.id.notesTextView);
        rentAmount = findViewById(R.id.rentAmountTextView);
        securityDeposit = findViewById(R.id.depositTextView);

        //getting values
        strName = getIntent().getStringExtra(Config.COLUMN_TENANTS_NAME);
        strPhone = getIntent().getStringExtra(Config.COLUMN_TENANTS_PHONE);
        strEmail = getIntent().getStringExtra(Config.COLUMN_TENANTS_EMAIL);
        strleaseStart = getIntent().getStringExtra(Config.COLUMN_TENANTS_LEASESTART);
        strLeaseEnd = getIntent().getStringExtra(Config.COLUMN_TENANTS_LEASEEND);
        strRentIsPaid = getIntent().getStringExtra(Config.COLUMN_TENANTS_RENTISPAID);
        strTotalOccupants = getIntent().getStringExtra(Config.COLUMN_TENANTS_TOTALOCCUPANTS);
        strNotes = getIntent().getStringExtra(Config.COLUMN_TENANTS_NOTES);
        strRentAmount = getIntent().getStringExtra(Config.COLUMN_TENANTS_RENT);
        strSecurityDeposit = getIntent().getStringExtra(Config.COLUMN_TENANTS_SECURITYDEPOSIT);

        name.setText(strName);
        phone.setText(strPhone);
        email.setText(strEmail);
        leaseStart.setText(strleaseStart);
        leaseEnd.setText(strLeaseEnd);
        rentIsPaid.setText(strRentIsPaid);
        totalOccupants.setText(strTotalOccupants);
        notes.setText(strNotes);
        rentAmount.setText(strRentAmount);
        securityDeposit.setText(strSecurityDeposit);



    }
}