package com.example.propertymanagementapplocal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class InvoiceFullDetails extends AppCompatActivity {

    TextView title, details, amount, rent, invoiceIssued, paymentdue, note;
    String strTitle, strDetails, strAmount, strRent, strInvoiceIssued, strPaymentDue, strNote;
    private Toolbar toolbar;

    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_full_details);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Invoice Details");

        title = findViewById(R.id.titleTv);
        details = findViewById(R.id.detailsTv);
        amount = findViewById(R.id.amountTv);
        rent = findViewById(R.id.rentTv);
        invoiceIssued = findViewById(R.id.invoiceissuedDateTv);
        paymentdue = findViewById(R.id.paymentdueDateTv);
        note = findViewById(R.id.noteTv);
        linearLayout = findViewById(R.id.invoiceFullDetailsHide);

        strTitle = getIntent().getStringExtra(Config.COLUMN_INVOICE_TITLE);
        strDetails = getIntent().getStringExtra(Config.COLUMN_INVOICE_DETAILS);
        strAmount = getIntent().getStringExtra(Config.COLUMN_INVOICE_AMOUNT);
        strRent = getIntent().getStringExtra(Config.COLUMN_INVOICE_RENT);
        strInvoiceIssued = getIntent().getStringExtra(Config.COLUMN_INVOICE_INVOICE_ISSUED);
        strPaymentDue = getIntent().getStringExtra(Config.COLUMN_INVOICE_PaymentDue);
        strNote = getIntent().getStringExtra(Config.COLUMN_INVOICE_Notes);

        title.setText(strTitle);
        details.setText(strDetails);
        amount.setText(strAmount);
        rent.setText(strRent);
        invoiceIssued.setText(strInvoiceIssued);
        paymentdue.setText(strPaymentDue);
        note.setText(strNote);

        //show and hide layout
        //linearLayout.setVisibility(linearLayout.GONE);

        if (!title.getText().toString().isEmpty() && !details.getText().toString().isEmpty() && !amount.getText().toString().isEmpty()) {
            linearLayout.setVisibility(linearLayout.VISIBLE);
        } else {
            linearLayout.setVisibility(linearLayout.GONE);
        }

    }
}