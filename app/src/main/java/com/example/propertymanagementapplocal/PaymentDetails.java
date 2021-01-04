package com.example.propertymanagementapplocal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class PaymentDetails extends AppCompatActivity {
    private Toolbar toolbar;

    TextView amountTv, paidwithTv, datereceivedTv, receivedfromTv, taxstatusTv, notesTv, currencyId;
    String strAmount, strPaidwith, strDatereceived, strReceivedfrom, strTaxstatus, strNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_details);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Payment Details");

        //id's

        currencyId = findViewById(R.id.currencyId);

        amountTv = findViewById(R.id.paymentDetailsAmount);
        paidwithTv = findViewById(R.id.paymentDetailsPaidwith);
        datereceivedTv = findViewById(R.id.paymentDetailsDatereceived);
        receivedfromTv = findViewById(R.id.paymentDetailsReceivedfrom);
        taxstatusTv = findViewById(R.id.paymentDetailsTaxstatus);
        notesTv = findViewById(R.id.paymentDetailsNotes);

        //getting values through intent

        strAmount = getIntent().getStringExtra(Config.COLUMN_PAYMENT_AMOUNT);
        strPaidwith = getIntent().getStringExtra(Config.COLUMN_PAYMENT_PAIDWITH);
        strDatereceived = getIntent().getStringExtra(Config.COLUMN_PAYMENT_DATERECEIVED);
        strReceivedfrom = getIntent().getStringExtra(Config.COLUMN_PAYMENT_RECEIVEDFROM);
        strTaxstatus = getIntent().getStringExtra(Config.COLUMN_PAYMENT_TAXSTATUS);
        strNotes = getIntent().getStringExtra(Config.COLUMN_PAYMENT_NOTES);

        //setting values

        amountTv.setText(strAmount);
        paidwithTv.setText(strPaidwith);
        datereceivedTv.setText(strDatereceived);
        receivedfromTv.setText(strReceivedfrom);
        taxstatusTv.setText(strTaxstatus);
        notesTv.setText(strNotes);


        //preferences settings
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        String upiv = pref.getString("ownerUpiId","");
        String curv = pref.getString("currencyType","₹");
        String rendv = pref.getString("RentalDueday","One Month");

        currencyId.setText(curv.trim());

    }
}